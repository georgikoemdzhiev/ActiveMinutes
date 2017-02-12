package georgikoemdzhiev.activeminutes.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import javax.inject.Inject;

import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.har.IHarManager;

public class DataCollectionService extends Service implements SensorEventListener {
    // Class constants
    public static final String EXPORT_DATA = "export_data";
    public static final String STOP_RECORDING = "stop_recording";
    public static final String START_RECORDING = "start_recording";
    public static final String CLEAR_DATA = "clear_collected_data";
    public static final String SET_LABEL_KEY = "set_activity_label";
    public static final String CONTROL_KEY = "export_data_key";
    @Inject
    IHarManager mHarManager;
    private String activityLabel = "";
    private SensorManager sensorManager;
    private Sensor accSensor;

    public DataCollectionService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ((ActiveMinutesApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getExtras() != null) {
            Bundle intentBundle = intent.getExtras();
            switch (intentBundle.getString(CONTROL_KEY, "")) {
                case EXPORT_DATA:
                    mHarManager.trainClassifier();
                    showToastMessage("Exporting data to sd card...");
                    break;
                case START_RECORDING:
                    showToastMessage("Recording...");
                    sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
                    break;
                case CLEAR_DATA:
                    showToastMessage("Data cleared!");
                    break;
                case STOP_RECORDING:
                    mHarManager.resetWindowBegTime();
                    sensorManager.unregisterListener(this, accSensor);
                    showToastMessage("Recording stopped!");
                    break;

                default:
                    System.out.println("DEFAULT case activated!");
                    this.activityLabel = intentBundle.getString(SET_LABEL_KEY);
                    mHarManager.setActivityLabel(activityLabel);
                    showToastMessage("Activity label change to: " + activityLabel);
                    break;
            }

        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  // This Service is not a BindService
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        mHarManager.feedData(sensorEvent.values, sensorEvent.timestamp);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
