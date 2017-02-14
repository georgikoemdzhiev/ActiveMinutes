package georgikoemdzhiev.activeminutes.har;

/**
 * Created by Georgi Koemdzhiev on 13/02/2017.
 */
public interface TrainClassifierResult {

    void onSuccess(String message);

    void onError(String message);
}
