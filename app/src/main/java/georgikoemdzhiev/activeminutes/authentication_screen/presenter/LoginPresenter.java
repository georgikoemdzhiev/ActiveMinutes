package georgikoemdzhiev.activeminutes.authentication_screen.presenter;

import georgikoemdzhiev.activeminutes.authentication_screen.model.ActionResult;
import georgikoemdzhiev.activeminutes.authentication_screen.model.ILoginModel;
import georgikoemdzhiev.activeminutes.authentication_screen.view.ILoginView;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public class LoginPresenter implements ILoginPresenter {
    private ILoginView view;
    private ILoginModel model;

    public LoginPresenter(ILoginModel model) {
        this.model = model;
    }

    @Override
    public void login(String username, String password) {
        model.loginUser(username, password, new ActionResult() {
            @Override
            public void onSuccess(String message) {
                view.showDialogMessage(message);
            }

            @Override
            public void onError(String message) {
                view.showDialogMessage(message);
            }
        });
    }

    public void setView(ILoginView view) {
        this.view = view;
    }

    public void releaseView() {
        this.view = null;
    }
}
