package georgikoemdzhiev.activeminutes.authentication_screen.model;

import georgikoemdzhiev.activeminutes.data_layer.AuthResult;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public class SignUpModel implements ISignUpModel {
    private IAuthDataManager mAuthManager;

    public SignUpModel(IAuthDataManager dataManager) {
        mAuthManager = dataManager;
    }

    @Override
    public void signUpUser(String username, String password, final ActionResult result) {
        mAuthManager.createNewUser(username, password, new AuthResult() {
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
