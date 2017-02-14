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

    Instances getTrainingInstances(int userId);

    ArrayList<TrainingData> getTrainingInstancesAsList(int userId);

    void saveTrainingInstance(Instance instance, int userId);

    Instances getInstanceHeader();

    void serialiseClassifierToFile(Classifier classifier);

    Classifier deSerialiseClassifierFromFile();


}
