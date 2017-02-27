package georgikoemdzhiev.activeminutes.initial_setup_screen.model;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */
public interface SetResult {

    void onSuccess();

    void onError(String message);
}
