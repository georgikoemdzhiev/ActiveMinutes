package georgikoemdzhiev.activeminutes.data_layer.db;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class Activity extends RealmObject {
    private int user_id;
    private Date date;
    private int activeTime;
    private int longestInactivityInterval;
    private int currentInactivityInterval;
    private int timesInactivityDetected;
    private int userPaGoal;
    private int userMaxContInacGoal;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(int activeTime) {
        this.activeTime = activeTime;
    }

    public int getLongestInactivityInterval() {
        return longestInactivityInterval;
    }

    public void setLongestInactivityInterval(int longestInactivityInterval) {
        this.longestInactivityInterval = longestInactivityInterval;
    }

    public int getCurrentInactivityInterval() {
        return currentInactivityInterval;
    }

    public void setCurrentInactivityInterval(int currentInactivityInterval) {
        this.currentInactivityInterval = currentInactivityInterval;
    }

    public int getTimesInactivityDetected() {
        return timesInactivityDetected;
    }

    public void setTimesInactivityDetected(int timesInactivityDetected) {
        this.timesInactivityDetected = timesInactivityDetected;
    }

    public int getUserPaGoal() {
        return userPaGoal;
    }

    public void setUserPaGoal(int userPaGoal) {
        this.userPaGoal = userPaGoal;
    }

    public int getUserMaxContInacGoal() {
        return userMaxContInacGoal;
    }

    public void setUserMaxContInacGoal(int userMaxContInacGoal) {
        this.userMaxContInacGoal = userMaxContInacGoal;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "user_id=" + user_id +
                ", date=" + date +
                ", activeTime=" + activeTime +
                ", longestInactivityInterval=" + longestInactivityInterval +
                ", currentInactivityInterval=" + currentInactivityInterval +
                ", timesInactivityDetected=" + timesInactivityDetected +
                ", userPaGoal=" + userPaGoal +
                ", userMaxContInacGoal=" + userMaxContInacGoal +
                '}';
    }
}
