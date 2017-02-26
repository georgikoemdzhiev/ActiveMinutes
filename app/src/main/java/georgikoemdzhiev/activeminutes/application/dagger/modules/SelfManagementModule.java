package georgikoemdzhiev.activeminutes.application.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.data_layer.IActivityDataManager;
import georgikoemdzhiev.activeminutes.self_management.ActivityMonitor;
import georgikoemdzhiev.activeminutes.self_management.FeedbackProvider;
import georgikoemdzhiev.activeminutes.self_management.IActivityMonitor;
import georgikoemdzhiev.activeminutes.self_management.IFeedbackProvider;

/**
 * Created by Georgi Koemdzhiev on 21/02/2017.
 */

@Module
public class SelfManagementModule {

    @Provides
    @ApplicationScope
    IActivityMonitor provideActivityMonitor(IActivityDataManager dataManager,
                                            IFeedbackProvider feedbackProvider) {
        return new ActivityMonitor(dataManager, feedbackProvider);
    }

    @Provides
    @ApplicationScope
    IFeedbackProvider provideFeedbackProvider(Context context,
                                              SharedPreferences sharedPreferences) {
        return new FeedbackProvider(context, sharedPreferences);
    }
}
