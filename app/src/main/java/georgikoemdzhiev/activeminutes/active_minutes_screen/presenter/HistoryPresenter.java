package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import java.util.List;

import georgikoemdzhiev.activeminutes.active_minutes_screen.model.HistoryDataResult;
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
        mModel.getDailyData(new HistoryDataResult() {
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
    public void setView(IHistoryView view) {
        this.mView = view;
    }

    @Override
    public void releaseView() {
        this.mView = null;
    }
}
