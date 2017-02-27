package georgikoemdzhiev.activeminutes.initial_setup_screen.dagger;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.IMaxContInacModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.IPaGoalModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.ISleepingHoursModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.MaxContInacModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.PaGoalModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.model.SleepingHoursModel;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.IMaxContInacPresenter;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.IPaGoalPresenter;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.ISleepingHoursPresenter;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.MaxContInacPresenter;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.PaGoalPresenter;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.SleepingHoursPresenter;
import io.realm.Realm;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

@Module
public class InitialSetupModule {

    // SleepingHours fragment provide methods
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

    // PaGoal fragment provide methods

    @Provides
    @ActivityScope
    IPaGoalModel providePaGoalModel(IAuthDataManager authDataManager, Realm realm) {
        return new PaGoalModel(authDataManager, realm);
    }

    @Provides
    @ActivityScope
    IPaGoalPresenter providePaGoalPresenter(IPaGoalModel model) {
        return new PaGoalPresenter(model);
    }

    // MaxContInac fragment provide methods

    @Provides
    @ActivityScope
    IMaxContInacModel providesMaxContInacModel(IAuthDataManager authDataManager, Realm realm) {
        return new MaxContInacModel(authDataManager, realm);
    }

    @Provides
    @ActivityScope
    IMaxContInacPresenter provideMaxContInacPresenter(IMaxContInacModel model) {
        return new MaxContInacPresenter(model);
    }

}
