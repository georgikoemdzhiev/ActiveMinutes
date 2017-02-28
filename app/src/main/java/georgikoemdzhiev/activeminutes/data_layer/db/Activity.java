package georgikoemdzhiev.activeminutes.data_layer.db;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class Activity extends RealmObject {
    @PrimaryKey
    private int activity_id;
    private int user_id;
    private Date date;
    private int activeTime;

    private int longestInactivityInterval;
    private int currentInactivityInterval;
    private int averageInactInterval;
    private int totalInactivityTime;
    private int timesInactivityDetected;
    // goal and targets
    private int userPaGoal;
    private int userMaxContInacTarget;

    public Activity() {
        this.timesInactivityDetected = 1;
    }

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


    public int getUserPaGoal() {
        return userPaGoal;
    }

    public void setUserPaGoal(int userPaGoal) {
        this.userPaGoal = userPaGoal;
    }

    public int getUserMaxContInacTarget() {
        return userMaxContInacTarget;
    }

    public void setUserMaxContInacTarget(int userMaxContInacTarget) {
        this.userMaxContInacTarget = userMaxContInacTarget;
    }

    public int getAverageInactInterval() {
        return averageInactInterval;
    }

    public void setAverageInactInterval(int averageInactInterval) {
        this.averageInactInterval = averageInactInterval;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getTotalInactivityTime() {
        return totalInactivityTime;
    }

    public void setTotalInactivityTime(int totalInactivityTime) {
        this.totalInactivityTime = totalInactivityTime;
    }

    public int getTimesInactivityDetected() {
        return timesInactivityDetected;
    }

    public void setTimesInactivityDetected(int timesInactivityDetected) {
        this.timesInactivityDetected = timesInactivityDetected;
    }

    @Override
    public String toString() {
        return "Activity{" + "\n" +
                "activity_id=" + activity_id + "\n" +
                ", user_id=" + user_id + "\n" +
                ", date=" + date + "\n" +
                ", activeTime=" + activeTime + "\n" +
                ", longestInactivityInterval=" + longestInactivityInterval + "\n" +
                ", currentInactivityInterval=" + currentInactivityInterval + "\n" +
                ", averageInactInterval=" + averageInactInterval + "\n" +
                ", userPaGoal=" + userPaGoal + "\n" +
                ", userMaxContInacTarget=" + userMaxContInacTarget + "\n" +
                ", totalInactivityTime=" + totalInactivityTime + "\n" +
                ", timesInactivityDetected=" + timesInactivityDetected + "\n" +
                '}' + "\n";
    }
}
