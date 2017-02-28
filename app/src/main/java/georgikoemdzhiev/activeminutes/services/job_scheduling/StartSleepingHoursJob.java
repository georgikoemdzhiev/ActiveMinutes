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

public class StartSleepingHoursJob extends Job {

    public static final String TAG = "StartSleepingHoursJob";
    private Context mContext;

    public StartSleepingHoursJob(Context context) {
        mContext = context;
    }

    public static int schedule(Date time, boolean updateCurrent) {
//        Calendar today = Calendar.getInstance();
//        today.set(Calendar.HOUR_OF_DAY, time.getHours());
//        today.set(Calendar.MINUTE, time.getMinutes());
//        today.set(Calendar.SECOND, 0);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 1 AM - 6 AM, ignore seconds
        long startMs = TimeUnit.MINUTES.toMillis(60 - minute)
                + TimeUnit.HOURS.toMillis((24 - hour) % 24);
        long endMs = startMs + TimeUnit.HOURS.toMillis(5);

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
        mContext.stopService(new Intent(mContext, ActiveMinutesService.class));
        return Job.Result.SUCCESS;
    }
}
