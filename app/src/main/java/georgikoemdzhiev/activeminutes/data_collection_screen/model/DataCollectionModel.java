package georgikoemdzhiev.activeminutes.data_collection_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.IHarDataManager;
import georgikoemdzhiev.activeminutes.data_layer.NumOfInstResult;

/**
 * Created by Georgi Koemdzhiev on 13/02/2017.
 */

public class DataCollectionModel implements IDataCollectionModel {
    private IHarDataManager dataManager;

    public DataCollectionModel(IHarDataManager dataManager) {
        this.dataManager = dataManager;
    }

    /***
     * Method that returns the number of collected instances for the generic user (id = 0)
     *
     * @param result interface that is used to store the value of getAllTrainingInstancesForAllUsers method
     */
    @Override
    public void getNumOfInstances(NumOfInstResult result) {
        result.onResult(dataManager.getTrainingDataAsList(0).size() + "");
    }
}
