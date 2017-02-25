package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;

/**
 * Created by Georgi Koemdzhiev on 24/02/2017.
 */

public class TodayModel implements ITodayModel {
    private IActivityDataManager mActivityDataManager;
    private IAuthDataManager mAuthDataManager;

    public TodayModel(IActivityDataManager activityDataManager, IAuthDataManager authDataManager) {
        mActivityDataManager = activityDataManager;
        mAuthDataManager = authDataManager;
    }

    @Override
    public void getActivityData(ActivityDataResult result) {
        int paGoal, activeTime, longestInacInter, averageInacInter, maxContInacTarget, timesTargetExceeded;

        try {
            mActivityDataManager.setUser(mAuthDataManager.getLoggedInUser());
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
