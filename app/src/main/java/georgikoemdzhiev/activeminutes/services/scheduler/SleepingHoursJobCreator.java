package georgikoemdzhiev.activeminutes.services.scheduler;

import android.content.Context;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by Georgi Koemdzhiev on 01/03/2017.
 */

public class SleepingHoursJobCreator implements JobCreator {
    private Context mContext;

    public SleepingHoursJobCreator(Context context) {
        mContext = context;
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case CheckUserSHJob.TAG:
                return new CheckUserSHJob(mContext);
            default:
                return null;
        }
    }
}