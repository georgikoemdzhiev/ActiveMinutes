package georgikoemdzhiev.activeminutes.data_layer;

import georgikoemdzhiev.activeminutes.data_layer.db.User;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public class AuthDataManager implements IAuthDataManager {
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String USER_ID = "userId";
    private Realm mRealm;

    public AuthDataManager(Realm realm) {
        this.mRealm = realm;
    }

    @Override
    public void createNewUser(String username, String password, AuthResult result) {


        RealmResults<User> users = mRealm.where(User.class)
                .equalTo(USERNAME, username).findAll();
        if (users.size() == 0) {
            // Create new user
            mRealm.beginTransaction();
            User newUser = mRealm.createObject(User.class, getNextInt());
            newUser.setUsername(username);
            newUser.setPassword(password);
            mRealm.commitTransaction();
            result.onSuccess("new user created");
        } else {
            result.onError("Username already exists!");
        }

    }

    @Override
    public void login(String username, String password, AuthResult result) {
        RealmResults<User> users = mRealm.where(User.class)
                .equalTo(USERNAME, username)
                .equalTo(PASSWORD, password).findAll();
        if (users.size() == 0) {
            result.onError("Cannot find user with these credentials!");
        } else {
            result.onSuccess("Login successful");
        }
    }


    private int getNextInt() {
        int key;
        try {
            key = mRealm.where(User.class).max(USER_ID).intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException ex) {
            key = 0;
        }

        return key;
    }
}
