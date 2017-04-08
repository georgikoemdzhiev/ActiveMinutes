package georgikoemdzhiev.activeminutes.har.common.data_preprocessing;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public class DataPreprocessor implements IDataPreprocessor {

    @Override
    public double[] applyLowPassFilter(float[] xyzRawData) {
        // alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
        final float alpha = 0.8f;
        final double[] gravity = new double[3];
        final double[] linear_acc = new double[3];
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * xyzRawData[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * xyzRawData[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * xyzRawData[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acc[0] = xyzRawData[0] - gravity[0];
        linear_acc[1] = xyzRawData[1] - gravity[1];
        linear_acc[2] = xyzRawData[2] - gravity[2];
        return linear_acc;
    }

    @Override
    public double calculateMagnitude(double x, double y, double z) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public static Instances selectAttributes(Instances data) {
        final AttributeSelection filter = new AttributeSelection();
        Instances filteredIns = null;
        // Evaluator
        final CfsSubsetEval evaluator = new CfsSubsetEval();
        evaluator.setMissingSeparate(true);
        // Assign evaluator to filter
        filter.setEvaluator(evaluator);
        // Search strategy: best first (default values)
        final BestFirst search = new BestFirst();
        filter.setSearch(search);
        // Apply filter
        try {
            filter.setInputFormat(data);
            filteredIns = Filter.useFilter(data, filter);
            System.out.println("Successfully applied feature selection algorithm!");
        } catch (Exception e) {
            System.out.println("Error when resampling input data with selected attributes!");
            e.printStackTrace();
        }
        return filteredIns;
    }
}
