package georgikoemdzhiev.activeminutes.application.dagger;

import dagger.Component;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.services.DataCollectionService;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Component(modules = {AppModule.class, DataModule.class})
@ApplicationScope
public interface AppComponent {
    DataCollectionComponent plus(DataCollectionModule module);

    void inject(DataCollectionService service);
}
