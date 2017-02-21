package georgikoemdzhiev.activeminutes.har.common.feature;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import georgikoemdzhiev.activeminutes.har.common.data.TimeSeries;
import georgikoemdzhiev.activeminutes.har.common.data.TimeWindow;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 10/02/2017.
 */

public class FeatureSet extends Hashtable<String, Double> {


    public FeatureSet() {

    }

    public FeatureSet(double[] values, String[] attributes) {
        if ((values == null) || (attributes == null) || (values.length) != (attributes.length)) {
            throw new IllegalArgumentException("Invalid arguments to create a feature set");
        }
        for (int i = 0; i < values.length; i++) {
            setAttribute(attributes[i], values[i]);
        }
    }

    /**
     * Creates a new feature set by extracting all features from the given time window
     */
    public FeatureSet(TimeWindow window) throws Exception {
        if (window == null) {
            throw new IllegalArgumentException("Cannot extract features from a null time window!");
        }

        Set<String> keys = window.keySet();
        for (String key : keys) {
            TimeSeries series = window.getTimeSeries(key);


            /** Compute some structural features */
            StructuralFeatureExtractor str = new StructuralFeatureExtractor(series);
            double[] first5FFTCoefficients = str.computeFirst5FFTCoefficients();

            /** Add them to the feature set */
            String id = series.getId();
            this.put(id + "_fft1", first5FFTCoefficients[0]);
            this.put(id + "_fft2", first5FFTCoefficients[1]);
            this.put(id + "_fft3", first5FFTCoefficients[2]);
            this.put(id + "_fft4", first5FFTCoefficients[3]);
            this.put(id + "_fft5", first5FFTCoefficients[4]);

        }
    }

    public void addFeatures(FeatureSet featureSet) {
        for (String name : featureSet.keySet()) {
            Double value = featureSet.get(name);
            this.put(name, value);
        }
    }

    public Double getValue(String attName) {
        return super.get(attName);
    }

    public void setAttribute(String attName, Double value) {
        super.put(attName, value);
    }

    /**
     * Converts a FeatureSet to WEKA Instance
     * Parameter INSTANCE_HEADER is an Instances object containing the attributes that the Instance should have.
     */

    public Instance toInstance(Instances instanceHeader, String activityLabel) {
        DenseInstance instance = null;
        if (instanceHeader != null) {
            instance = new DenseInstance(instanceHeader.numAttributes());
            instance.setDataset(instanceHeader);

            if (activityLabel.equals("")) {
                instance.setClassMissing();
            } else {
                instance.setClassValue(activityLabel);
            }


            Enumeration e = instanceHeader.enumerateAttributes();
            while (e.hasMoreElements()) {
                Attribute attr = (Attribute) e.nextElement();
                if (this.containsKey(attr.name())) {
                    if (attr.isNominal()) {
                        instance.setValue(attr, "" + this.getValue(attr.name()));
                    } else {
                        instance.setValue(attr, this.getValue(attr.name()));
                    }
                } else {
                    /* Attributes not found in this featureSet are set to zero.
                     * Otherwise the classifier cannot evaluate the instance */
                    if (attr.isNominal()) {
                        instance.setValue(attr, "0.0");
                    } else {
                        instance.setValue(attr, 0);
                    }
                }

            }
        }
        return instance;
    }


}
