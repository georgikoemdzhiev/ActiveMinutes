package georgikoemdzhiev.activeminutes.data_layer;

import java.util.Date;

import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public interface IActivityDataManager {

    void setUserId(User user);

    void incActiveTime();

    void incCurrentInacInterval();

    void clearCurrentInacInterval();

    void incTimesInacDetected();

    void checkOrUpdateLognestInacInterval();

    int getActiveTimeByDate(Date date);

    int getLongestInacTimeByDate(Date date);

    int getTimesInacDetected(Date date);
}
