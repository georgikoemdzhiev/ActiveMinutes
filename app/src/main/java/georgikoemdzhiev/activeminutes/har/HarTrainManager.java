package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.ITrainingDataManager;
import georgikoemdzhiev.activeminutes.har.common.data.Point;
import georgikoemdzhiev.activeminutes.har.common.feature.FeatureSet;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */

public class HarTrainManager extends HarManager {
    // OFFSET time that will "cut" the first 3 seconds from the start of the recording
    private final int TIME_OFFSET = 1;
    ITrainingDataManager mDataManager;
    private int time_offset_counter = 0;

    public HarTrainManager(ITrainingDataManager dataManager) {
        super();
        this.mDataManager = dataManager;
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

    @Override
    public void resetWindowBegTime() {
        windowBegTime = -1;
        time_offset_counter = 0;
    }


    public void applyTimeOffset() {
        mDataManager.deleteLastTrainingDataRecord(0);
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


    public ITrainingDataManager getDataManager() {
        return this.mDataManager;
    }

    @Override
    public void issueTimeWindow() {
        // extract features, convert the featureSet to weka instance object and add it to a list
        //Create a FeatureSet instance and use its toInstance method to create weka instance
        FeatureSet featureSet = null;
        Instance instance = null;
        try {
            featureSet = new FeatureSet(window);

            instance = featureSet.toInstance(mDataManager.getInstanceHeader(), activityLabel);
            // save this training instance with userId 0 (generic training data)
            mDataManager.saveTrainingData(instance, 0);
            System.out.println("FeatureSet.toString: " + featureSet.toString());
            System.out.println("FeatureSet.toInstance: " + instance);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Method that builds a classifier from a given dataset
     *
     * @param dataSet dataset that will be used for the classifier training
     */
    // TODO This method potentially would be replaced by the IClassifierBuilder variable
    private void buildClassifier(Instances dataSet, TrainClassifierResult result) {
        if (dataSet.size() != 0) {
            try {
                IBk iBkClassifier = new IBk();
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
