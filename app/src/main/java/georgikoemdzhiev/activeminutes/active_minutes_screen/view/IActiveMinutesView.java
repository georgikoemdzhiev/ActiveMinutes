package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

import georgikoemdzhiev.activeminutes.data_layer.db.User;

/**
 * Created by Georgi Koemdzhiev on 20/02/2017.
 */

public interface IActiveMinutesView {

    void userNotLoggedIn();

    void setLoggedInUser(User user);

    void showMessage(String message);
}
