package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.SetResult;

/**
 * Created by Georgi Koemdzhiev on 13/03/2017.
 */

public class SettingsModel implements ISettingsModel {
    private IActivityDataManager mActivityDataManager;

    public SettingsModel(IActivityDataManager activityDataManager, IAuthDataManager authDataManager) {
        mActivityDataManager = activityDataManager;
        mActivityDataManager.setUser(authDataManager.getLoggedInUser());
    }

    @Override
    public void setSleepingHours(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {

        mActivityDataManager.setUserSleepingHours(hourOfDay, minute, hourOfDayEnd, minuteEnd);

    }

    @Override
    public void getSettingsInfo(SettingsInfoResult result) {
        try {

            String sleepingHours = mActivityDataManager.getUserSleepingHours();
            String paGoal = String.valueOf(mActivityDataManager.getUserPaGoal() / 60);
            String stGoal = String.valueOf(mActivityDataManager.getUserStGoal() / 60);
            result.onSuccess(sleepingHours, paGoal, stGoal);
        } catch (Exception e) {
            result.onError(e.getMessage());
        }
    }

    @Override
    public void setPaGoal(int paGoal, SetResult result) {
        try {
            mActivityDataManager.setUserAndActivityPaGoal(paGoal);

            result.onSuccess();
        } catch (Exception e) {
            result.onError(e.getMessage());
        }
    }

    @Override
    public void setStGoal(int stGoal, SetResult setResult) {
        try {
            mActivityDataManager.setUserAndActivityStGoal(stGoal);
            setResult.onSuccess();
        } catch (Exception e) {
            setResult.onError(e.getMessage());
        }

    }
}
