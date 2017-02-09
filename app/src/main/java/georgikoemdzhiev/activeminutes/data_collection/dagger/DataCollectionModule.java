package georgikoemdzhiev.activeminutes.data_collection.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.ActivityScope;
import georgikoemdzhiev.activeminutes.data_collection.presenter.DataCollectionController;
import georgikoemdzhiev.activeminutes.data_collection.presenter.DataCollectionPresenter;
import georgikoemdzhiev.activeminutes.data_collection.presenter.IDataCollectionController;
import georgikoemdzhiev.activeminutes.data_collection.presenter.IDataCollectionPresenter;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Module
public class DataCollectionModule {

    @Provides
    @ActivityScope
    IDataCollectionController provideDataCollectionController(Context context) {
        return new DataCollectionController(context);
    }

    @Provides
    @ActivityScope
    IDataCollectionPresenter provideDataCollectionPresenter(IDataCollectionController controller) {
        return new DataCollectionPresenter(controller);
    }

}
