package georgikoemdzhiev.activeminutes.har;

/**
 * Created by koemdzhiev on 10/02/2017.
 */

public interface IHarManager {

    void feedData(float[] xyz, long timestamp);

    void issueTimeWindow();

    void resetWindowBegTime();

    void setActivityLabel(String activityLabel);
}
