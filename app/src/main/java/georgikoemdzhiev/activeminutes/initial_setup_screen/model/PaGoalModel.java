package georgikoemdzhiev.activeminutes.initial_setup_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import io.realm.Realm;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class PaGoalModel implements IPaGoalModel {
    private IAuthDataManager mAuthDataManager;
    private Realm mRealm;

    public PaGoalModel(IAuthDataManager authDataManager, Realm realm) {
        mAuthDataManager = authDataManager;
        mRealm = realm;
    }

    @Override
    public void setPaGoal(int paGoal, SetPaGoalResult result) {
        try {
            User user = mAuthDataManager.getLoggedInUser();
            mRealm.beginTransaction();
            user.setPaGoal(paGoal);
            mRealm.copyToRealmOrUpdate(user);
            mRealm.commitTransaction();

            result.onSuccess();
        } catch (Exception e) {
            result.onError(e.getMessage());
        }
    }
}
