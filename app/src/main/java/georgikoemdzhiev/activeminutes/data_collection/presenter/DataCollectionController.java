package georgikoemdzhiev.activeminutes.data_collection.presenter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import georgikoemdzhiev.activeminutes.services.DataCollectionService;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class DataCollectionController implements IDataCollectionController {
    private Context mContext;
    private Intent serviceIntent;

    public DataCollectionController(Context context) {
        mContext = context;
        serviceIntent = new Intent(mContext, DataCollectionService.class);
    }

    @Override
    public void startService() {
        serviceIntent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.START_RECORDING);
        mContext.startService(serviceIntent);
        Toast.makeText(mContext, "Recording!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void stopService() {
        serviceIntent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.STOP_RECORDING);
        mContext.startService(serviceIntent);
    }

    @Override
    public void exportCollectedData() {
        serviceIntent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.EXPORT_DATA);
        mContext.startService(serviceIntent);
    }

    @Override
    public void clearCollectedData() {
        serviceIntent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.CLEAR_DATA);
        mContext.startService(serviceIntent);
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
