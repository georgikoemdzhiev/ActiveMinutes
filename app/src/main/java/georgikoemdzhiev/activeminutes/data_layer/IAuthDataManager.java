package georgikoemdzhiev.activeminutes.data_layer;

import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public interface IAuthDataManager {

    void createNewUser(String username, String password, AuthResult result);

    void login(String username, String password, AuthResult result);

    User getLoggedInUser();

    void logOutUser();

    IUserManager getUserManager();
}
