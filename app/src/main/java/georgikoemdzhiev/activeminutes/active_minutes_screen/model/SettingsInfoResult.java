package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

/**
 * Created by Georgi Koemdzhiev on 14/03/2017.
 */

public interface SettingsInfoResult {
    void onSuccess(String sleepingHours, String paGoal, String stGoal);

    void onError(String message);
}
