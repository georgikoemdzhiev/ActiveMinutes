package georgikoemdzhiev.activeminutes.data_layer;

import android.content.SharedPreferences;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public class UserManager implements IUserManager {
    private final String LOGGED_USER = "logged_user";

    private SharedPreferences mSharedPreferences;

    public UserManager(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public int getLoggedInUser() {
        return mSharedPreferences.getInt(LOGGED_USER, 0);
    }

    @Override
    public void setLoggedInUser(int userId) {
        mSharedPreferences.edit().putInt(LOGGED_USER, userId).apply();
        System.out.println("Logged user id = " + mSharedPreferences.getInt(LOGGED_USER, 0));
    }
}
