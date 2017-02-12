package georgikoemdzhiev.activeminutes.Utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    private Context context;

    private File path = Environment.getExternalStoragePublicDirectory(DIRECTORY_HAR);

    private File classifierFile = new File(path, "/" + "classifierModel.data");

    public FileManager(Context context) {
        this.context = context;
    }

    @Override
    public void saveCurrentDataToArffFile(Instances instances, String activityLabel) {
//        String formattedUserName = userName.replace(" ", "_");

        // have the object build the directory structure, if needed.
        boolean harDirectoryCreated = path.mkdirs();
        if (harDirectoryCreated) {
            File file = new File(path, "/" + "HAR_" + activityLabel + "_" +
                    System.currentTimeMillis() + ".arff");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(instances.toString());
                writer.flush();
                writer.close();
                Toast.makeText(context, "File Saved!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Error during saving data to Arff file!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public Instances readARFFFileSchema() {
        BufferedReader reader = null;
        Instances instances = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("schema_file.arff")));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            instances = arff.getData();
            instances.setClassIndex(instances.numAttributes() - 1);

            System.out.println("Schema read successfully ->" + instances.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return instances;
    }

    @Override
    public void serialiseAndStoreClassifier(Classifier classifier) {
        path.mkdirs();


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


}
