package georgikoemdzhiev.activeminutes.application;

import android.app.Application;

import georgikoemdzhiev.activeminutes.application.dagger.components.AppComponent;
import georgikoemdzhiev.activeminutes.application.dagger.components.DaggerAppComponent;
import georgikoemdzhiev.activeminutes.application.dagger.modules.AppModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.DataModule;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthComponent;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthModule;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class ActiveMinutesApplication extends Application {
    private AppComponent mComponent;
    private DataCollectionComponent mDataCollectionComponent;
    private AuthComponent mAuthenticationComponent;

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
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        // Create default User with id = 0;
        User defaultUser = realm.createObject(User.class, 0);
        realm.commitTransaction();
    }

    public DataCollectionComponent buildDataCollComp() {
        mDataCollectionComponent = mComponent.plus(new DataCollectionModule());
        return mDataCollectionComponent;
    }

    public void releaseDataCollComp() {
        this.mDataCollectionComponent = null;
    }

    public AuthComponent buildAuthComp() {
        mAuthenticationComponent = mComponent.plus(new AuthModule());
        return mAuthenticationComponent;
    }

    public void releaseAuthComp() {
        this.mAuthenticationComponent = null;
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}
