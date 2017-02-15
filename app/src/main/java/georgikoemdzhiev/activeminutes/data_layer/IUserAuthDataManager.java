package georgikoemdzhiev.activeminutes.data_layer;

import georgikoemdzhiev.activeminutes.login_screen.AuthResult;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public interface IUserAuthDataManager {

    void createNewUser(String username, String password, AuthResult result);

    void login(String username, String password, AuthResult result);
}
