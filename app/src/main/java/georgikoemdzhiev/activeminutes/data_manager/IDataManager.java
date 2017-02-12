package georgikoemdzhiev.activeminutes.data_manager;

import weka.core.Instance;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IDataManager {

    void readInstancesFromDB();

    void saveInstanceToDB(Instance instance);
}
