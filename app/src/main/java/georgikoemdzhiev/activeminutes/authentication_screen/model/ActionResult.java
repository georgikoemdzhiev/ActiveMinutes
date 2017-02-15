package georgikoemdzhiev.activeminutes.authentication_screen.model;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */
public interface ActionResult {
    void onSuccess(String message);

    void onError(String message);
}
