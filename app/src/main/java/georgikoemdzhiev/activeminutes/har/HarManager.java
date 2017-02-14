package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.IDataManager;
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

public class HarManager implements IHarManager {
    // 3 Second time window
    private static final long WINDOW_LENGTH = 3000;
    // OFFSET time that will "cut" the first 3 seconds from the start of the recording
    private final int TIME_OFFSET = 1;
    private int time_offset_counter = 0;
    private long windowBegTime = -1;
    private TimeSeries accXSeries, accYSeries, accZSeries, accMSeries;
    private TimeWindow window;
    private String activityLabel;
    private IDataManager mDataManager;
    private Classifier iBkClassifier;
    private IDataPreprocessor dataPreprocessor;

    public HarManager(IDataManager dataManager) {
        this.accXSeries = new TimeSeries("accX_");
        this.accYSeries = new TimeSeries("accY_");
        this.accZSeries = new TimeSeries("accZ_");
        this.accMSeries = new TimeSeries("accM_");
        this.window = new TimeWindow();

        this.mDataManager = dataManager;

        iBkClassifier = new IBk(3);

        dataPreprocessor = new DataPreprocessor();

    }

    @Override
    public void feedData(float[] xyz, long timestamp) {
        // apply low-pass filter to remove earth's gravity force
        double[] noGravForce = dataPreprocessor.applyLowPassFilter(xyz);
        // add the data points to the appropriate lists..
        accXSeries.addPoint(new Point(timestamp, noGravForce[0]));
        accYSeries.addPoint(new Point(timestamp, noGravForce[1]));
        accZSeries.addPoint(new Point(timestamp, noGravForce[2]));
        accMSeries.addPoint(new Point(timestamp, calcMagnitude(noGravForce[0],
                noGravForce[1], noGravForce[2])));

        // Check if 3 seconds have passed...
        if (System.currentTimeMillis() - windowBegTime > WINDOW_LENGTH) {
            if (windowBegTime > 0) {
                // Check if to start recording checking the time offset
                if (time_offset_counter >= TIME_OFFSET) {
                    window.addTimeSeries(accXSeries);
                    window.addTimeSeries(accYSeries);
                    window.addTimeSeries(accZSeries);
                    window.addTimeSeries(accMSeries);

                    System.out.println("Time Window Issued!");
                    issueTimeWindow();
                    resetTimeSeries();
                } else {
                    time_offset_counter++;
                }
            }

            windowBegTime = System.currentTimeMillis();
        }
    }

    private void resetTimeSeries() {
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
        time_offset_counter = 0;
    }

    private double calcMagnitude(double x, double y, double z) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
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
        if (userOwnDataSet.size() == 0) {
            result.onError("Cannot train classifier with empty dataset!");
        } else {
            System.out.println("Building personalised classifier for user with id= " +
                    userId + " data set used:" + userOwnDataSet.toString());
            buildClassifier(userOwnDataSet);
            result.onSuccess();
        }

    }

    /***
     * Method that builds a generic classifier
     */
    @Override
    public void trainAndSaveGenericClassifier() {
        Instances genericDataSet = mDataManager.getTrainingData(0);
        System.out.println("Building generic classifier. Data set used:" + genericDataSet.toString());
        buildClassifier(genericDataSet);
    }

    @Override
    public IDataManager getDataManager() {
        return this.mDataManager;
    }

    @Override
    public void applyTimeOffset() {
        mDataManager.deleteLastTrainingDataRecord(0);
    }

    /***
     * Method that builds a classifier from a given dataset
     *
     * @param dataSet dataset that will be used for the classifier training
     */
    private void buildClassifier(Instances dataSet) {
        try {
            iBkClassifier.buildClassifier(dataSet);
            mDataManager.serialiseClassifierToFile(iBkClassifier);
            System.out.println("Classifier build successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
