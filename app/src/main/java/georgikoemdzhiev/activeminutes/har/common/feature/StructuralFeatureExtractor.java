package georgikoemdzhiev.activeminutes.har.common.feature;

import org.jtransforms.fft.DoubleFFT_1D;

import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;

/**
 * Created by Georgi Koemdzhiev on 10/02/2017.
 */

public class StructuralFeatureExtractor extends FeatureExtractor {
    private double[] first5FFTCoefficients = null;

    public StructuralFeatureExtractor(TimeSeries series) {
        super(series);
        double[] tempArray = series.asArrayOfPointValues();

        DoubleFFT_1D fft = new DoubleFFT_1D(series.size());
        fft.realForward(tempArray);

        double[] first5 = new double[5];

        for (int i = 0; i < first5.length; i++) {
            // round to second decimal place... Avoid the first value
            first5[i] = Math.round(tempArray[i + 1] * 100.0) / 100.0;
        }
        first5FFTCoefficients = first5;
    }


    public double[] computeFirst5FFTCoefficients() {
        return first5FFTCoefficients;
    }
}
