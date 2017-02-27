package georgikoemdzhiev.activeminutes.initial_setup_screen.dagger;

import dagger.Subcomponent;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.MaxContInacFragment;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.PaGoalFragment;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.SleepingHoursFragment;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */
@ActivityScope
@Subcomponent(modules = InitialSetupModule.class)
public interface InitialSetupComponent {

    void inject(SleepingHoursFragment fragment);

    void inject(PaGoalFragment fragment);

    void inject(MaxContInacFragment fragment);
}
