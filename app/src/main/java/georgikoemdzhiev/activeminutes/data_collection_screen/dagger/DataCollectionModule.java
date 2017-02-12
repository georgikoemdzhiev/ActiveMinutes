package georgikoemdzhiev.activeminutes.data_collection_screen.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ActivityScope;
import georgikoemdzhiev.activeminutes.data_collection_screen.presenter.DataCollectionController;
import georgikoemdzhiev.activeminutes.data_collection_screen.presenter.DataCollectionPresenter;
import georgikoemdzhiev.activeminutes.data_collection_screen.presenter.IDataCollectionController;
import georgikoemdzhiev.activeminutes.data_collection_screen.presenter.IDataCollectionPresenter;
import georgikoemdzhiev.activeminutes.database.IDataManager;

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
    IDataCollectionPresenter provideDataCollectionPresenter(IDataCollectionController controller,
                                                            IDataManager dataManager) {
        return new DataCollectionPresenter(controller, dataManager);
    }

}
