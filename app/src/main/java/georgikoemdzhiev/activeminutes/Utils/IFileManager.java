package georgikoemdzhiev.activeminutes.Utils;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IFileManager {
    void saveCurrentDataToArffFile(Instances instances, String activityLabel);

    Instances readARFFFileSchema();

    void serialiseAndStoreClassifier(Classifier classifier);

    Classifier deSerialiseClassifier();
}
