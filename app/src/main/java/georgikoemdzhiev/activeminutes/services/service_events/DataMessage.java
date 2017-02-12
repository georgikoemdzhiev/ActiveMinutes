package georgikoemdzhiev.activeminutes.services.service_events;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public class DataMessage {
    private String activityLabel;

    public DataMessage(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public String getActivityLabel() {
        return activityLabel;
    }
}
