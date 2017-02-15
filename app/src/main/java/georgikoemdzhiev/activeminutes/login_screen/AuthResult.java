package georgikoemdzhiev.activeminutes.login_screen;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */
public interface AuthResult {

    void onSuccess(String message);

    void onError(String message);
}
