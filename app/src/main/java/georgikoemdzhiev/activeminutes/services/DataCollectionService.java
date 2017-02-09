package georgikoemdzhiev.activeminutes.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DataCollectionService extends Service {
    public static final String EXPORT_DATA = "export_data";
    public static final String STOP_RECORDING = "stop_recording";
    public static final String START_RECORDING = "start_recording";
    public static final String CLEAR_DATA = "clear_collected_data";
    public static final String CONTROL_KEY = "export_data_key";


    public DataCollectionService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getExtras() != null)
            switch (intent.getExtras().getString(CONTROL_KEY, "")) {
                case EXPORT_DATA:
                    saveCollectedDataToDB();
                    System.out.println("SAVING DATA TO DATA BASE...");
                    break;
                case START_RECORDING:
                    System.out.println("Recording...");
                    break;

                case CLEAR_DATA:
                    System.out.println("Data cleared!");
                    break;
                case STOP_RECORDING:
                    // TODO Maybe unregister sensor listener
                    System.out.println("Recording stopped!");
                    break;

            }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void saveCollectedDataToDB() {
        //stopSelf();
        // TODO to be implemented
    }
}
