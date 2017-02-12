package georgikoemdzhiev.activeminutes.database;

import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IDataManager {

    Instances readInstancesFromDB();

    void saveInstanceToDB(Instance instance);

    void saveInstancesToDB(Instances instances);

    Instances getInstanceHeader();

}
