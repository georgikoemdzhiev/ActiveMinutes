package georgikoemdzhiev.activeminutes.application.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_layer.AuthDataManager;
import georgikoemdzhiev.activeminutes.data_layer.ClassificationDataManager;
import georgikoemdzhiev.activeminutes.data_layer.FileManager;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IClassificationDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IFileManager;
import georgikoemdzhiev.activeminutes.data_layer.ITrainingDataManager;
import georgikoemdzhiev.activeminutes.data_layer.IUserManager;
import georgikoemdzhiev.activeminutes.data_layer.TrainingDataManager;
import georgikoemdzhiev.activeminutes.data_layer.UserManager;
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
    ITrainingDataManager provideTrainingDataManager(Realm realm, IFileManager fileManager) {
        return new TrainingDataManager(realm, fileManager);
    }

    @Provides
    @ApplicationScope
    IClassificationDataManager provideClassificationDataManager(IFileManager fileManager) {
        return new ClassificationDataManager(fileManager);
    }

    @Provides
    @ApplicationScope
    IAuthDataManager provideAuthDataManager(Realm realm, IUserManager userManager) {
        return new AuthDataManager(realm, userManager);
    }

    @Provides
    @ApplicationScope
    IFileManager providesFileManager(Context context) {
        return new FileManager(context);
    }

    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("ActiveMinutesPreferences", Context.MODE_PRIVATE);
    }

    @Provides
    @ApplicationScope
    IUserManager provideUserManager(SharedPreferences preferences) {
        return new UserManager(preferences);
    }
}
