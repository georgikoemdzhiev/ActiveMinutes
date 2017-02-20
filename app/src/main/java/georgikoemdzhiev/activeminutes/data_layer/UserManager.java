package georgikoemdzhiev.activeminutes.data_layer;

import android.content.SharedPreferences;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public class UserManager implements IUserManager {
    private final String LOGGED_USER = "logged_user";
    private final String IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences mSharedPreferences;

    public UserManager(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public int getLoggedInUserId() {
        return mSharedPreferences.getInt(LOGGED_USER, 0);
    }

    @Override
    public void setLoggedInUser(int userId) {
        mSharedPreferences.edit().putInt(LOGGED_USER, userId).apply();
        System.out.println("Logged user id = " + mSharedPreferences.getInt(LOGGED_USER, 0));
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    @Override
    public void setLoggedIn(boolean keepLoggedIn) {
        mSharedPreferences.edit().putBoolean(IS_LOGGED_IN, keepLoggedIn).apply();
    }
}
