package georgikoemdzhiev.activeminutes.data_layer;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IFileManager {

    Instances readArffFileSchemaFromAssets();

    Instances readArffFileFromAssets();

    Instances readFromArffFileFromES();

    void saveToArffFile(Instances dataset);

    void serialiseClassifierAndStoreToSDCard(Classifier classifier);

    Classifier deSerialiseClassifierFromSDCard();
}
