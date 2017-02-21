package georgikoemdzhiev.activeminutes.data_layer;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class ClassificationDataManager implements IClassificationDataManager {
    private Instances INSTANCE_HEADER;
    private IFileManager mFileManager;

    public ClassificationDataManager(IFileManager fileManager) {
        this.mFileManager = fileManager;
        INSTANCE_HEADER = fileManager.readArffFileSchemaFromAssets();
    }

    @Override
    public Instances getInstanceHeader() {
        return INSTANCE_HEADER;
    }

    @Override
    public Classifier deSerialiseClassifierFromFile() {
        return mFileManager.deSerialiseClassifierFromSDCard();
    }
}
