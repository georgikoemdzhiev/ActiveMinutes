package georgikoemdzhiev.activeminutes.data_layer;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public class FileManager implements IFileManager {

    private static final String DIRECTORY_HAR = "HAR";

    private final String DATASET_SCHEMA_FILE_NAME = "schema_file.arff";
    private final String DATASET_FILE_NAME = "dataset.arff";

    private Context context;

    private File path = Environment.getExternalStoragePublicDirectory(DIRECTORY_HAR);

    private File classifierFile = new File(path, "/" + "classifierModel.data");

    private File arffFile = new File(path, "/" + "dataset.arff");

    public FileManager(Context context) {
        this.context = context;
    }

    @Override
    public Instances readArffFileSchemaFromAssets() {
        return readArffFileFromAssets(DATASET_SCHEMA_FILE_NAME);
    }

    @Override
    public Instances readArffFileFromAssets() {
        return readArffFileFromAssets(DATASET_FILE_NAME);
    }

    public void saveToArffFile(Instances dataset) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(arffFile));
            writer.write(dataset.toString());
            writer.flush();
            writer.close();
            System.out.println("Arff file saved!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Arff file NOT saved! " + e.getMessage());
        }

    }

    @Override
    public Instances readFromArffFileFromES() {
        BufferedReader reader = null;
        Instances instances = null;
        try {
            reader = new BufferedReader(new FileReader(arffFile));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            instances = arff.getData();
            instances.setClassIndex(instances.numAttributes() - 1);

            System.out.println("Reading ARFF file from ES: " + instances.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return instances;
    }

    @Override
    public void serialiseAndStoreClassifier(Classifier classifier) {
        try {
            FileOutputStream fos = new FileOutputStream(classifierFile);
            ObjectOutputStream ou = new ObjectOutputStream(fos);
            ou.writeObject(classifier);
            ou.close();
            fos.close();
            System.out.println("serialise Success");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("serialise error: " + e.toString());
        }
    }

    @Override
    public Classifier deSerialiseClassifier() {
        IBk classifier = null;
        try {
            FileInputStream fi = new FileInputStream(classifierFile);
            ObjectInputStream oi = new ObjectInputStream(fi);
            classifier = (IBk) oi.readObject();
            oi.close();
            fi.close();
            System.out.println("deserialize Success");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return classifier;
    }

    /***
     * Helper method that reads a arff file from the application's asset folder
     *
     * @param fileName the name of the file to be read
     * @return Instances (dataset) WEKA object
     */
    private Instances readArffFileFromAssets(String fileName) {
        BufferedReader reader = null;
        Instances instances = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            instances = arff.getData();
            instances.setClassIndex(instances.numAttributes() - 1);

            System.out.println("Reading ARFF " + fileName + " file from Assets: " + instances.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return instances;
    }

}
