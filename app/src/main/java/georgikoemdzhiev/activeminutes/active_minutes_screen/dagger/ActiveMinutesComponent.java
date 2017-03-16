package georgikoemdzhiev.activeminutes.active_minutes_screen.dagger;

import dagger.Subcomponent;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ActiveMinutesActivity;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.HistoryFragment;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.SettingsFragment;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.TodayFragment;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

@Subcomponent(modules = ActiveMinutesModule.class)
@ActivityScope
public interface ActiveMinutesComponent {

    void inject(ActiveMinutesActivity activity);

    void inject(TodayFragment fragment);

    void inject(HistoryFragment fragment);

    void inject(SettingsFragment fragment);
}
