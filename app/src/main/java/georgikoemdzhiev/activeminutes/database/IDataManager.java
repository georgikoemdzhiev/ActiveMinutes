package georgikoemdzhiev.activeminutes.database;

import georgikoemdzhiev.activeminutes.database.db.TrainingData;
import io.realm.RealmResults;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IDataManager {

    Instances readInstancesFromDB(int userId);

    Instances readInstancesFromDB();

    void saveInstanceToDB(Instance instance, int userId);

    void saveInstanceToDB(Instance instance);

    RealmResults<TrainingData> getAllTrainingInstances(int userId);

    void saveInstancesToDB(Instances instances, int userId);

    void saveInstancesToDB(Instances instances);

    Instances getInstanceHeader();

    void serialiseClassifierToFile(Classifier classifier);

    Classifier deSerialiseClassifierFromFile();


}
