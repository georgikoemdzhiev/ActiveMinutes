package georgikoemdzhiev.activeminutes.application.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_layer.DataManager;
import georgikoemdzhiev.activeminutes.data_layer.FileManager;
import georgikoemdzhiev.activeminutes.data_layer.IDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IFileManager;
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
    IDataManager provideDataManager(Realm realm, IFileManager fileManager) {
        return new DataManager(realm, fileManager);
    }

    @Provides
    @ApplicationScope
    IFileManager providesFileManager(Context context) {
        return new FileManager(context);
    }
}
