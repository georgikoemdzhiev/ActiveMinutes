package georgikoemdzhiev.activeminutes.application.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.Utils.FileManager;
import georgikoemdzhiev.activeminutes.Utils.IFileManager;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.database.DataManager;
import georgikoemdzhiev.activeminutes.database.IDataManager;
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
