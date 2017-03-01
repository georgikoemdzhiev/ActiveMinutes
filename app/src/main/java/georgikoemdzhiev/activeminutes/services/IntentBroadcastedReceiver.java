package georgikoemdzhiev.activeminutes.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IntentBroadcastedReceiver extends BroadcastReceiver {
    public IntentBroadcastedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("BroadcastedReceiver", "On Receive called!");

        if (intent.getExtras() != null)
            if (intent.getExtras().getString("command", "").equals("STOP_SERVICE")) {
                Log.e("BroadcastedReceiver", "Intent STOP SERVICE FIRED!");
                context.stopService(new Intent(context, ActiveMinutesService.class));
            } else {
                Log.e("BroadcastedReceiver", "Intent START SERVICE FIRED!");
                context.startService(new Intent(context, ActiveMinutesService.class));
            }

    }
}
