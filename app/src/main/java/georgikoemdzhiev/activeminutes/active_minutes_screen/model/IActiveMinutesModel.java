package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 20/02/2017.
 */

public interface IActiveMinutesModel {

    boolean isUserLoggedIn();

    User getLoggedInUser();

    void logOutUser();
}
