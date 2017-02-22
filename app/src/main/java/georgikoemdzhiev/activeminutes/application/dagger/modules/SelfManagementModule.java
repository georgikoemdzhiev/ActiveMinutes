package georgikoemdzhiev.activeminutes.application.dagger.modules;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.self_management.ActivityMonitor;
import georgikoemdzhiev.activeminutes.self_management.IActivityMonitor;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

@Module
public class SelfManagementModule {

    @Provides
    @ApplicationScope
    IActivityMonitor provideActivityMonitor(IActivityDataManager dataManager) {
        return new ActivityMonitor(dataManager);
    }
}
