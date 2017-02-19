package georgikoemdzhiev.activeminutes.authentication_screen.presenter;

import georgikoemdzhiev.activeminutes.authentication_screen.model.ActionResult;
import georgikoemdzhiev.activeminutes.authentication_screen.model.ISignUpModel;
import georgikoemdzhiev.activeminutes.authentication_screen.view.ISignUpView;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public class SignUpPresenter implements ISignUpPresenter {
    private ISignUpModel model;
    private ISignUpView view;

    public SignUpPresenter(ISignUpModel model) {
        this.model = model;
    }


    @Override
    public void signUp(String username, String password1, String password2) {
        if (!password1.equals(password2)) {
            view.showDialogMessage("Passwords do not match!");
            return;
        }

        model.signUpUser(username, password1, new ActionResult() {
            @Override
            public void onSuccess(String message) {
                view.showDialogMessage("New user created!");
                view.navigateToTodayScreen();
            }

            @Override
            public void onError(String message) {
                view.showDialogMessage("Cannot create user with these credentials!");
            }
        });
    }

    @Override
    public void setView(ISignUpView view) {
        this.view = view;
    }

    @Override
    public void releaseView() {

    }
}
