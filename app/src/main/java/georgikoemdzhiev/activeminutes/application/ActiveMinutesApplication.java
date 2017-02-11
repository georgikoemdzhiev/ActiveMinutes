package georgikoemdzhiev.activeminutes.application;

import android.app.Application;

import georgikoemdzhiev.activeminutes.application.dagger.AppComponent;
import georgikoemdzhiev.activeminutes.application.dagger.AppModule;
import georgikoemdzhiev.activeminutes.application.dagger.DaggerAppComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionComponent;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class ActiveMinutesApplication extends Application {
    private AppComponent mComponent;
    private DataCollectionComponent mDataCollectionComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

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
