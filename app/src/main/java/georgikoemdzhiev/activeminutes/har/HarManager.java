package georgikoemdzhiev.activeminutes.har;

import java.util.Observable;

import georgikoemdzhiev.activeminutes.har.common.data.Point;
import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;
import georgikoemdzhiev.activeminutes.har.common.data.TimeWindow;
import georgikoemdzhiev.activeminutes.har.common.data_preprocessing.DataPreprocessor;
import georgikoemdzhiev.activeminutes.har.common.data_preprocessing.IDataPreprocessor;

/**
 * Created by koemdzhiev on 10/02/2017.
 */

public abstract class HarManager extends Observable implements IHarManager {
    // 3 Second time window
    protected static final long WINDOW_LENGTH = 3000;

    long windowBegTime = -1;
    TimeSeries accXSeries, accYSeries, accZSeries, accMSeries;
    TimeWindow window;
    IDataPreprocessor dataPrep;
    String activityLabel;


    public HarManager() {
        this.accXSeries = new TimeSeries("accX_");
        this.accYSeries = new TimeSeries("accY_");
        this.accZSeries = new TimeSeries("accZ_");
        this.accMSeries = new TimeSeries("accM_");
        this.window = new TimeWindow();

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
                window.addTimeSeries(accXSeries);
                window.addTimeSeries(accYSeries);
                window.addTimeSeries(accZSeries);
                window.addTimeSeries(accMSeries);

//                System.out.println("Adding data time window");

                issueTimeWindow();
                resetTimeSeries();
            }

            windowBegTime = System.currentTimeMillis();
        }
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

    }

    /***
     * Method that resets window begin time & time offset counter
     */
    public void resetWindowBegTime() {
        windowBegTime = -1;
    }

}
