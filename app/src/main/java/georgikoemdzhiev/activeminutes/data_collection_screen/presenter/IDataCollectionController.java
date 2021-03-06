package georgikoemdzhiev.activeminutes.data_collection_screen.presenter;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public interface IDataCollectionController {

    void startService();

    void startRecording();

    void stopService();

    void exportCollectedData();

    void clearCollectedData();

    void setActivityLabel(String label);
}
