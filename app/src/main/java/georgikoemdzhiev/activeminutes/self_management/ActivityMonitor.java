package georgikoemdzhiev.activeminutes.self_management;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.utils.LimitedSizeList;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

public class ActivityMonitor implements Observer, IActivityMonitor {
    // increment time is 3 seconds
    private final int INCREMENT_AMOUNT = 3;
    private final double ENCOURAGEMENT_PERCENTAGE = 0.8;
    private final double WARNING_PERCENTAGE = 0.8;
    private IActivityDataManager mDataManager;
    private ArrayList<Integer> correctionList;
    private IFeedbackProvider mFeedbackProvider;

    public ActivityMonitor(IActivityDataManager dataManager, IFeedbackProvider feedbackProvider) {
        mDataManager = dataManager;
        correctionList = new LimitedSizeList<>(7);
        this.mFeedbackProvider = feedbackProvider;
    }

    @Override
    public void update(Observable observable, Object activity) {
        monitorActivity(activity);
    }

    @Override
    public void monitorActivity(Object activityClass) {
        int activityInt = (int) activityClass;
        int currentPa = 0;
        int currentSt = 0;
        // Get the user's PA goal...
        int paGoal = mDataManager.getUserPaGoal();
        int stTarget = mDataManager.getMaxContInacTarget();

        correctionList.add(activityInt);
        int correctResult = applyCorrection();
        // check of the correctedResult contains activity of type ACTIVE
        if (correctResult == correctionList.size()) {
            // It does, so incrementActive time.
            currentPa = mDataManager.incActiveTime();
            // clear current inactive interval since activity is detected...
            mDataManager.clearCurrentInacInterval();
        } else {
            currentSt = mDataManager.incCurrentInacInterval();
        }
        // Check if the user's goal is achieved and make sure that
        // the notification is send only once
        if (currentPa > paGoal && currentPa <= paGoal + INCREMENT_AMOUNT) {
            // is achieved, so notify for goal achieved..
            mFeedbackProvider.provideGoalAchievedFeedback(paGoal);
        }
        // Check to see if the PA goal is 80% achieved
        if (currentPa > paGoal * ENCOURAGEMENT_PERCENTAGE &&
                currentPa <= paGoal * ENCOURAGEMENT_PERCENTAGE + INCREMENT_AMOUNT) {
            // is achieved, so notify for goal achieved..
            int leftMinutesTillGoal = (int) (paGoal - (paGoal * ENCOURAGEMENT_PERCENTAGE));
            mFeedbackProvider.provideEncouragingFeedback(leftMinutesTillGoal);
        }
        // Check if the maximum continuous inactivity is reached
//        if (currentSt > stTarget && currentSt <= stTarget + INCREMENT_AMOUNT) {
//            mFeedbackProvider.provideProlongedInactivityFeedback(currentSt);
//        }

        if (currentSt % stTarget == 0 && currentSt != 0) {
            mFeedbackProvider.provideProlongedInactivityFeedback(currentSt);
            System.out.println("currentSt % stTarget == 0 currentSt: " + currentSt);
        }
        // Check to see if the ST target is 80% reached
        if (currentSt > stTarget * WARNING_PERCENTAGE &&
                currentSt <= stTarget * WARNING_PERCENTAGE + INCREMENT_AMOUNT) {
            mFeedbackProvider.provideWarningProlongedInactivityFeedback(currentSt);
        }
    }

    @Override
    public void setUser(User user) {
        this.mDataManager.setUser(user);
    }

    private int applyCorrection() {
        int count = 0;
        // iterate through the correctionList containing the last 3 recognised classes (i.e. 0,1,3)
        for (int item : correctionList) {
            // check if the current item is of class "cycling" - 3; "running" - 1;"walking" - 0
            if (item == 0 || item == 1 || item == 3) {
                // if it is, increment the counter
                count++;
            }
        }

        return count;
    }
}
