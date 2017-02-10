package georgikoemdzhiev.activeminutes.har.common.feature;

import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;

/**
 * Created by Georgi Koemdzhiev on 10/02/2017.
 */

public abstract class FeatureExtractor {
    //** The eries features will be extracted from */
    protected TimeSeries series;

    //** The set of features extracted from the given time series */
    protected FeatureSet featureSet;

    public FeatureExtractor(TimeSeries series, String activityLabel) {
        this.series = series;
        this.featureSet = new FeatureSet(activityLabel);
    }

}