package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.ITrainingDataManager;

/**
 * Created by koemdzhiev on 10/02/2017.
 */

public interface IHarManager {

    void feedData(float[] xyz, long timestamp);

    void issueTimeWindow();

    void resetWindowBegTime();

    void resetTimeSeries();

    void setActivityLabel(String activityLabel);

    void trainAndSavePersonalisedClassifier(int userId, TrainClassifierResult result);

    void trainAndSaveGenericClassifier(TrainClassifierResult result);

    ITrainingDataManager getDataManager();

}
