package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import java.util.Calendar;
import java.util.Date;

import georgikoemdzhiev.activeminutes.active_minutes_screen.model.ISettingsModel;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.SettingsInfoResult;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ISettingsView;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.SetResult;
import georgikoemdzhiev.activeminutes.utils.DateUtils;

/**
 * Created by Georgi Koemdzhiev on 13/03/2017.
 */

public class SettingsPresenter implements ISettingsPresenter {
    private ISettingsView mView;
    private ISettingsModel mModel;

    public SettingsPresenter(ISettingsModel model) {
        mModel = model;
    }

    @Override
    public void onSleepingHoursSet(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        // set the user selected hour and minute
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        // create a date object
        Date startSHdate = calendar.getTime();
        // add one day (for the stopSleepingHours end interval - assuming it next day)
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        // set user selected hour and minute
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDayEnd);
        calendar.set(Calendar.MINUTE, minuteEnd);
        // create a date object
        Date stopSHDate = calendar.getTime();
        // Check of the difference between the dates is at least 8 hours
        if (DateUtils.getDifferenceInHours(startSHdate, stopSHDate) >= 8) {
            // the difference between the dates is > = 8 hours. It is OK
            try {
                mModel.setSleepingHours(hourOfDay, minute, hourOfDayEnd, minuteEnd);
                mView.showMessage("Selected: " +
                        hourOfDay + " " + minute +
                        " " + hourOfDayEnd
                        + " " + minuteEnd);
            } catch (Exception e) {
                mView.showMessage("Error! " + e.getMessage());
            }

        } else {
            mView.showMessage("The minimal interval for Sleeping hours is 8 hours!");
        }
    }

    @Override
    public void setView(ISettingsView view) {
        this.mView = view;
    }


    @Override
    public void releaseView() {
        this.mView = null;
    }

    @Override
    public void getSettingsInfo() {
        mModel.getSettingsInfo(new SettingsInfoResult() {
            @Override
            public void onSuccess(String sleepingHours, String paGoal, String stGoal) {
                mView.setSettingsData(sleepingHours, paGoal, stGoal);
            }

            @Override
            public void onError(String message) {
                mView.showMessage("Error! " + message);
            }
        });
    }

    @Override
    public void setPaGoal(int paGoal) {
        mModel.setPaGoal(paGoal, new SetResult() {
            @Override
            public void onSuccess() {
                mView.showMessage("PA goal set");
            }

            @Override
            public void onError(String message) {
                mView.showMessage("Error! " + message);
            }
        });
    }

    @Override
    public void setStGoal(final int stGoal) {
        mModel.setStGoal(stGoal, new SetResult() {
            @Override
            public void onSuccess() {
                mView.showMessage(stGoal + " ST goal set");
            }

            @Override
            public void onError(String message) {
                mView.showMessage("Error! " + message);
            }
        });
    }
}
