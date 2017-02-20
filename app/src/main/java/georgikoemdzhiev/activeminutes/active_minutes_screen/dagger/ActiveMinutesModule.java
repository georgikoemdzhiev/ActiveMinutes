package georgikoemdzhiev.activeminutes.active_minutes_screen.dagger;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.ActiveMinutesModel;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.IActiveMinutesModel;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.ActiveMinutesPresenter;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.IActiveMinutesPresenter;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

@Module
public class ActiveMinutesModule {

    @Provides
    @ActivityScope
    IActiveMinutesModel provideActiveMinutesModel(IAuthDataManager manager) {
        return new ActiveMinutesModel(manager);
    }

    @Provides
    @ActivityScope
    IActiveMinutesPresenter provideActiveMinutesPresenter(IActiveMinutesModel model) {
        return new ActiveMinutesPresenter(model);
    }
}
