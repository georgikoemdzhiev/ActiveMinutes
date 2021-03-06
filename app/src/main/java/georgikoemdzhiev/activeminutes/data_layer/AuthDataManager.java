package georgikoemdzhiev.activeminutes.data_layer;

import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.data_layer.results.AuthResult;
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
    private IUserManager userManager;

    public AuthDataManager(Realm realm, IUserManager userManager) {
        this.mRealm = realm;
        this.userManager = userManager;
    }

    @Override
    public void createNewUser(String username, String password, AuthResult result) {

        RealmResults<User> users = mRealm.where(User.class)
                .equalTo(USERNAME, username).findAll();
        if (users.size() == 0) {
            // Create new user
            mRealm.beginTransaction();
            int nextUserId = getNextInt();
            User newUser = mRealm.createObject(User.class, nextUserId);
            newUser.setUsername(username);
            newUser.setPassword(password);
            mRealm.commitTransaction();

            userManager.setLoggedInUser(nextUserId);
            userManager.setLoggedIn(true);
            result.onSuccess("new user created. Id = " + nextUserId);
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

            userManager.setLoggedInUser(users.get(0).getUserId());
            userManager.setLoggedIn(true);
//            System.out.println("Logged user id = " + userManager.getLoggedInUserId());
        }
    }

    @Override
    public User getLoggedInUser() {
        mRealm.beginTransaction();
        User user = mRealm.where(User.class)
                .equalTo(USER_ID, userManager.getLoggedInUserId())
                .findFirst();
        mRealm.commitTransaction();
        return user;
    }

    @Override
    public boolean isUserLoggedIn() {
        return userManager.isLoggedIn();
    }

    @Override
    public void logOutUser() {
        userManager.setLoggedIn(false);
    }

    @Override
    public void setInitialSetupCompleted() {
        User user = getLoggedInUser();
        mRealm.beginTransaction();
        user.setIsFirstTime(false);
        mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();

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
