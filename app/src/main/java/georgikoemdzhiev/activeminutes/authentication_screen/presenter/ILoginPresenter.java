package georgikoemdzhiev.activeminutes.authentication_screen.presenter;

import georgikoemdzhiev.activeminutes.authentication_screen.view.ILoginView;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public interface ILoginPresenter {

    void login(String username, String password);

    void setView(ILoginView view);

    void releaseView();
}
