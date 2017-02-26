package georgikoemdzhiev.activeminutes.self_management;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.utils.LimitedSizeQueue;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class ActivityMonitor implements Observer, IActivityMonitor {
    private IActivityDataManager mDataManager;
    private ArrayList<Integer> correctionList;

    public ActivityMonitor(IActivityDataManager dataManager) {
        mDataManager = dataManager;
        correctionList = new LimitedSizeQueue<>(3);
    }

    @Override
    public void update(Observable observable, Object activity) {
        monitorActivity(activity);
    }

    private void monitorActivity(Object activityClass) {
        int activityInt = (int) activityClass;
        correctionList.add(activityInt);
        int correctResult = applyCorrection(activityInt);
        // TODO Maybe make incActiveTime to return the active time for FeedBack management in the future
        if (correctResult == correctionList.size()) {
            mDataManager.incActiveTime();
            mDataManager.clearCurrentInacInterval();
        } else {
            mDataManager.incCurrentInacInterval();
        }


    }

    @Override
    public void setUser(User user) {
        User user1 = user;
        this.mDataManager.setUser(user1);
    }

    private int applyCorrection(int classInt) {
        int count = 0;
        // iterate through the correctionList containing the last 3 recognised classes (i.e. 0,1,3)
        for (int item : correctionList) {
            // check if the current item is of class "cycling" - 3; "running" - 1;"walking" - 0
            if (item == 0 || item == 1 || item == 3) {
                // if it is, increment the counter
                count++;
            }
        }

        return count;
    }
}
