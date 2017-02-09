package georgikoemdzhiev.activeminutes.data_collection.dagger;

import dagger.Subcomponent;
import georgikoemdzhiev.activeminutes.application.dagger.ActivityScope;
import georgikoemdzhiev.activeminutes.data_collection.view.DataCollectionActivity;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@ActivityScope
@Subcomponent(modules = DataCollectionModule.class)
public interface DataCollectionComponent {
    void inject(DataCollectionActivity activity);
}
