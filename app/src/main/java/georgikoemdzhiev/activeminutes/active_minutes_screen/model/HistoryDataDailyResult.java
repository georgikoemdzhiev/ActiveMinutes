package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import java.util.List;

import georgikoemdzhiev.activeminutes.data_layer.db.Activity;

/**
 * Created by Georgi Koemdzhiev on 08/03/2017.
 */

public interface HistoryDataDailyResult {

    void onSuccess(List<Activity> activities);

    void onError(String message);
}
