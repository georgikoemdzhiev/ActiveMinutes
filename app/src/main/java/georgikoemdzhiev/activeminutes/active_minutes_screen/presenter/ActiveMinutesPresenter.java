package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import java.util.Date;

import georgikoemdzhiev.activeminutes.active_minutes_screen.model.IActiveMinutesModel;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.IActiveMinutesView;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.utils.DateUtils;

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
    public void isFirstTimeLaunch() {
        boolean isFirstTime = mModel.isFirstTimeLaunch();
        if (isFirstTime) {
            mView.showInitialSetup();
            mModel.setInitialSetupCompleted();
        } else {
            mView.loadDrawerScreens();

            User mUser = mModel.getLoggedInUser();
            Date now = new Date();
            Date stopSleepingHours = mUser.getStopSleepingHours();
            Date startSleepingHours = mUser.getStartSleepingHours();
            // check if it is not sleeping hours before starting the service
            if (!DateUtils.isSleepingHours(startSleepingHours, now, stopSleepingHours)) {
                // start the ActiveMinutesService
                mView.startService();
            }

            mView.scheduleService();
        }
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
