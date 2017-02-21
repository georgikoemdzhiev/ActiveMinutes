package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;
import georgikoemdzhiev.activeminutes.har.common.data.TimeWindow;
import georgikoemdzhiev.activeminutes.har.common.data_preprocessing.DataPreprocessor;
import georgikoemdzhiev.activeminutes.har.common.data_preprocessing.IDataPreprocessor;
import weka.classifiers.lazy.IBk;

/**
 * Created by koemdzhiev on 10/02/2017.
 */

public abstract class HarManager implements IHarManager {
    // 3 Second time window
    protected static final long WINDOW_LENGTH = 3000;

    long windowBegTime = -1;
    TimeSeries accXSeries, accYSeries, accZSeries, accMSeries;
    TimeWindow window;
    IDataPreprocessor dataPrep;
    String activityLabel;
    IBk iBkClassifier;

    public HarManager() {
        this.accXSeries = new TimeSeries("accX_");
        this.accYSeries = new TimeSeries("accY_");
        this.accZSeries = new TimeSeries("accZ_");
        this.accMSeries = new TimeSeries("accM_");
        this.window = new TimeWindow();

        iBkClassifier = new IBk(3);

        dataPrep = new DataPreprocessor();

    }

    @Override
    public void feedData(float[] xyz, long timestamp) {
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
