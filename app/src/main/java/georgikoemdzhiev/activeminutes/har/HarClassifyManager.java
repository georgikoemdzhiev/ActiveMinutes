package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.IClassificationDataManager;
import georgikoemdzhiev.activeminutes.har.common.data.Point;
import georgikoemdzhiev.activeminutes.har.common.feature.FeatureSet;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */

public class HarClassifyManager extends HarManager {
    private IClassificationDataManager mDataManager;

    public HarClassifyManager(IClassificationDataManager dataManager) {
        super();
        this.mDataManager = dataManager;
        this.iBkClassifier = (IBk) mDataManager.deSerialiseClassifierFromFile();
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
//        counter++;
//        System.out.println("Adding data to lists..." + counter);
        // Check if 3 seconds have passed...
        if (System.currentTimeMillis() - windowBegTime > WINDOW_LENGTH) {
            if (windowBegTime > 0) {
                window.addTimeSeries(accXSeries);
                window.addTimeSeries(accYSeries);
                window.addTimeSeries(accZSeries);
                window.addTimeSeries(accMSeries);

                System.out.println("Adding data time window");

                issueTimeWindow();
                resetTimeSeries();
            }

            windowBegTime = System.currentTimeMillis();
        }
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
