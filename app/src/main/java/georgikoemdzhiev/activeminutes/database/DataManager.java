package georgikoemdzhiev.activeminutes.database;

import georgikoemdzhiev.activeminutes.Utils.IFileManager;
import georgikoemdzhiev.activeminutes.database.db.TrainingData;
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
    private static Instances INSTANCE_HEADER;

    private Realm realm;
    private IFileManager fileManager;
    private Instances dataSet;

    public DataManager(Realm realm, IFileManager fileManager) {
        this.realm = realm;
        this.fileManager = fileManager;
        INSTANCE_HEADER = fileManager.readARFFFileSchema();
        dataSet = INSTANCE_HEADER;
    }

    @Override
    public Instances readInstancesFromDB() {
        realm.beginTransaction();
        RealmResults<TrainingData> realmResults = realm.where(TrainingData.class).findAll();
        System.out.println("RealmResults: " + realmResults.toString());

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

        realm.commitTransaction();
        return dataSet;
    }


    @Override
    public void saveInstanceToDB(Instance instance) {
        double[] instanceValues = instance.toDoubleArray();
        realm.beginTransaction();
        TrainingData trainingDataInstance = realm.createObject(TrainingData.class);
        trainingDataInstance.setValues(instanceValues);
        realm.commitTransaction();
        System.out.println("Instance saved to Database");
    }

    @Override
    public void saveInstancesToDB(Instances instances) {
        for (Instance instance : instances) {
            saveInstanceToDB(instance);
        }
        System.out.println("Instances saved to Database");
    }

    @Override
    public Instances getInstanceHeader() {
        return INSTANCE_HEADER;
    }

    @Override
    public void serialiseClassifierToFile(Classifier iBkClassifier) {
        fileManager.serialiseAndStoreClassifier(iBkClassifier);
    }

    @Override
    public Classifier deSerialiseClassifierFromFile() {
        return fileManager.deSerialiseClassifier();
    }

    @Override
    public void getNumOfInstances(NumOfInstResult result) {
        result.onResult(getAllTrainingData().size() + "");
    }


    private RealmResults<TrainingData> getAllTrainingData() {
        RealmResults<TrainingData> results;
        realm.beginTransaction();
        results = realm.where(TrainingData.class).findAll();
        realm.commitTransaction();
        return results;
    }
}
