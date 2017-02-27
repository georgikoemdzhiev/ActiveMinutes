package georgikoemdzhiev.activeminutes.initial_setup_screen.presenter;

import georgikoemdzhiev.activeminutes.initial_setup_screen.view.IPaGoalView;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public interface IPaGoalPresenter {

    void setSetPa(int paGoal);

    void setView(IPaGoalView view);

    void releaseView();
}
