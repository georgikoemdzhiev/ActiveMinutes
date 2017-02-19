package georgikoemdzhiev.activeminutes.authentication_screen.presenter;

import georgikoemdzhiev.activeminutes.authentication_screen.view.ISignUpView;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public interface ISignUpPresenter {

    void signUp(String username, String password1, String password2);

    void setView(ISignUpView view);

    void releaseView();
}
