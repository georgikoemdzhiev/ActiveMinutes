package georgikoemdzhiev.activeminutes.initial_setup_screen.presenter;

import georgikoemdzhiev.activeminutes.initial_setup_screen.view.IMaxContInacView;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public interface IMaxContInacPresenter {

    void setMCI(int mci);

    void setView(IMaxContInacView view);

    void releaseView();

}
