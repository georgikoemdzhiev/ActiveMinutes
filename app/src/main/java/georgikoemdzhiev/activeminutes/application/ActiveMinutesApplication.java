package georgikoemdzhiev.activeminutes.application;

import android.app.Application;

import georgikoemdzhiev.activeminutes.application.dagger.components.AppComponent;
import georgikoemdzhiev.activeminutes.application.dagger.components.DaggerAppComponent;
import georgikoemdzhiev.activeminutes.application.dagger.modules.AppModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.DataModule;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class ActiveMinutesApplication extends Application {
    private AppComponent mComponent;
    private DataCollectionComponent mDataCollectionComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        // Initial Database setup
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        setUpRealmDatabase();

        // Initial Dagger setup
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .build();

    }

    private void setUpRealmDatabase() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded() // Only during for the development
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public DataCollectionComponent inject() {
        mDataCollectionComponent = mComponent.plus(new DataCollectionModule());
        return mDataCollectionComponent;
    }

    public void releaseDataCollectionComponent() {
        this.mDataCollectionComponent = null;
    }


    public AppComponent getComponent() {
        return mComponent;
    }
}
