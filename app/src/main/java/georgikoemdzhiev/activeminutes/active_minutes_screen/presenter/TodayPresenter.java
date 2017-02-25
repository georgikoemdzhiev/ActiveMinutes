package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import georgikoemdzhiev.activeminutes.active_minutes_screen.model.ActivityDataResult;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.ITodayModel;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ITodayView;

/**
 * Created by Georgi Koemdzhiev on 24/02/2017.
 */

public class TodayPresenter implements ITodayPresenter {
    private ITodayModel mModel;
    private ITodayView mView;

    public TodayPresenter(ITodayModel model) {
        mModel = model;
    }

    @Override
    public void getActivityInformation() {
        mModel.getActivityData(new ActivityDataResult() {
            @Override
            public void onSuccess(int paGoal,
                                  int maxContInacTarget,
                                  int timesTargetExceeded,
                                  int activeTime,
                                  int longestInacInter,
                                  int averageIacInter) {
                mView.setData(paGoal,
                        String.valueOf(maxContInacTarget / 60),
                        String.valueOf(timesTargetExceeded),
                        activeTime,
                        String.valueOf(longestInacInter / 60),
                        String.valueOf(averageIacInter / 60));
            }

            @Override
            public void onError(String message) {
                mView.showErrorMessage(message);
            }
        });
    }

    @Override
    public void setView(ITodayView view) {
        this.mView = view;
    }

    @Override
    public void releaseView() {
        this.mView = null;
    }
}
