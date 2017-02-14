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

    Instances getTrainingData(int userId);

    ArrayList<TrainingData> getTrainingDataAsList(int userId);

    void saveTrainingData(Instance instance, int userId);

    void deleteAllTrainingData();

    void deleteLastTrainingDataRecord(int userId);

    Instances getInstanceHeader();

    void serialiseClassifierToFile(Classifier classifier);

    Classifier deSerialiseClassifierFromFile();


}
