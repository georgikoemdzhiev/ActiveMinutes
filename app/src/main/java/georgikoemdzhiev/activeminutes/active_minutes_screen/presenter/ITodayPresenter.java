package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ITodayView;

/**
 * Created by Georgi Koemdzhiev on 24/02/2017.
 */

public interface ITodayPresenter {

    void getActivityInformation();

    void setView(ITodayView view);

    void releaseView();
}
