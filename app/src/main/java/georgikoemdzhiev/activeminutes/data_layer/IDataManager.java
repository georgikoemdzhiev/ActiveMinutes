package georgikoemdzhiev.activeminutes.data_layer;

import java.util.ArrayList;

import georgikoemdzhiev.activeminutes.data_layer.db.TrainingData;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IDataManager {

    Instances getInstances(int userId);

    Instances getAll();

    void saveInstance(Instance instance, int userId);

    ArrayList<TrainingData> getInstancesAsList(int userId);

    Instances getInstanceHeader();

    void serialiseClassifierToFile(Classifier classifier);


    Classifier deSerialiseClassifierFromFile();


}
