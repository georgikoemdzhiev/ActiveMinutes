package georgikoemdzhiev.activeminutes.active_minutes_screen.presenter;

import georgikoemdzhiev.activeminutes.active_minutes_screen.view.IHistoryView;

/**
 * Created by Georgi Koemdzhiev on 08/03/2017.
 */

public interface IHistoryPresenter {

    void getDailyActivityData();

    void getWeeklyActivityData();

    void setView(IHistoryView view);

    void releaseView();
}
