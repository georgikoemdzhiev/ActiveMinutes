package georgikoemdzhiev.activeminutes.application.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.Utils.IFileManager;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_manager.IDataManager;
import georgikoemdzhiev.activeminutes.har.HarManager;
import georgikoemdzhiev.activeminutes.har.IHarManager;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @ApplicationScope
    Context provideContext() {
        return this.mContext;
    }

    @Provides
    @ApplicationScope
    IHarManager provideHarManager(IFileManager fileManager, IDataManager dataManager) {
        return new HarManager(fileManager, dataManager);
    }

}
