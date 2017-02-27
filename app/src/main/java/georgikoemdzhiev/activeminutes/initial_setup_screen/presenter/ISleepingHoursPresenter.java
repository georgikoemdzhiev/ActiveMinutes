package georgikoemdzhiev.activeminutes.initial_setup_screen.presenter;

import georgikoemdzhiev.activeminutes.initial_setup_screen.view.ISleepingHoursView;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public interface ISleepingHoursPresenter {

    void onSleepingHoursSet(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd);

    void setView(ISleepingHoursView view);

    void releaseView();
}
