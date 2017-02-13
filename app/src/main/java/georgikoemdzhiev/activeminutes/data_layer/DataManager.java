package georgikoemdzhiev.activeminutes.data_layer;

import georgikoemdzhiev.activeminutes.Utils.IFileManager;
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
    public Instances readInstancesFromDB(int userId) {
        realm.beginTransaction();
        RealmResults<TrainingData> realmResults = realm.where(TrainingData.class)
                .equalTo(USER_ID, userId)
                .findAll();

        System.out.println("RealmResults: " + realmResults.toString());

        Instances dataSet = toWekaInstance(realmResults);

        realm.commitTransaction();
        return dataSet;
    }

    @Override
    public Instances readInstancesFromDB() {
        realm.beginTransaction();
        RealmResults<TrainingData> realmResults = realm.where(TrainingData.class)
                .equalTo(USER_ID, 0)
                .findAll();
        System.out.println("RealmResults: " + realmResults.toString());
        Instances dataSet = toWekaInstance(realmResults);
        realm.commitTransaction();
        return dataSet;
    }


    @Override
    public void saveInstanceToDB(Instance instance, int userId) {
        double[] instanceValues = instance.toDoubleArray();
        realm.beginTransaction();
        TrainingData trainingDataInstance = realm.createObject(TrainingData.class);
        trainingDataInstance.setUserId(userId);
        trainingDataInstance.setValues(instanceValues);
        realm.commitTransaction();
        System.out.println("Instance saved to Database");
    }

    @Override
    public void saveInstanceToDB(Instance instance) {
        saveInstanceToDB(instance, 0);
    }

    @Override
    public void saveInstancesToDB(Instances instances, int userId) {
        for (Instance instance : instances) {
            saveInstanceToDB(instance, userId);
        }
        System.out.println("Instances saved to Database");
    }

    @Override
    public void saveInstancesToDB(Instances instances) {
        saveInstancesToDB(instances, 0);
    }

    @Override
    public Instances getInstanceHeader() {
        return INSTANCE_HEADER;
    }

    @Override
    public void serialiseClassifierToFile(Classifier classifier) {
        fileManager.serialiseAndStoreClassifier(classifier);
    }

    @Override
    public Classifier deSerialiseClassifierFromFile() {
        return fileManager.deSerialiseClassifier();
    }

    @Override
    public RealmResults<TrainingData> getAllTrainingInstances(int userId) {
        RealmResults<TrainingData> genericTrainingData;
        realm.beginTransaction();
        genericTrainingData = realm.where(TrainingData.class)
                .equalTo(USER_ID, userId)
                .findAll();
        realm.commitTransaction();
        return genericTrainingData;
    }

    private Instances toWekaInstance(RealmResults<TrainingData> realmResults) {
        Instances dataSet = INSTANCE_HEADER;
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
}
