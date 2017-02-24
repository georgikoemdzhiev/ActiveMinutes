package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

/**
 * Created by Georgi Koemdzhiev on 24/02/2017.
 */

public interface ITodayView {

    void setData(int paGoal, int activeTime, int longestInacInter, int averageIacInter);

    void showErrorMessage(String message);
}
