package georgikoemdzhiev.activeminutes.data_layer;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public interface IAuthDataManager {

    void createNewUser(String username, String password, AuthResult result);

    void login(String username, String password, AuthResult result);
}
