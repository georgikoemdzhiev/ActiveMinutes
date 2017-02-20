package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import georgikoemdzhiev.activeminutes.active_minutes_screen.model.IActiveMinutesModel;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.IActiveMinutesView;

/**
 * Created by Georgi Koemdzhiev on 20/02/2017.
 */

public class ActiveMinutesPresenter implements IActiveMinutesPresenter {
    private IActiveMinutesModel mModel;
    private IActiveMinutesView mView;

    public ActiveMinutesPresenter(IActiveMinutesModel model) {
        mModel = model;
    }

    @Override
    public void isUserLoggedIn() {
        if (!mModel.isUserLoggedIn()) {
            mView.userNotLoggedIn();
        } else {
            mView.setLoggedInUser(mModel.getLoggedInUser());
        }
    }

    @Override
    public void logOutUser() {
        mModel.logOutUser();
        mView.showMessage("You are logged out.");
    }

    @Override
    public void setView(IActiveMinutesView view) {
        this.mView = view;
    }

    @Override
    public void releaseView() {
        this.mView = null;
    }
}
