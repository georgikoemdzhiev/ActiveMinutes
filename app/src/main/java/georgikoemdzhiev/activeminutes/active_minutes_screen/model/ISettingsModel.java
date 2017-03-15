package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import georgikoemdzhiev.activeminutes.initial_setup_screen.model.SetResult;

/**
 * Created by Georgi Koemdzhiev on 13/03/2017.
 */

public interface ISettingsModel {

    void setSleepingHours(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd);

    void getSettingsInfo(SettingsInfoResult result);

    void setPaGoal(int paGoal, SetResult setResult);

    void setStGoal(int stGoal, SetResult setResult);
}
