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
import android.util.Log;

import java.util.Observer;

import javax.inject.Inject;

import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ActiveMinutesActivity;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.application.dagger.qualifiers.Named;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.har.HarClassifyManager;
import georgikoemdzhiev.activeminutes.har.IHarManager;
import georgikoemdzhiev.activeminutes.self_management.IActivityMonitor;

public class ActiveMinutesService extends Service implements SensorEventListener {
    @Inject
    IAuthDataManager mAuthDataManager;

    @Inject
    @Named("classify")
    IHarManager mHarManager;

    @Inject
    IActivityMonitor mActivityMonitor;

    private SensorManager sensorManager;
    private Sensor accSensor;

    public ActiveMinutesService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        satisfyDependencies();
        Log.e("TAG", "ActiveMinutesService---OnCreate. Logged in user: " + mAuthDataManager.getLoggedInUser().toString());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        setUpActivityManager();


    }

    private void setUpActivityManager() {
        // set the logged in user...
        mActivityMonitor.setUser(mAuthDataManager.getLoggedInUser());
        // add observable mHarManager to the observer ActivityMonitor
        ((HarClassifyManager) mHarManager).addObserver((Observer) mActivityMonitor);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runAsForeground();
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this, accSensor);
    }

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getApplication())
                .getComponent()
                .inject(this);
    }

    private void runAsForeground() {
        Intent notificationIntent = new Intent(this, ActiveMinutesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.monitoring_icon)
                .setContentTitle("Activity Monitoring activated.")
                .setContentText("Monitoring your inactivity levels...")
                .setContentIntent(pendingIntent).build();
        startForeground(1336, notification);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        mHarManager.feedData(sensorEvent.values, sensorEvent.timestamp);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
