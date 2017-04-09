package georgikoemdzhiev.activeminutes.initial_setup_screen.presenter;

import georgikoemdzhiev.activeminutes.initial_setup_screen.model.IMaxContInacModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.SetResult;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.IMaxContInacView;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class MaxContInacPresenter implements IMaxContInacPresenter {
    private IMaxContInacModel mModel;
    private IMaxContInacView mView;
    private final int MINUTES_IN_HOUR = 60;

    public MaxContInacPresenter(IMaxContInacModel model) {
        mModel = model;
    }

    @Override
    public void setMCI(final int mci) {
        mModel.setMCI(mci, new SetResult() {
            @Override
            public void onSuccess() {
                mView.showMessage("MCI set to " + mci / MINUTES_IN_HOUR);
            }

            @Override
            public void onError(String message) {
                mView.showMessage("Error! " + message);
            }
        });
    }

    @Override
    public void setView(IMaxContInacView view) {
        this.mView = view;
    }

    @Override
    public void releaseView() {
        this.mView = null;
    }
}
