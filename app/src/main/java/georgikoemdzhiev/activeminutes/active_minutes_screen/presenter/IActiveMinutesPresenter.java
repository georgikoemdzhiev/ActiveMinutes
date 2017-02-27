package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import georgikoemdzhiev.activeminutes.active_minutes_screen.view.IActiveMinutesView;

/**
 * Created by Georgi Koemdzhiev on 20/02/2017.
 */

public interface IActiveMinutesPresenter {

    void isFirstTimeLaunch();

    void isUserLoggedIn();

    void logOutUser();

    void setView(IActiveMinutesView view);

    void releaseView();
}
