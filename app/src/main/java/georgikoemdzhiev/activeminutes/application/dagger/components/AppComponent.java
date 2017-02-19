package georgikoemdzhiev.activeminutes.application.dagger.components;

import dagger.Component;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.application.dagger.modules.AppModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.DataModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.HarModule;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthComponent;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthModule;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.services.DataCollectionService;
import georgikoemdzhiev.activeminutes.today_screen.dagger.ActiveMinutesComponent;
import georgikoemdzhiev.activeminutes.today_screen.dagger.ActiveMinutesModule;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Component(modules = {
        AppModule.class,
        DataModule.class,
        HarModule.class
})
@ApplicationScope
public interface AppComponent {

    DataCollectionComponent plus(DataCollectionModule module);

    AuthComponent plus(AuthModule module);

    ActiveMinutesComponent plus(ActiveMinutesModule module);

    void inject(DataCollectionService service);

    void inject(ActiveMinutesApplication application);
}
