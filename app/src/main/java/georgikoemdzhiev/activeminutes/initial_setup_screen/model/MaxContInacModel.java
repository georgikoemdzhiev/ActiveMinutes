package georgikoemdzhiev.activeminutes.initial_setup_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import io.realm.Realm;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class MaxContInacModel implements IMaxContInacModel {
    private IAuthDataManager mAuthDataManager;
    private Realm mRealm;

    public MaxContInacModel(IAuthDataManager authDataManager, Realm realm) {
        mAuthDataManager = authDataManager;
        mRealm = realm;
    }

    @Override
    public void setMCI(int mci, SetResult result) {
        try {
            User user = mAuthDataManager.getLoggedInUser();
            mRealm.beginTransaction();
            user.setMaxContInactTarget(mci);
            mRealm.copyToRealmOrUpdate(user);
            mRealm.commitTransaction();

            result.onSuccess();
        } catch (Exception e) {
            result.onError(e.getMessage());
        }
    }
}
