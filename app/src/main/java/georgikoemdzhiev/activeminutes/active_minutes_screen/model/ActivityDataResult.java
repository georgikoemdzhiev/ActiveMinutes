package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

/**
 * Created by Georgi Koemdzhiev on 24/02/2017.
 */
public interface ActivityDataResult {

    void onSuccess(int paGoal, int activeTime, int longestInacInter, int averageIacInter);

    void onError(String message);
}
