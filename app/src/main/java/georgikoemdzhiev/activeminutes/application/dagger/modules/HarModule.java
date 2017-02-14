package georgikoemdzhiev.activeminutes.application.dagger.modules;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.qualifiers.Named;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_layer.IDataManager;
import georgikoemdzhiev.activeminutes.har.HarClassifyManager;
import georgikoemdzhiev.activeminutes.har.HarTrainManager;
import georgikoemdzhiev.activeminutes.har.IHarManager;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */

@Module
public class HarModule {

    @Provides
    @ApplicationScope
    @Named("train")
    IHarManager provideTrainManager(IDataManager dataManager) {
        return new HarTrainManager(dataManager);
    }

    @Provides
    @ApplicationScope
    @Named("classify")
    IHarManager provideClassifyManager(IDataManager dataManager) {
        return new HarClassifyManager(dataManager);
    }
}
