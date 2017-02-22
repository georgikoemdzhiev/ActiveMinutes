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
    public void update(Observable observable, Object activity) {
        monitorActivity(activity);
    }

    private void monitorActivity(Object activityClass) {

        switch ((int) activityClass) {
            case 0:
                //activity = "walking";
            case 1:
                //activity = "running";
            case 3:
                //activity = "cycling";
                mDataManager.incActiveTime();
                mDataManager.clearCurrentInacInterval();
                break;
            case 2:
                //activity = "static";
                mDataManager.incCurrentInacInterval();
                break;
        }

    }

    @Override
    public void setUser(User user) {
        this.mUser = user;
        this.mDataManager.setUserId(mUser);
    }
}
