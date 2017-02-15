package georgikoemdzhiev.activeminutes.authentication_screen.dagger;

import dagger.Subcomponent;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.authentication_screen.view.LoginFragment;

/**
 * Created by Georgi Koemdzhiev on 15/02/2017.
 */

@ActivityScope
@Subcomponent(modules = AuthModule.class)
public interface AuthComponent {

    void inject(LoginFragment loginFragment);

}
