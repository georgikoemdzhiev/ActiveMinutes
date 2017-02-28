package georgikoemdzhiev.activeminutes.services.job_scheduling;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import georgikoemdzhiev.activeminutes.services.ActiveMinutesService;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class StopSleepingHoursJob extends Job {

    public static final String TAG = "StopSleepingHoursJob";
    private Context mContext;

    public StopSleepingHoursJob(Context context) {
        mContext = context;
    }

    public static int schedule(Date time, boolean updateCurrent) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, time.getHours());
        today.set(Calendar.MINUTE, time.getMinutes());
        today.set(Calendar.SECOND, 0);

        long startMs = today.getTime().getTime();

        long endMs = startMs + TimeUnit.MINUTES.toMillis(1);

        int id = new JobRequest.Builder(TAG)
                .setExecutionWindow(startMs, endMs)
                .setPersisted(true)
                .setUpdateCurrent(updateCurrent)
                .build()
                .schedule();

        return id;
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // run your job here
        mContext.startService(new Intent(mContext, ActiveMinutesService.class));
        return Result.SUCCESS;
    }
}
