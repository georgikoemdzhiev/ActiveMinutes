package georgikoemdzhiev.activeminutes.data_layer;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public interface IClassificationDataManager {

    Instances getInstanceHeader();

    Classifier deSerialiseClassifierFromFile();
}
