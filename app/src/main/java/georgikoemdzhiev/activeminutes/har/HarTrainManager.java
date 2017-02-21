package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.ITrainingDataManager;
import georgikoemdzhiev.activeminutes.har.common.data.Point;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */

public class HarTrainManager extends HarManager {
    // OFFSET time that will "cut" the first 3 seconds from the start of the recording
    private final int TIME_OFFSET = 1;
    private int time_offset_counter = 0;

    public HarTrainManager(ITrainingDataManager dataManager) {
        super(dataManager);
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
}
