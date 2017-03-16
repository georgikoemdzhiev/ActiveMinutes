package georgikoemdzhiev.activeminutes.application;

import android.app.Application;
import android.preference.PreferenceManager;

import com.evernote.android.job.JobManager;

import javax.inject.Inject;

import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.dagger.ActiveMinutesComponent;
import georgikoemdzhiev.activeminutes.active_minutes_screen.dagger.ActiveMinutesModule;
import georgikoemdzhiev.activeminutes.application.dagger.components.AppComponent;
import georgikoemdzhiev.activeminutes.application.dagger.components.DaggerAppComponent;
import georgikoemdzhiev.activeminutes.application.dagger.modules.AppModule;
import georgikoemdzhiev.activeminutes.application.dagger.modules.DataModule;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthComponent;
import georgikoemdzhiev.activeminutes.authentication_screen.dagger.AuthModule;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.data_layer.IFileManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.har.IClassifierBuilder;
import georgikoemdzhiev.activeminutes.initial_setup_screen.dagger.InitialSetupComponent;
import georgikoemdzhiev.activeminutes.initial_setup_screen.dagger.InitialSetupModule;
import georgikoemdzhiev.activeminutes.services.scheduler.SleepingHoursJobCreator;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class ActiveMinutesApplication extends Application {
    @Inject
    IFileManager mFileManager;
    @Inject
    IClassifierBuilder mClassifierBuilder;
    @Inject
    SleepingHoursJobCreator mSleepingHoursJobCreator;

    private AppComponent mComponent;
    private DataCollectionComponent mDataCollectionComponent;
    private AuthComponent mAuthenticationComponent;
    private ActiveMinutesComponent mActiveMinutesComponent;
    private InitialSetupComponent mInitialSetupComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // Set the default values of the Settings screen preferences
        PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, false);
        // Initial Database setup
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        setUpRealmDatabase();

        // Initial Dagger setup
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .build();

        mComponent.inject(this);
        // Create JobManager to manage scheduling periodic tasks
        JobManager.create(this).addJobCreator(mSleepingHoursJobCreator);

        setUpGenericClassifier();
    }


    private void setUpRealmDatabase() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded() // Only during for the development
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();

        // Find all users with id == 0 (default user's id)
        RealmResults<User> users = realm.where(User.class)
                .equalTo("userId", 0).findAll();
        // Check if the default user has been created already...
        if (users.size() == 0) {
            // Default user has not been created. Create default User with id = 0;
            realm.beginTransaction();
            User defaultUser = realm.createObject(User.class, 0);
            realm.commitTransaction();
        }

    }

    /***
     * Method that reads the default ARFF dataset file from the assets folder,
     * trains a generic classifier and saves it to the HAR directory for
     * later use in ActiveMinutesService
     */
    public void setUpGenericClassifier() {
        Instances genericDataset = mFileManager.readArffFileFromAssets();
        IBk ibkClassifier = (IBk) mClassifierBuilder.buildClassifier(genericDataset);
        mFileManager.serialiseClassifierAndStoreToSDCard(ibkClassifier);
        System.out.println("Generic classifier trained and stored to SD Card!");
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

    public ActiveMinutesComponent buildActiveMinutesComp() {
        mActiveMinutesComponent = mComponent.plus(new ActiveMinutesModule());
        return mActiveMinutesComponent;
    }

    public void releaseActiveMinutesComp() {
        this.mActiveMinutesComponent = null;
    }

    public void buildInitialSetupComp() {
        mInitialSetupComponent = mComponent.plus(new InitialSetupModule());
    }

    public InitialSetupComponent getInitialSetupComponent() {
        return mInitialSetupComponent;
    }

    public void releaseInitialSetupComponent() {
        mInitialSetupComponent = null;
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}
