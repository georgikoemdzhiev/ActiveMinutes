package georgikoemdzhiev.activeminutes.data_collection_screen.model;

import georgikoemdzhiev.activeminutes.database.IDataManager;
import georgikoemdzhiev.activeminutes.database.NumOfInstResult;

/**
 * Created by Georgi Koemdzhiev on 13/02/2017.
 */

public class DataCollectionModel implements IDataCollectionModel {
    private IDataManager dataManager;

    public DataCollectionModel(IDataManager dataManager) {
        this.dataManager = dataManager;
    }

    /***
     * Method that returns the number of collected instances for the generic user (id = 0)
     *
     * @param result interface that is used to store the value of getAllTrainingInstances method
     */
    @Override
    public void getNumOfInstances(NumOfInstResult result) {
        result.onResult(dataManager.getAllTrainingInstances(0).size() + "");
    }
}
