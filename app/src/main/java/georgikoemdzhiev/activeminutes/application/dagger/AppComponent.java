package georgikoemdzhiev.activeminutes.application.dagger;

import javax.inject.Singleton;

import dagger.Component;
import georgikoemdzhiev.activeminutes.data_collection.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection.dagger.DataCollectionModule;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    DataCollectionComponent plus(DataCollectionModule module);
}
