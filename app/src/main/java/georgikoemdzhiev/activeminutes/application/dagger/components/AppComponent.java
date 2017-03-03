package georgikoemdzhiev.activeminutes.application.dagger.components;

import dagger.Component;
import georgikoemdzhiev.activeminutes.active_minutes_screen.dagger.ActiveMinutesComponent;
import georgikoemdzhiev.activeminutes.active_minutes_screen.dagger.ActiveMinutesModule;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.application.dagger.modules.AppModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.DataModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.HarModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.SelfManagementModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.ServiceSchedulerModule;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthComponent;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthModule;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.initial_setup_screen.dagger.InitialSetupComponent;
import georgikoemdzhiev.activeminutes.initial_setup_screen.dagger.InitialSetupModule;
import georgikoemdzhiev.activeminutes.services.ActiveMinutesService;
import georgikoemdzhiev.activeminutes.services.DataCollectionService;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Component(modules = {
        AppModule.class,
        DataModule.class,
        HarModule.class,
        SelfManagementModule.class,
        ServiceSchedulerModule.class
})
@ApplicationScope
public interface AppComponent {

    DataCollectionComponent plus(DataCollectionModule module);

    AuthComponent plus(AuthModule module);

    ActiveMinutesComponent plus(ActiveMinutesModule module);

    InitialSetupComponent plus(InitialSetupModule module);

    void inject(DataCollectionService service);

    void inject(ActiveMinutesService service);

    void inject(ActiveMinutesApplication application);
}
