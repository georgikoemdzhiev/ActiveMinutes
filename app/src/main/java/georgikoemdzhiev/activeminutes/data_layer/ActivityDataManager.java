package georgikoemdzhiev.activeminutes.data_layer;

import java.util.Date;

import georgikoemdzhiev.activeminutes.data_layer.db.Activity;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static georgikoemdzhiev.activeminutes.utils.DateUtils.truncateDate;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class ActivityDataManager implements IActivityDataManager {
    private static final int THRESHOLD = 600;
    private final String ACTIVITY_ID = "activity_id";
    private final String DATE_KEY = "date";
    private final String USER_ID_KEY = "user_id";
    private final String DATE_FIELD = "date";
    // since every 3 seconds new activity is recognised...
    private final int INCREMENT_VALUE = 3;

    private Realm mRealm;
    private User mUser;

    public ActivityDataManager(Realm realm) {
        mRealm = realm;
    }

    @Override
    public void setUser(User user) {
        mUser = user;
    }

    @Override
    public int incActiveTime() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateTodayUserActivity();
        int currentActiveTime = activity.getActiveTime();
        activity.setActiveTime(currentActiveTime + INCREMENT_VALUE);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
        debugActivityTable();
        return activity.getActiveTime();
    }


    @Override
    public int incCurrentInacInterval() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateTodayUserActivity();

        int currentInacInterval = activity.getCurrentInactivityInterval();
        int longestInactInterval = activity.getLongestInactivityInterval();
        int currentInacInterIncremented = currentInacInterval + INCREMENT_VALUE;
        // get times current inactivity is reset
        int timesCurrentInacReseted = activity.getTimesCurrentInacReseted();
        int totalInacTime = activity.getTotalInactivityTime();
        // calculate the averate based on the above variable
        activity.setAverageInactInterval(totalInacTime / timesCurrentInacReseted);

        // Check if longest inactivity interval against current - if it needs updating...
        if (currentInacInterval > longestInactInterval) {
            activity.setLongestInactivityInterval(currentInacInterval);
        }
        activity.setCurrentInactivityInterval(currentInacInterIncremented);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
//        debugActivityTable();
        return activity.getCurrentInactivityInterval();
    }

    @Override
    public void clearCurrentInacInterval() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateTodayUserActivity();
        // Get the times current inactivity interval is reset
        int timesCurrentInacReseted = activity.getTimesCurrentInacReseted();


        int currentTotalInacTime = activity.getTotalInactivityTime();
        int currentInactInterval = activity.getCurrentInactivityInterval();
        // add the currentInactivityInterval to the total
        activity.setTotalInactivityTime(currentTotalInacTime + currentInactInterval);
        // reset current inac interval now
        activity.setCurrentInactivityInterval(0);

        if (currentInactInterval > THRESHOLD) {
            // increment times current inactivity resented
            timesCurrentInacReseted++;
        }
        // save times current inac interval is reset
        activity.setTimesCurrentInacReseted(timesCurrentInacReseted);

        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
        debugActivityTable();
    }

    @Override
    public int getActiveTime() {
        Activity activity = getOrCreateTodayUserActivity();
        return activity.getActiveTime();
    }

    @Override
    public int getLongestInacInterval() {
        Activity activity = getOrCreateTodayUserActivity();
        return activity.getLongestInactivityInterval();
    }

    @Override
    public int getAverageInacInterval() {
        Activity activity = getOrCreateTodayUserActivity();
        return activity.getAverageInactInterval();
    }

    @Override
    public int getUserPaGoal() {
        Activity activity = getOrCreateTodayUserActivity();
        return activity.getUserPaGoal();
    }

    @Override
    public int getMaxContInacTarget() {
        Activity activity = getOrCreateTodayUserActivity();
        return activity.getUserMaxContInacTarget();
    }

    @Override
    public int getTimesTargetExceeded() {
        Activity activity = getOrCreateTodayUserActivity();
        int maxContInacTarget = activity.getUserMaxContInacTarget();
        int longestInactInterval = activity.getLongestInactivityInterval();
        return Math.round(longestInactInterval / maxContInacTarget);
    }

    @Override
    public RealmResults<Activity> getAllActivitiesSortedByDate() {
        RealmResults<Activity> activities = mRealm.where(Activity.class)
                .equalTo(USER_ID_KEY, mUser.getUserId())
                .findAllSorted(DATE_FIELD, Sort.DESCENDING);
        return activities;
    }

    // helper methods
    private Activity getOrCreateTodayUserActivity() {
        Date today = truncateDate(new Date());
        Activity activity = new Activity();
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
                return activity;
            } else {
                // create new Activity entry with id field incremented...
                activity.setActivity_id(getNextInt());
            }
        } else {
            // no data in the date. Create first entry - id = 0...
            activity.setActivity_id(0);
            //set up the reset of the attributes...
        }
        //set up the reset of the attributes...
        activity.setUser_id(mUser.getUserId());
        activity.setActiveTime(0);
        activity.setDate(today);
        activity.setLongestInactivityInterval(0);
        activity.setCurrentInactivityInterval(0);
        activity.setUserMaxContInacTarget(mUser.getMaxContInactTarget());
        activity.setUserPaGoal(mUser.getPaGoal());

        return activity;
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
