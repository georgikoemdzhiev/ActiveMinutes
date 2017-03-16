package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

/**
 * Created by Georgi Koemdzhiev on 13/03/2017.
 */

public interface ISettingsView {

    void showMessage(String message);

    void setSettingsData(String sleepingHours, String paGoal, String stGoal);
}
