package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

/**
 * Created by Georgi Koemdzhiev on 24/02/2017.
 */

public interface ITodayView {

    void setData(int paGoal,
                 String maxContInacTarget,
                 String timesTargetExceeded,
                 int activeTime,
                 String longestInacInter,
                 String averageIacInter);

    void showErrorMessage(String message);
}
