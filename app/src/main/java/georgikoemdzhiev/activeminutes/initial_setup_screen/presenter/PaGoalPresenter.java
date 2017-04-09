package georgikoemdzhiev.activeminutes.initial_setup_screen.presenter;

import georgikoemdzhiev.activeminutes.initial_setup_screen.model.IPaGoalModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.SetResult;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.IPaGoalView;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class PaGoalPresenter implements IPaGoalPresenter {
    private IPaGoalModel mModel;
    private IPaGoalView mView;
    private final int MINUTES_IN_HOUR = 60;

    public PaGoalPresenter(IPaGoalModel model) {
        mModel = model;
    }

    @Override
    public void setSetPa(final int paGoal) {
        mModel.setPaGoal(paGoal, new SetResult() {
            @Override
            public void onSuccess() {
                mView.showMessage("PA goal set to " + paGoal / MINUTES_IN_HOUR);
            }

            @Override
            public void onError(String message) {
                mView.showErrorMessage("Error! " + message);
            }
        });
    }

    @Override
    public void setView(IPaGoalView view) {
        this.mView = view;
    }

    @Override
    public void releaseView() {
        this.mView = null;
    }
}
