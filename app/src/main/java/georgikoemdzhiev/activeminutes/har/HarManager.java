package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.Utils.IFileManager;
import georgikoemdzhiev.activeminutes.database.IDataManager;
import georgikoemdzhiev.activeminutes.har.common.data.Point;
import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;
import georgikoemdzhiev.activeminutes.har.common.data.TimeWindow;
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
    private long windowBegTime = -1;

    private TimeSeries accXSeries, accYSeries, accZSeries, accMSeries;
    private TimeWindow window;

    private String activityLabel;

    private IDataManager mDataManager;

    private Instances dataSet;
    private Classifier iBkClassifier;

    public HarManager(IFileManager fileManager, IDataManager dataManager) {
        this.accXSeries = new TimeSeries("accX_");
        this.accYSeries = new TimeSeries("accY_");
        this.accZSeries = new TimeSeries("accZ_");
        this.accMSeries = new TimeSeries("accM_");
        this.window = new TimeWindow();

        this.mDataManager = dataManager;

        iBkClassifier = new IBk(3);

    }

    @Override
    public void feedData(float[] xyz, long timestamp) {
        double[] xyzNoGf = removeGravityForce(xyz);
        accXSeries.addPoint(new Point(timestamp, xyzNoGf[0]));
        accYSeries.addPoint(new Point(timestamp, xyzNoGf[1]));
        accZSeries.addPoint(new Point(timestamp, xyzNoGf[2]));
        accMSeries.addPoint(new Point(timestamp, calcMagnitude(xyzNoGf[0], xyzNoGf[1], xyzNoGf[2])));

        if (System.currentTimeMillis() - windowBegTime > WINDOW_LENGTH) {
            if (windowBegTime > 0) {
                window.addTimeSeries(accXSeries);
                window.addTimeSeries(accYSeries);
                window.addTimeSeries(accZSeries);
                window.addTimeSeries(accMSeries);

                System.out.println("Time Window Issued!");
                issueTimeWindow();
                resetTimeSeries();
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

    private double[] removeGravityForce(float[] xyz) {
        // alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        final float alpha = 0.8f;
        final double[] gravity = new double[3];
        final double[] linear_acc = new double[3];
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * xyz[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * xyz[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * xyz[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acc[0] = xyz[0] - gravity[0];
        linear_acc[1] = xyz[1] - gravity[1];
        linear_acc[2] = xyz[2] - gravity[2];

        return linear_acc;
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

            mDataManager.saveInstanceToDB(instance);
            System.out.println("FeatureSet.toString: " + featureSet.toString());
            System.out.println("FeatureSet.toInstance: " + instance);

            // TODO maybe setup a HarManager classification and classify the instance here...

        } catch (Exception e) {
            e.printStackTrace();
        }

//        dataSet.add(featureSet.toInstance(this.INSTANCE_HEADER));
    }

    public void resetWindowBegTime() {
        windowBegTime = -1;
    }

    private double calcMagnitude(double x, double y, double z) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    @Override
    public void trainClassifier() {
        dataSet = mDataManager.readInstancesFromDB();
        System.out.println("DATASET TO BE USED FOR TRAINING: " + dataSet.toString());

        try {
            iBkClassifier.buildClassifier(dataSet);
            System.out.println("Classifier build successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
