package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.IClassificationDataManager;
import georgikoemdzhiev.activeminutes.har.common.feature.FeatureSet;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */

public class HarClassifyManager extends HarManager {
    private IClassificationDataManager mDataManager;
    private Classifier iBkClassifier;

    public HarClassifyManager(IClassificationDataManager dataManager) {
        super();
        this.mDataManager = dataManager;
        this.iBkClassifier = mDataManager.deSerialiseClassifierFromFile();
    }


    @Override
    public void issueTimeWindow() {
        System.out.println("issueTimeWindow called(). accXSeriex size: " + accXSeries.size() + "accYSize: " + accYSeries.size());
        FeatureSet featureSet = null;
        Instance instance = null;
        try {
            featureSet = new FeatureSet(window);
            // empty activity label -> classIsMissing...
            instance = featureSet.toInstance(mDataManager.getInstanceHeader(), "");

            double activityClass = iBkClassifier.classifyInstance(instance);

            System.out.println("FeatureSet.toString: " + featureSet.toString());
            System.out.println("FeatureSet.toInstance: " + instance);
            System.out.println("Activity Class: " + activityClass);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception during classification! " + e.getMessage());
        }
    }

    @Override
    public void setActivityLabel(String activityLabel) {

    }

    @Override
    public void trainAndSavePersonalisedClassifier(int userId, TrainClassifierResult result) {

    }

    @Override
    public void trainAndSaveGenericClassifier(TrainClassifierResult result) {

    }

}
