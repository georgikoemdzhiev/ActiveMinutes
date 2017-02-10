package georgikoemdzhiev.activeminutes.har.common.feature;

import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;

/**
 * Created by Georgi Koemdzhiev on 10/02/2017.
 */

public class StructuralFeatureExtractor extends FeatureExtractor {
    private double[] first5FFTCoefficients = null;

    public StructuralFeatureExtractor(TimeSeries series, String activityLabel) {
        super(series, activityLabel);
    }

    public FeatureSet computeFeatures() throws Exception {


//        featureSet.put("fft1_" + series.getId(), mean);
//        featureSet.put("fft2_" + series.getId(), computeSTDV());
//        featureSet.put("fft3_" + series.getId(), computeRMS());
//        featureSet.put("fft4_" + series.getId(), computeMAD());
//        featureSet.put("fft5_" + series.getId(), computeVariance());

        return featureSet;
    }

    public double[] computeFirst5FFTCoefficients() {
        // TODO change this MOCK data
        return new double[]{1, 2, 3, 4, 5};
    }
}
