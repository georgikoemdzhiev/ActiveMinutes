package georgikoemdzhiev.activeminutes.today_screen.dagger;

import dagger.Subcomponent;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.today_screen.view.ActiveMinutesActivity;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

@Subcomponent(modules = ActiveMinutesModule.class)
@ActivityScope
public interface ActiveMinutesComponent {

    void inject(ActiveMinutesActivity activity);
}
