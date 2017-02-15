package georgikoemdzhiev.activeminutes.application.dagger.components;

import dagger.Component;
import georgikoemdzhiev.activeminutes.application.dagger.modules.AppModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.DataModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.HarModule;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.services.DataCollectionService;

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

    void inject(DataCollectionService service);
}