package georgikoemdzhiev.activeminutes.data_layer;

import java.util.Calendar;
import java.util.Date;

import georgikoemdzhiev.activeminutes.data_layer.db.Activity;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class ActivityDataManager implements IActivityDataManager {
    private final String ACTIVITY_ID = "activity_id";
    private final String DATE_KEY = "date";
    private final String USER_ID_KEY = "user_id";
    // since every 3 seconds new activity is recognised...
    private final int INCREMENT_VALUE = 3;

    private Realm mRealm;
    private User mUser;

    public ActivityDataManager(Realm realm) {
        mRealm = realm;
    }

    @Override
    public void setUserId(User user) {
        mUser = user;
    }

    @Override
    public void incActiveTime() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateActivityByDate();
        int currentActiveTime = activity.getActiveTime();
        activity.setActiveTime(currentActiveTime + INCREMENT_VALUE);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
        debugActivityTable();
    }


    @Override
    public void incCurrentInacInterval() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateActivityByDate();
        int currentInacInterval = activity.getCurrentInactivityInterval();
        int incremented = currentInacInterval + INCREMENT_VALUE;
        activity.setCurrentInactivityInterval(incremented);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
        debugActivityTable();
    }

    @Override
    public void clearCurrentInacInterval() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateActivityByDate();
        activity.setCurrentInactivityInterval(0);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
        //debugActivityTable();
    }

    @Override
    public void incTimesInacDetected() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateActivityByDate();
        int timesInacDedected = activity.getTimesInactivityDetected();
        timesInacDedected++;
        activity.setTimesInactivityDetected(timesInacDedected);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
        //debugActivityTable();
    }

    @Override
    public void checkOrUpdateLognestInacInterval() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateActivityByDate();
        int currentInacInterval = activity.getCurrentInactivityInterval();
        int longestInactInterval = activity.getLongestInactivityInterval();

        if (currentInacInterval > longestInactInterval) {
            activity.setLongestInactivityInterval(currentInacInterval);
            mRealm.copyToRealmOrUpdate(activity);
        }

        mRealm.commitTransaction();
        // debugActivityTable();
    }

    @Override
    public int getActiveTimeByDate(Date date) {
        Date truncatedDate = truncateDate(date);
        Activity activity = mRealm.where(Activity.class)
                .greaterThanOrEqualTo(DATE_KEY, truncatedDate)
                .findFirst();
        return activity.getActiveTime();
    }

    @Override
    public int getLongestInacTimeByDate(Date date) {
        Date truncatedDate = truncateDate(date);
        Activity activity = mRealm.where(Activity.class)
                .greaterThanOrEqualTo(DATE_KEY, truncatedDate)
                .findFirst();
        return activity.getLongestInactivityInterval();
    }

    @Override
    public int getTimesInacDetected(Date date) {
        Date truncatedDate = truncateDate(date);
        Activity activity = mRealm.where(Activity.class)
                .greaterThanOrEqualTo(DATE_KEY, truncatedDate)
                .findFirst();
        return activity.getTimesInactivityDetected();
    }


    private Activity getOrCreateActivityByDate() {
        Date today = getTruncatedTodayDate();

        Activity activity;
        // Find all Activity table records...
        RealmResults<Activity> activities = mRealm
                .where(Activity.class)
                .findAll();

        // check to see if there is any date at all in the table
        if (activities.size() != 0) {
            // Find all Activity table records that have date == today & user_id = mUser.id...
            RealmResults<Activity> activityRealmResults = mRealm
                    .where(Activity.class)
                    .greaterThanOrEqualTo(DATE_KEY, today)
                    .equalTo(USER_ID_KEY, mUser.getUserId())
                    .findAll();
            if (activityRealmResults.size() != 0) {
                activity = activityRealmResults.get(0);
            } else {
                // create new Activity entry...
                activity = new Activity();
                activity.setActivity_id(getNextInt());

                //set up the reset of the attributes...
                activity.setUser_id(mUser.getUserId());
                activity.setActiveTime(0);
                activity.setDate(today);
                activity.setLongestInactivityInterval(0);
                activity.setCurrentInactivityInterval(0);
                activity.setTimesInactivityDetected(0);
                activity.setUserMaxContInacGoal(mUser.getMaxContInactGoal());
                activity.setUserPaGoal(mUser.getPaGoal());
            }
        } else {
            // no data in the date. Create first entry...
            activity = new Activity();
            activity.setActivity_id(0);

            //set up the reset of the attributes...
            activity.setUser_id(mUser.getUserId());
            activity.setActiveTime(0);
            activity.setDate(today);
            activity.setLongestInactivityInterval(0);
            activity.setCurrentInactivityInterval(0);
            activity.setTimesInactivityDetected(0);
            activity.setUserMaxContInacGoal(mUser.getMaxContInactGoal());
            activity.setUserPaGoal(mUser.getPaGoal());
        }


        return activity;
    }

    private Date getTruncatedTodayDate() {
        Date today = new Date();
        return truncateDate(today);
    }

    private Date truncateDate(Date date) {
        Date today = date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        today = cal.getTime();
        return today;
    }


    private int getNextInt() {
        int key;
        try {
            key = mRealm.where(Activity.class).max(ACTIVITY_ID).intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException ex) {
            key = 0;
        }

        return key;
    }

    private void debugActivityTable() {
        RealmResults<Activity> activities = mRealm
                .where(Activity.class)
                .findAll();

        for (Activity activity : activities) {
            System.out.println(activity.toString());
        }
    }
}
