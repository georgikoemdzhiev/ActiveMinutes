package georgikoemdzhiev.activeminutes.application.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_layer.FileManager;
import georgikoemdzhiev.activeminutes.data_layer.HarDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IFileManager;
import georgikoemdzhiev.activeminutes.data_layer.IHarDataManager;
import io.realm.Realm;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

@Module
public class DataModule {

    public DataModule() {
    }

    @Provides
    @ApplicationScope
    Realm provideRealmDB() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @ApplicationScope
    IHarDataManager provideDataManager(Realm realm, IFileManager fileManager) {
        return new HarDataManager(realm, fileManager);
    }

    @Provides
    @ApplicationScope
    IFileManager providesFileManager(Context context) {
        return new FileManager(context);
    }
}
