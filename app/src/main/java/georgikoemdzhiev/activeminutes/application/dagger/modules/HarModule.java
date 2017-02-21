package georgikoemdzhiev.activeminutes.application.dagger.modules;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.qualifiers.Named;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_layer.ITrainingDataManager;
import georgikoemdzhiev.activeminutes.har.ClassifierBuilder;
import georgikoemdzhiev.activeminutes.har.HarClassifyManager;
import georgikoemdzhiev.activeminutes.har.HarTrainManager;
import georgikoemdzhiev.activeminutes.har.IClassifierBuilder;
import georgikoemdzhiev.activeminutes.har.IHarManager;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */

@Module
public class HarModule {

    @Provides
    @ApplicationScope
    @Named("train")
    IHarManager provideTrainManager(ITrainingDataManager dataManager) {
        return new HarTrainManager(dataManager);
    }

    @Provides
    @ApplicationScope
    @Named("classify")
    IHarManager provideClassifyManager(ITrainingDataManager dataManager) {
        return new HarClassifyManager(dataManager);
    }

    @Provides
    @ApplicationScope
    IClassifierBuilder provideClassifierBuilder() {
        return new ClassifierBuilder();
    }
}
