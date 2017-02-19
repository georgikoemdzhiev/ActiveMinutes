package georgikoemdzhiev.activeminutes.har;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public interface IClassifierBuilder {

    Classifier buildClassifier(Instances dataset);

}
