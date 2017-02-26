package georgikoemdzhiev.activeminutes.self_management;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import java.util.Random;

import georgikoemdzhiev.activeminutes.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Georgi Koemdzhiev on 26/02/2017.
 */

public class FeedbackProvider implements IFeedbackProvider {
    private int ACHIEVED_NOTIFICATION_ID = 0;
    private int SEDENTARY_NOTIFICATION_ID = 1;
    private int ENCOURAGE_NOTIFICATION_ID = 2;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private NotificationCompat.Builder mNotificationBuilder;
    private NotificationManager mNotifyMgr;
    private Uri uri;

    public FeedbackProvider(Context context, SharedPreferences sharedPreferences) {
        mContext = context;
        mSharedPreferences = sharedPreferences;
        mNotificationBuilder = new NotificationCompat.Builder(mContext);
        mNotifyMgr = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    @Override
    public void provideEncouragingFeedback(int leftMinutesTillGoal) {
        mNotificationBuilder
                .setSound(uri)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Almost there!")
                .setContentText(getEncourageMessage(leftMinutesTillGoal));
        mNotifyMgr.notify(ENCOURAGE_NOTIFICATION_ID, mNotificationBuilder.build());
    }


    @Override
    public void provideGoalAchievedFeedback(int goal) {
        mNotificationBuilder
                .setSound(uri)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Goal Achieved!")
                .setContentText(getGoalAchievedMessage(goal));
        mNotifyMgr.notify(ACHIEVED_NOTIFICATION_ID, mNotificationBuilder.build());
    }

    @Override
    public void provideProlongedInactivityFeedback(int currentSt) {
        mNotificationBuilder
                .setSound(uri)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You have been inactive for too long!")
                .setContentText(getProlongedInactivityDetectedMessage(currentSt));
        mNotifyMgr.notify(SEDENTARY_NOTIFICATION_ID, mNotificationBuilder.build());
    }

    private String getGoalAchievedMessage(int paGoal) {
        // convert in minutes and toString...
        String goalStr = String.valueOf(paGoal / 60);
        String[] messages = {"Good job, goal of " + goalStr + " min achieved!"};
        int rnd = new Random().nextInt(messages.length);
        return messages[rnd];
    }

    private String getProlongedInactivityDetectedMessage(int currentSt) {
        // convert in minutes and toString...
        String currentStStr = String.valueOf(currentSt / 60);
        String[] messages = {"You have been inactive for " + currentStStr + " minutes. It is recommended to break long " +
                "inactivity periods by at least 5 minute breaks"};
        int rnd = new Random().nextInt(messages.length);
        return messages[rnd];
    }

    private String getEncourageMessage(int leftMinTillGoal) {
        String currentStStr = String.valueOf(leftMinTillGoal / 60);
        String[] messages = {"You are almost there! Only " + currentStStr + " minutes till you reach your goal!"};
        int rnd = new Random().nextInt(messages.length);
        return messages[rnd];
    }
}
