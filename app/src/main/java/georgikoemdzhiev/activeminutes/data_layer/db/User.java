package georgikoemdzhiev.activeminutes.data_layer.db;

import java.util.Arrays;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public class User extends RealmObject {
    @PrimaryKey
    private int userId;
    private String username;
    private String password;
    private int paGoal;
    private int maxContInactTarget;
    private boolean isClassifierPersonalised;
    private boolean isFirstTime;
    private byte[] profilePicture;
    private Date startAMService;
    private Date stopAMService;

    public User() {
        // 30 minutes = 1800 seconds
        this.paGoal = 30 * 60;
        // 60 minutues = 3600 seconds
        this.maxContInactTarget = 60 * 60;
        isClassifierPersonalised = false;
        isFirstTime = true;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPaGoal() {
        return paGoal;
    }

    public void setPaGoal(int paGoal) {
        this.paGoal = paGoal;
    }

    public int getMaxContInactTarget() {
        return maxContInactTarget;
    }

    public void setMaxContInactTarget(int maxContInactTarget) {
        this.maxContInactTarget = maxContInactTarget;
    }

    public boolean isClassifierPersonalised() {
        return isClassifierPersonalised;
    }

    public void setClassifierPersonalised(boolean classifierPersonalised) {
        isClassifierPersonalised = classifierPersonalised;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Date getStartAMService() {
        return startAMService;
    }

    public void setStartAMService(Date startAMService) {
        this.startAMService = startAMService;
    }

    public Date getStopAMService() {
        return stopAMService;
    }

    public void setStopAMService(Date stopAMService) {
        this.stopAMService = stopAMService;
    }

    public boolean getFirstTime() {
        return isFirstTime;
    }

    public void setIsFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", paGoal=" + paGoal +
                ", maxContInactTarget=" + maxContInactTarget +
                ", isClassifierPersonalised=" + isClassifierPersonalised +
                ", isFirstTime=" + isFirstTime +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                ", startAMService=" + startAMService +
                ", stopAMService=" + stopAMService +
                '}';
    }
}
