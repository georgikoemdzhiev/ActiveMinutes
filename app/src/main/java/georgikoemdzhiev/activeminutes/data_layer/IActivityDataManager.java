package georgikoemdzhiev.activeminutes.data_layer;

import java.util.List;

import georgikoemdzhiev.activeminutes.data_layer.db.Activity;
import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public interface IActivityDataManager {

    // Setters & other data modifying methods
    void setUser(User user);

    void setUserSleepingHours(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd);

    String getUserSleepingHours();

    void setUserAndActivityStGoal(int stGoal);

    int incActiveTime();

    int incCurrentInacInterval();

    void clearCurrentInacInterval();


    // Getters

    int getActiveTime();

    int getLongestInacInterval();

    int getAverageInacInterval();

    int getUserPaGoal();

    void setUserAndActivityPaGoal(int paGoal);

    int getMaxContInacTarget();

    int getTimesTargetExceeded();

    List<Activity> getAllActivitiesSortedByDate();

    List<List<Activity>> getAllActivityGroupedByWeek();


    int getUserStGoal();

    void deleteAllMonitoringData();
}
