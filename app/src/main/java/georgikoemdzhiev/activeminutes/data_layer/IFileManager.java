package georgikoemdzhiev.activeminutes.data_layer;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IFileManager {

    Instances readArffFileSchema();

    Instances readFromArffFile();

    void saveToArffFile(Instances dataset);

    void serialiseAndStoreClassifier(Classifier classifier);

    Classifier deSerialiseClassifier();
}
