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

    public MaxContInacPresenter(IMaxContInacModel model) {
        mModel = model;
    }

    @Override
    public void setMCI(int mci) {
        mModel.setMCI(mci, new SetResult() {
            @Override
            public void onSuccess() {
                mView.showMessage("Target saved!");
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
