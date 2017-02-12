package georgikoemdzhiev.activeminutes.data_collection_screen.presenter;

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


    public DataCollectionController(Context context) {
        mContext = context;
    }

    @Override
    public void startService() {
        Intent intent = new Intent(mContext, DataCollectionService.class);
        intent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.START_RECORDING);
        mContext.startService(intent);
        Toast.makeText(mContext, "Recording!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void stopService() {
        Intent intent = new Intent(mContext, DataCollectionService.class);
        intent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.STOP_RECORDING);
        mContext.startService(intent);
    }

    @Override
    public void exportCollectedData() {
        Intent intent = new Intent(mContext, DataCollectionService.class);
        intent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.EXPORT_DATA);
        mContext.startService(intent);
    }

    @Override
    public void clearCollectedData() {
        Intent intent = new Intent(mContext, DataCollectionService.class);
        intent.putExtra(DataCollectionService.CONTROL_KEY, DataCollectionService.CLEAR_DATA);
        mContext.startService(intent);
    }

    @Override
    public void setActivityLabel(String label) {
        Intent intent = new Intent(mContext, DataCollectionService.class);
        intent.putExtra(DataCollectionService.SET_LABEL_KEY, label);
        mContext.startService(intent);
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
