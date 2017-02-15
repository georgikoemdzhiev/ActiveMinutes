package georgikoemdzhiev.activeminutes.authentication_screen.presenter;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public interface IUserAuthPresenter {

    void login(String username, String password);

    void signUp(String username, String password);
}
