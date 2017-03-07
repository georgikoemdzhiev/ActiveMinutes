package georgikoemdzhiev.activeminutes.data_layer;

import java.util.List;

import georgikoemdzhiev.activeminutes.data_layer.db.Activity;
import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public interface IActivityDataManager {

    void setUser(User user);

    int incActiveTime();

    int incCurrentInacInterval();

    void clearCurrentInacInterval();


    // Getters

    int getActiveTime();

    int getLongestInacInterval();

    int getAverageInacInterval();

    int getUserPaGoal();

    int getMaxContInacTarget();

    int getTimesTargetExceeded();

    List<Activity> getAllActivitiesSortedByDate();

}
