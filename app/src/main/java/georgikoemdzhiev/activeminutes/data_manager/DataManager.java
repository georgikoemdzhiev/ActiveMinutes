package georgikoemdzhiev.activeminutes.data_manager;

import georgikoemdzhiev.activeminutes.Utils.IFileManager;
import georgikoemdzhiev.activeminutes.data_manager.db.TrainingData;
import io.realm.Realm;
import io.realm.RealmResults;
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
        RealmResults<TrainingData> realmResults = realm.where(TrainingData.class).findAll();
        System.out.println("RealmResults: " + realmResults.toString());
        for (int p = 0; p < realmResults.size(); p++) {
            DenseInstance denseInstance = new DenseInstance(INSTANCE_HEADER.numAttributes());
            TrainingData currentDBInstance = realmResults.get(p);

            for (int i = 0; i < INSTANCE_HEADER.numAttributes(); i++) {
                denseInstance.setValue(i, currentDBInstance.getValues()[i]);
            }

            dataSet.add(denseInstance);
        }

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
}
