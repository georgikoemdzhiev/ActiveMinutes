package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

import java.util.List;

import georgikoemdzhiev.activeminutes.data_layer.db.Activity;

/**
 * Created by Georgi Koemdzhiev on 08/03/2017.
 */

public interface IHistoryView {

    void setDailyActivityData(List<Activity> activityData);

    void setWeeklyActivityData(List<List<Activity>> activities);

    void showMessage(String message);
}
