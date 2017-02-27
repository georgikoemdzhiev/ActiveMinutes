package georgikoemdzhiev.activeminutes.initial_setup_screen.dagger;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.ISleepingHoursModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.SleepingHoursModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.ISleepingHoursPresenter;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.SleepingHoursPresenter;
import io.realm.Realm;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

@Module
public class InitialSetupModule {

    @Provides
    @ActivityScope
    ISleepingHoursModel provideSleepingHoursModel(IAuthDataManager authDataManager, Realm realm) {
        return new SleepingHoursModel(authDataManager, realm);
    }

    @Provides
    @ActivityScope
    ISleepingHoursPresenter provideSleepingHoursPresenter(ISleepingHoursModel model) {
        return new SleepingHoursPresenter(model);
    }
}
