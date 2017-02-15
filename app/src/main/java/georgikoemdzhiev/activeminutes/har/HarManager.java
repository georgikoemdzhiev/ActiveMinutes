package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.IHarDataManager;
import georgikoemdzhiev.activeminutes.har.common.data.Point;
import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;
import georgikoemdzhiev.activeminutes.har.common.data.TimeWindow;
import georgikoemdzhiev.activeminutes.har.common.data_preprocessing.DataPreprocessor;
import georgikoemdzhiev.activeminutes.har.common.data_preprocessing.IDataPreprocessor;
import georgikoemdzhiev.activeminutes.har.common.feature.FeatureSet;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by koemdzhiev on 10/02/2017.
 */

public abstract class HarManager implements IHarManager {
    // 3 Second time window
    protected static final long WINDOW_LENGTH = 3000;

    long windowBegTime = -1;
    TimeSeries accXSeries, accYSeries, accZSeries, accMSeries;
    TimeWindow window;
    IHarDataManager mDataManager;
    IDataPreprocessor dataPrep;
    private String activityLabel;
    private Classifier iBkClassifier;

    public HarManager(IHarDataManager dataManager) {
        this.accXSeries = new TimeSeries("accX_");
        this.accYSeries = new TimeSeries("accY_");
        this.accZSeries = new TimeSeries("accZ_");
        this.accMSeries = new TimeSeries("accM_");
        this.window = new TimeWindow();

        this.mDataManager = dataManager;

        iBkClassifier = new IBk(3);

        dataPrep = new DataPreprocessor();

    }

    @Override
    public void feedData(float[] xyz, long timestamp) {
        // apply low-pass filter to remove earth's gravity force
        double[] noGravForce = dataPrep.applyLowPassFilter(xyz);
        // add the data points to the appropriate lists..
        accXSeries.addPoint(new Point(timestamp, noGravForce[0]));
        accYSeries.addPoint(new Point(timestamp, noGravForce[1]));
        accZSeries.addPoint(new Point(timestamp, noGravForce[2]));
        accMSeries.addPoint(new Point(timestamp, dataPrep.calculateMagnitude(noGravForce[0],
                noGravForce[1], noGravForce[2])));

        // Check if 3 seconds have passed...
        if (System.currentTimeMillis() - windowBegTime > WINDOW_LENGTH) {
            if (windowBegTime > 0) {
                // Check if to start recording checking the time offset
                window.addTimeSeries(accXSeries);
                window.addTimeSeries(accYSeries);
                window.addTimeSeries(accZSeries);
                window.addTimeSeries(accMSeries);

                System.out.println("Time Window Issued!");
                issueTimeWindow();
                resetTimeSeries();
            }
        }

        windowBegTime = System.currentTimeMillis();

    }

    public void resetTimeSeries() {
        this.accXSeries.clear();
        this.accYSeries.clear();
        this.accZSeries.clear();
        this.accMSeries.clear();
        this.window.clear();
    }


    @Override
    public void issueTimeWindow() {
        // extract features, convert the featureSet to weka instance object and add it to a list
        //Create a FeatureSet instance and use its toInstance method to create weka instance
        FeatureSet featureSet = null;
        Instance instance = null;
        try {
            featureSet = new FeatureSet(window);
            featureSet.setActivityLabel(activityLabel);

            instance = featureSet.toInstance(mDataManager.getInstanceHeader());
            // save this training instance with userId 0 (generic training data)
            mDataManager.saveTrainingData(instance, 0);
            System.out.println("FeatureSet.toString: " + featureSet.toString());
            System.out.println("FeatureSet.toInstance: " + instance);

            // TODO maybe setup a HarManager classification and classify the instance here...

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * Method that resets window begin time & time offset counter
     */
    public void resetWindowBegTime() {
        windowBegTime = -1;
    }


    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    /***
     * Method that builds a personalised classifier using user's own data
     *
     * @param userId id of the user to be used to retrieve the user's collected training data
     */
    @Override
    public void trainAndSavePersonalisedClassifier(int userId, TrainClassifierResult result) {
        Instances userOwnDataSet = mDataManager.getTrainingData(userId);
        buildClassifier(userOwnDataSet, result);
    }

    /***
     * Method that builds a generic classifier
     */
    @Override
    public void trainAndSaveGenericClassifier(TrainClassifierResult result) {
        Instances genericDataSet = mDataManager.getTrainingData(0);
        buildClassifier(genericDataSet, result);
    }

    @Override
    public IHarDataManager getDataManager() {
        return this.mDataManager;
    }


    /***
     * Method that builds a classifier from a given dataset
     *
     * @param dataSet dataset that will be used for the classifier training
     */
    private void buildClassifier(Instances dataSet, TrainClassifierResult result) {
        if (dataSet.size() != 0) {
            try {
                iBkClassifier.buildClassifier(dataSet);
                mDataManager.serialiseClassifierToFile(iBkClassifier);
                result.onSuccess("Classifier is built successfully!");

            } catch (Exception e) {
                e.printStackTrace();
                result.onError("Could NOT build classifier! " + e.getMessage());
            }
        } else {
            result.onError("No data provided to build a classifier!");
        }
    }
}
