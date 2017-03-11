package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;

/**
 * Created by Georgi Koemdzhiev on 08/03/2017.
 */

public class HistoryModel implements IHistoryModel {
    private IActivityDataManager mDataManager;

    public HistoryModel(IActivityDataManager dataManager, IAuthDataManager authDataManager) {
        mDataManager = dataManager;
        mDataManager.setUser(authDataManager.getLoggedInUser());
    }

    @Override
    public void getDailyData(HistoryDataDailyResult result) {
        try {
            result.onSuccess(mDataManager.getAllActivitiesSortedByDate());
        } catch (Exception e) {
            result.onError(e.getMessage());
        }
    }

    @Override
    public void getWeeklyDate(HistoryDataWeeklyResult result) {
        try {
            result.onSuccess(mDataManager.getAllActivityGroupedByWeek());
        } catch (Exception e) {
            result.onError(e.getMessage());
        }
    }
}
