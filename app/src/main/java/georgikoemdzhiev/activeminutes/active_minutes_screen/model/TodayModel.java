package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;

/**
 * Created by Georgi Koemdzhiev on 24/02/2017.
 */

public class TodayModel implements ITodayModel {
    private IActivityDataManager mActivityDataManager;

    public TodayModel(IActivityDataManager activityDataManager, IAuthDataManager authDataManager) {
        mActivityDataManager = activityDataManager;
        mActivityDataManager.setUser(authDataManager.getLoggedInUser());
//        Log.e("TodayModel", "TodayModel constructor created!");
    }

    @Override
    public void getActivityData(ActivityDataResult result) {
        int paGoal, activeTime, longestInacInter, averageInacInter, maxContInacTarget, timesTargetExceeded;

        try {
            paGoal = mActivityDataManager.getUserPaGoal();
            activeTime = mActivityDataManager.getActiveTime();
            longestInacInter = mActivityDataManager.getLongestInacInterval();
            averageInacInter = mActivityDataManager.getAverageInacInterval();
            maxContInacTarget = mActivityDataManager.getMaxContInacTarget();
            timesTargetExceeded = mActivityDataManager.getTimesTargetExceeded();

            result.onSuccess(paGoal,
                    maxContInacTarget,
                    timesTargetExceeded,
                    activeTime,
                    longestInacInter,
                    averageInacInter);
        } catch (Exception e) {
            result.onError(e.getMessage());
        }


    }
}
