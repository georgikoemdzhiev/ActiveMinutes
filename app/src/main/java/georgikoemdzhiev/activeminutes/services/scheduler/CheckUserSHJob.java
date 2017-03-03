package georgikoemdzhiev.activeminutes.services.scheduler;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import georgikoemdzhiev.activeminutes.services.ActiveMinutesService;
import georgikoemdzhiev.activeminutes.utils.DateUtils;

/**
 * Created by Georgi Koemdzhiev on 01/03/2017.
 */

public class CheckUserSHJob extends Job {
    public static final String TAG = "check_user_job{_tag";
    private final String START_SH_KEY = "start_sh_key";
    private final String STOP_SH_KEY = "stop_sh_key";
    private Context mContext;


    public CheckUserSHJob(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        Date now = new Date();
        Date stopSleepingHours = new Date(params.getExtras().getLong(STOP_SH_KEY, 0L));
        Date startSleepingHours = new Date(params.getExtras().getLong(START_SH_KEY, 0L));


        if (DateUtils.isSleepingHours(startSleepingHours, now, stopSleepingHours)) {
            System.out.println("SLEEPING HOURS");

            if (isMyServiceRunning(ActiveMinutesService.class)) {
                mContext.stopService(new Intent(mContext, ActiveMinutesService.class));
            }

        } else {
            System.out.println("NOT SLEEPING HOURS");
            if (!isMyServiceRunning(ActiveMinutesService.class)) {
                mContext.startService(new Intent(mContext, ActiveMinutesService.class));
            }
        }

        return Result.SUCCESS;
    }


    public int scheduleSleepingHoursCheckJob(Date startSleepingHours, Date stopSleepingHours) {

        PersistableBundleCompat bundleCompat = new PersistableBundleCompat();
        bundleCompat.putLong(START_SH_KEY, startSleepingHours.getTime());
        bundleCompat.putLong(STOP_SH_KEY, stopSleepingHours.getTime());

        int jobId = new JobRequest.Builder(CheckUserSHJob.TAG)
                .setExtras(bundleCompat)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setPersisted(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();

        return jobId;
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
