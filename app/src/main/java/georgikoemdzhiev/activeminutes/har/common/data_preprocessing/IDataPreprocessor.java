package georgikoemdzhiev.activeminutes.har.common.data_preprocessing;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public interface IDataPreprocessor {

    double[] applyLowPassFilter(float[] xyzRawData);

    double calculateMagnitude(double x, double y, double z);
}
