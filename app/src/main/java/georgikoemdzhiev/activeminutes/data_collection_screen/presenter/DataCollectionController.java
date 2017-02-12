package georgikoemdzhiev.activeminutes.data_collection_screen.presenter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import georgikoemdzhiev.activeminutes.services.DataCollectionService;
import georgikoemdzhiev.activeminutes.services.service_events.ControlMessage;
import georgikoemdzhiev.activeminutes.services.service_events.DataMessage;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class DataCollectionController implements IDataCollectionController {
    private Context mContext;

    public DataCollectionController(Context context) {
        mContext = context;
    }

    @Override
    public void startService() {
        Intent intent = new Intent(mContext, DataCollectionService.class);
        mContext.startService(intent);
    }

    @Override
    public void startRecording() {
        EventBus.getDefault().post(new ControlMessage(DataCollectionService.START_RECORDING));
    }

    @Override
    public void stopService() {
        EventBus.getDefault().post(new ControlMessage(DataCollectionService.STOP_RECORDING));
    }

    @Override
    public void exportCollectedData() {
        EventBus.getDefault().post(new ControlMessage(DataCollectionService.EXPORT_DATA));
    }

    @Override
    public void clearCollectedData() {
        EventBus.getDefault().post(new ControlMessage(DataCollectionService.CLEAR_DATA));
    }

    @Override
    public void setActivityLabel(String label) {
        EventBus.getDefault().post(new DataMessage(label));
    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
