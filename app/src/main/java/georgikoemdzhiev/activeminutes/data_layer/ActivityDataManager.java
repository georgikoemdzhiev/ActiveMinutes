package georgikoemdzhiev.activeminutes.data_layer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

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
    public void setUserSleepingHours(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        mRealm.beginTransaction();
        mUser.setStartSleepingHours(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDayEnd);
        calendar.set(Calendar.MINUTE, minuteEnd);

        mUser.setStopSleepingHours(calendar.getTime());

        mRealm.copyToRealmOrUpdate(mUser);
        mRealm.commitTransaction();
    }

    @Override
    public String getUserSleepingHours() {
        return mUser.getStartSleepingHours().getHours() + " - " +
                mUser.getStopSleepingHours().getHours();
    }

    @Override
    public void setUserAndActivityStGoal(int stGoal) {
        mRealm.beginTransaction();
        Activity activity = getOrCreateTodayUserActivity();
        activity.setUserMaxContInacTarget(stGoal);
        mUser.setMaxContInactTarget(stGoal);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.copyToRealmOrUpdate(mUser);
        mRealm.commitTransaction();
    }

    @Override
    public int incActiveTime() {
        mRealm.beginTransaction();
        Activity activity = getOrCreateTodayUserActivity();
        int currentActiveTime = activity.getActiveTime();
        activity.setActiveTime(currentActiveTime + INCREMENT_VALUE);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.commitTransaction();
//        debugActivityTable();
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
//        debugActivityTable();
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
    public void setUserAndActivityPaGoal(int paGoal) {
        mRealm.beginTransaction();

        Activity activity = getOrCreateTodayUserActivity();
        activity.setUserPaGoal(paGoal);
        mUser.setPaGoal(paGoal);
        mRealm.copyToRealmOrUpdate(activity);
        mRealm.copyToRealmOrUpdate(mUser);
        mRealm.commitTransaction();
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

    /***
     * This method returns all activities grouped by week in a list
     * @return List of lists of activities grouped by the week they were recorded
     */

    @Override
    public List<List<Activity>> getAllActivityGroupedByWeek() {
        RealmResults<Activity> activities = getAllActivitiesSortedByDate();
        TreeMap<Integer, List<Activity>> activitiesGroupedByWeek = new TreeMap<>();
        // iterate through all activities in the db
        for (int i = 0; i < activities.size(); i++) {
            List<Activity> datesList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(activities.get(i).getDate());
            // the number of the week in the month
            int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

            for (Activity activity : activities) {
                Calendar c = Calendar.getInstance();
                c.setTime(activity.getDate());
                // if this activity's week number equals the above week number
                if (weekOfMonth == c.get(Calendar.WEEK_OF_MONTH)) {
                    // add it to "this" week's activities
                    datesList.add(activity);
                }
            }

            activitiesGroupedByWeek.put(weekOfMonth, datesList);
        }
        /***
         * Sort the list so that the recent week (e.g. list of activities grouped by week)
         * is at the top
         */
        List<List<Activity>> tempList = new ArrayList<>(activitiesGroupedByWeek.values());
        Collections.sort(tempList, new Comparator<List<Activity>>() {
            @Override
            public int compare(List<Activity> a1, List<Activity> a2) {
                if (a1.get(0).getDate().before(a2.get(0).getDate())) {
                    return 1;
                } else if (a1.get(0).getDate().after(a2.get(0).getDate())) {
                    return -1;
                }
                return 0;
            }
        });
        return tempList;
    }

    @Override
    public int getUserStGoal() {
        return mUser.getMaxContInactTarget();
    }

    @Override
    public void deleteAllMonitoringData() {
        mRealm.beginTransaction();
        RealmResults<Activity> activities = mRealm.where(Activity.class)
                .equalTo(USER_ID_KEY, mUser.getUserId()).findAll();
        activities.deleteAllFromRealm();
        mRealm.commitTransaction();
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
