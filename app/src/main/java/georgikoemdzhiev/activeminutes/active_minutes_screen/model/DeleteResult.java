package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

/**
 * Created by Georgi Koemdzhiev on 02/04/2017.
 */

public interface DeleteResult {
    void onSuccess(String message);

    void onError(String message);
}
