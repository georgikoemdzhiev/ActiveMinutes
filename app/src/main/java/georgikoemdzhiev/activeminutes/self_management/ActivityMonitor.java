package georgikoemdzhiev.activeminutes.self_management;

import java.util.Observable;
import java.util.Observer;

import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class ActivityMonitor implements Observer, IActivityMonitor {
    private User mUser;
    private IActivityDataManager mDataManager;

    public ActivityMonitor(IActivityDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Recognised activity: " + getStringActivityFromClass(o));

    }

    private String getStringActivityFromClass(Object activityClass) {
        String activity = "";
        switch ((int) activityClass) {
            case 0:
                activity = "walking";
                mDataManager.incActiveTime();
                mDataManager.clearCurrentInacInterval();
                break;
            case 1:
                activity = "running";
                mDataManager.incActiveTime();
                mDataManager.clearCurrentInacInterval();
                break;
            case 2:
                activity = "static";
                mDataManager.incCurrentInacInterval();
                mDataManager.incTimesInacDetected();
                mDataManager.checkOrUpdateLognestInacInterval();
                break;
            case 3:
                activity = "cycling";
                mDataManager.incActiveTime();
                mDataManager.clearCurrentInacInterval();
                break;
        }

        return activity;
    }

    @Override
    public void setUser(User user) {
        this.mUser = user;
        this.mDataManager.setUserId(mUser);
    }
}
