package georgikoemdzhiev.activeminutes.har;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public class ClassifierBuilder implements IClassifierBuilder {
    private Classifier iBkClassifier;

    public ClassifierBuilder() {
        this.iBkClassifier = new IBk(3);
    }

    @Override
    public Classifier buildClassifier(Instances dataset) {
        if (dataset.size() != 0) {
            try {
                iBkClassifier.buildClassifier(dataset);
                System.out.println("Classifier is built successfully!");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could NOT build classifier! " + e.getMessage());
            }
        } else {
            System.out.println("No data provided to build a classifier!");
        }

        return this.iBkClassifier;
    }
}
