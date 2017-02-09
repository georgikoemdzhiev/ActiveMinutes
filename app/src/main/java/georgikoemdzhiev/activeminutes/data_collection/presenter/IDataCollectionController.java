package georgikoemdzhiev.activeminutes.data_collection.presenter;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public interface IDataCollectionController {

    void startService();

    void stopService();

    void exportCollectedData();

    void clearCollectedData();
}
