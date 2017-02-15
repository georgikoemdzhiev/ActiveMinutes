package georgikoemdzhiev.activeminutes.authentication_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.AuthResult;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

public class LoginModel implements ILoginModel {
    private IAuthDataManager mDataManager;

    public LoginModel(IAuthDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loginUser(String username, String password, final ActionResult result) {
        mDataManager.login(username, password, new AuthResult() {
            @Override
            public void onSuccess(String message) {
                result.onSuccess(message);
            }

            @Override
            public void onError(String message) {
                result.onError(message);
            }
        });
    }
}
