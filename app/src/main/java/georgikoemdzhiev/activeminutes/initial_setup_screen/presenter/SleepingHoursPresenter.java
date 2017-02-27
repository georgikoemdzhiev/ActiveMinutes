package georgikoemdzhiev.activeminutes.initial_setup_screen.presenter;

import georgikoemdzhiev.activeminutes.initial_setup_screen.model.ISleepingHoursModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.ISleepingHoursView;

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
        mModel.setSleepingHours(hourOfDay, minute, hourOfDayEnd, minuteEnd);
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
