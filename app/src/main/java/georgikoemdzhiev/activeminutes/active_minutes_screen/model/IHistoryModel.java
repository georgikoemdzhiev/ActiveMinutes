package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

/**
 * Created by Georgi Koemdzhiev on 08/03/2017.
 */

public interface IHistoryModel {

    void getDailyData(HistoryDataDailyResult result);

    void getWeeklyDate(HistoryDataWeeklyResult result);
}
