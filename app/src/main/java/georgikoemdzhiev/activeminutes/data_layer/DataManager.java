package georgikoemdzhiev.activeminutes.data_layer;

import java.util.ArrayList;

import georgikoemdzhiev.activeminutes.data_layer.db.TrainingData;
import io.realm.Realm;
import io.realm.RealmResults;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public class DataManager implements IDataManager {
    private static final String USER_ID = "userId";
    private static Instances INSTANCE_HEADER;

    private Realm realm;
    private IFileManager fileManager;


    public DataManager(Realm realm, IFileManager fileManager) {
        this.realm = realm;
        this.fileManager = fileManager;
        INSTANCE_HEADER = fileManager.readARFFFileSchema();
    }


    @Override
    public Instances getTrainingData(int userId) {
        RealmResults<TrainingData> realmResults = realm.where(TrainingData.class)
                .equalTo(USER_ID, userId)
                .findAll();
        return getWekaInstanceFrom(realmResults);
    }

    @Override
    public ArrayList<TrainingData> getTrainingDataAsList(int userId) {
        RealmResults<TrainingData> realmResults = realm.where(TrainingData.class)
                .equalTo(USER_ID, userId)
                .findAll();
        return getListFrom(realmResults);
    }

    @Override
    public void saveTrainingData(Instance instance, int userId) {
        double[] instanceValues = instance.toDoubleArray();
        realm.beginTransaction();
        TrainingData trainingDataInstance = realm.createObject(TrainingData.class);
        trainingDataInstance.setUserId(userId);
        trainingDataInstance.setValues(instanceValues);
        realm.commitTransaction();
        System.out.println("Instance saved to Database");
    }

    @Override
    public void deleteAllTrainingData() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }


    @Override
    public Instances getInstanceHeader() {
        return INSTANCE_HEADER;
    }

    @Override
    public void serialiseClassifierToFile(Classifier classifier) {
        // Serialise the classifier object and save it to a file for later use
        fileManager.serialiseAndStoreClassifier(classifier);
        // Save the data for the generic user to arff file as  well
        fileManager.saveToArffFile(getTrainingData(0));
    }


    @Override
    public Classifier deSerialiseClassifierFromFile() {
        return fileManager.deSerialiseClassifier();
    }


    private Instances getWekaInstanceFrom(RealmResults<TrainingData> realmResults) {
        Instances dataSet = new Instances(INSTANCE_HEADER);
        for (int p = 0; p < realmResults.size(); p++) {
            TrainingData currentDBInstance = realmResults.get(p);
            DenseInstance instance = new DenseInstance(INSTANCE_HEADER.numAttributes());
            instance.setDataset(INSTANCE_HEADER);
            instance.setClassValue(currentDBInstance.getValues()[currentDBInstance.getValues().length - 1]);

            for (int i = 0; i < INSTANCE_HEADER.numAttributes(); i++) {
                instance.setValue(i, currentDBInstance.getValues()[i]);
            }

            dataSet.add(instance);

        }
        return dataSet;
    }

    private ArrayList<TrainingData> getListFrom(RealmResults<TrainingData> realResults) {
        ArrayList<TrainingData> trainingDataList = new ArrayList<>();
        for (TrainingData td : realResults) {
            trainingDataList.add(td);
        }
        return trainingDataList;
    }

}
