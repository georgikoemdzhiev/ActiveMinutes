package georgikoemdzhiev.activeminutes.Utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public class FileManager implements IFileManager {

    private static final String DIRECTORY_HAR = "HAR";

    private Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    @Override
    public void saveCurrentDataToArffFile(Instances instances, String activityLabel) {
//        String formattedUserName = userName.replace(" ", "_");
        File path = Environment.getExternalStoragePublicDirectory(DIRECTORY_HAR);
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
}
