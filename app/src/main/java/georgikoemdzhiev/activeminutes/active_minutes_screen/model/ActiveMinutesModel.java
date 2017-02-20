package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 20/02/2017.
 */

public class ActiveMinutesModel implements IActiveMinutesModel {
    private IAuthDataManager mAuthDataManager;

    public ActiveMinutesModel(IAuthDataManager authDataManager) {
        mAuthDataManager = authDataManager;
    }

    @Override
    public boolean isUserLoggedIn() {
        return mAuthDataManager.isUserLoggedIn();
    }

    @Override
    public User getLoggedInUser() {
        return mAuthDataManager.getLoggedInUser();
    }

    @Override
    public void logOutUser() {
        mAuthDataManager.logOutUser();
    }
}
