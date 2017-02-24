package georgikoemdzhiev.activeminutes.data_layer;

import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public interface IActivityDataManager {

    void setUserId(User user);

    void incActiveTime();

    void incCurrentInacInterval();

    void clearCurrentInacInterval();


    // Getters

    int getActiveTime();

    int getLongestInacInterval();

    int getAverageInacInterval();

    int getUserPaGoal();

}
