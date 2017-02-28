package georgikoemdzhiev.activeminutes.services.job_scheduling;

import android.content.Context;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class ServiceJobCreator implements JobCreator {
    private Context mContext;

    public ServiceJobCreator(Context context) {
        mContext = context;
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case StartSleepingHoursJob.TAG:
                return new StartSleepingHoursJob(mContext);
            default:
                return null;
        }
    }
}
