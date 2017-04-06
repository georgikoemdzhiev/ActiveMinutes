package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ISettingsView;

/**
 * Created by Georgi Koemdzhiev on 13/03/2017.
 */

public interface ISettingsPresenter {

    void onSleepingHoursSet(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd);

    void setView(ISettingsView view);

    void releaseView();

    void getSettingsInfo();

    void setPaGoal(int paGoal);

    void setStGoal(int stGoal);

    void deleteMonitoringData();
}
