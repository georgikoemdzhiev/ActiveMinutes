package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import java.util.List;

import georgikoemdzhiev.activeminutes.active_minutes_screen.model.HistoryDataDailyResult;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.HistoryDataWeeklyResult;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.IHistoryModel;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.IHistoryView;
import georgikoemdzhiev.activeminutes.data_layer.db.Activity;

/**
 * Created by Georgi Koemdzhiev on 08/03/2017.
 */

public class HistoryPresenter implements IHistoryPresenter {
    private IHistoryView mView;
    private IHistoryModel mModel;

    public HistoryPresenter(IHistoryModel model) {
        mModel = model;
    }

    @Override
    public void getDailyActivityData() {
        mModel.getDailyData(new HistoryDataDailyResult() {
            @Override
            public void onSuccess(List<Activity> activities) {
                mView.setDailyActivityData(activities);
            }

            @Override
            public void onError(String message) {
                mView.showMessage("Error! " + message);
            }
        });
    }

    @Override
    public void getWeeklyActivityData() {
        mModel.getWeeklyDate(new HistoryDataWeeklyResult() {
            @Override
            public void onSuccess(List<List<Activity>> activities) {
                mView.setWeeklyActivityData(activities);
            }

            @Override
            public void onError(String message) {
                mView.showMessage("Error! " + message);
            }
        });
    }

    @Override
    public void setView(IHistoryView view) {
        this.mView = view;
    }

    @Override
    public void releaseView() {
        this.mView = null;
    }
}
