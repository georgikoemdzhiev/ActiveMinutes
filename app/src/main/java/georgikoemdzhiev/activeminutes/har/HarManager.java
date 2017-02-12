package georgikoemdzhiev.activeminutes.har;

import android.util.Log;

import georgikoemdzhiev.activeminutes.Utils.IFileManager;
import georgikoemdzhiev.activeminutes.har.common.data.Point;
import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;
import georgikoemdzhiev.activeminutes.har.common.data.TimeWindow;
import georgikoemdzhiev.activeminutes.har.common.feature.FeatureSet;
import weka.core.Instances;

import static android.content.ContentValues.TAG;

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

    private Instances instanceHeader;
    private Instances dataSet;

    private IFileManager mFileManager;

    public HarManager(IFileManager fileManager) {
        this.accXSeries = new TimeSeries("accX_");
        this.accYSeries = new TimeSeries("accY_");
        this.accZSeries = new TimeSeries("accZ_");
        this.accMSeries = new TimeSeries("accM_");
        this.window = new TimeWindow();
        this.mFileManager = fileManager;
        instanceHeader = mFileManager.readARFFFileSchema();
        dataSet = instanceHeader;
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
        // use the classifier to classify the instance (TODO)
        FeatureSet featureSet = null;
        try {
            featureSet = new FeatureSet(window);
            featureSet.setActivityLabel(activityLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "FeatureSet.toString: " + featureSet.toString());
        Log.d(TAG, "FeatureSet.toInstance: " + featureSet.toInstance(this.instanceHeader));

        // TODO save the instance in the DataBase instead?
//        dataSet.add(featureSet.toInstance(this.instanceHeader));

        //set the numberOfInstances view to the current dataSet size
//        mNumberOfInstancesView.setText(dataSet.size() + "");
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
}
