package georgikoemdzhiev.activeminutes.authentication_screen.dagger;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.authentication_screen.model.ILoginModel;
import georgikoemdzhiev.activeminutes.authentication_screen.model.ISignUpModel;
import georgikoemdzhiev.activeminutes.authentication_screen.model.LoginModel;
import georgikoemdzhiev.activeminutes.authentication_screen.model.SignUpModel;
import georgikoemdzhiev.activeminutes.authentication_screen.presenter.ILoginPresenter;
import georgikoemdzhiev.activeminutes.authentication_screen.presenter.ISignUpPresenter;
import georgikoemdzhiev.activeminutes.authentication_screen.presenter.LoginPresenter;
import georgikoemdzhiev.activeminutes.authentication_screen.presenter.SignUpPresenter;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

@Module
public class AuthModule {

    @Provides
    @ActivityScope
    ILoginModel provideLoginModel(IAuthDataManager dataManager) {
        return new LoginModel(dataManager);
    }

    @Provides
    @ActivityScope
    ILoginPresenter provideLoginPresenter(ILoginModel model) {
        return new LoginPresenter(model);
    }

    @Provides
    @ActivityScope
    ISignUpModel provideSignUpModel(IAuthDataManager dataManager) {
        return new SignUpModel(dataManager);
    }

    @Provides
    @ActivityScope
    ISignUpPresenter provideSignUpPresenter(ISignUpModel model) {
        return new SignUpPresenter(model);
    }
}
