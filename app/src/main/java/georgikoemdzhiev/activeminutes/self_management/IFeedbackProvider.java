package georgikoemdzhiev.activeminutes.self_management;

/**
 * Created by Georgi Koemdzhiev on 26/02/2017.
 */

public interface IFeedbackProvider {

    void provideEncouragingFeedback(int leftMinutesTillGoal);

    void provideGoalAchievedFeedback(int paGoal);

    void provideProlongedInactivityFeedback(int currentSt);
}
