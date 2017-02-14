package georgikoemdzhiev.activeminutes.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.data_collection_screen.view.DataCollectionActivity;
import georgikoemdzhiev.activeminutes.har.IHarManager;
import georgikoemdzhiev.activeminutes.services.service_events.ControlMessage;
import georgikoemdzhiev.activeminutes.services.service_events.DataMessage;

public class DataCollectionService extends Service implements SensorEventListener {
    // Class constants
    public static final String EXPORT_DATA = "export_data";
    public static final String STOP_RECORDING = "stop_recording";
    public static final String START_RECORDING = "start_recording";
    public static final String CLEAR_DATA = "clear_collected_data";

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
        // Register event bus so this class can receive event messages
        EventBus.getDefault().register(this);
        ((ActiveMinutesApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  // This Service is not a BindService
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onControlMessageEvent(ControlMessage message) {
        switch (message.getMESSAGE()) {
            case EXPORT_DATA:
                mHarManager.trainAndSaveGenericClassifier();
                showToastMessage("Training & Saving Classifier...");
                break;
            case START_RECORDING:
                showToastMessage("Recording...");
                runAsForeground();
                sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
                break;
            case CLEAR_DATA:
                showToastMessage("Data cleared!");
                break;
            case STOP_RECORDING:
                mHarManager.resetWindowBegTime();
                sensorManager.unregisterListener(this, accSensor);
                stopForeground(true);
                showToastMessage("Recording stopped!");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataReceivedEvent(DataMessage messageEvent) {
        this.activityLabel = messageEvent.getActivityLabel();
        mHarManager.setActivityLabel(activityLabel);
        showToastMessage("Activity label change to: " + activityLabel);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregister when the service is being destroyed
        EventBus.getDefault().unregister(this);
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

    private void runAsForeground() {
        Intent notificationIntent = new Intent(this, DataCollectionActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Data collection")
                .setContentText("Recording accelerometer data...")
                .setContentIntent(pendingIntent).build();
        startForeground(1337, notification);
    }
}
