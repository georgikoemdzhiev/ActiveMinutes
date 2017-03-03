package georgikoemdzhiev.activeminutes.initial_setup_screen.presenter;

import java.util.Calendar;
import java.util.Date;

import georgikoemdzhiev.activeminutes.initial_setup_screen.model.ISleepingHoursModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.ISleepingHoursView;
import georgikoemdzhiev.activeminutes.utils.DateUtils;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class SleepingHoursPresenter implements ISleepingHoursPresenter {
    private ISleepingHoursModel mModel;
    private ISleepingHoursView mView;

    public SleepingHoursPresenter(ISleepingHoursModel model) {
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
            mModel.setSleepingHours(hourOfDay, minute, hourOfDayEnd, minuteEnd);
        } else {
            mView.showError("The minimal interval for Sleeping hours is 8 hours!");
        }
    }

    @Override
    public void setView(ISleepingHoursView view) {
        this.mView = view;
    }

    @Override
    public void releaseView() {
        this.mView = null;
    }
}
