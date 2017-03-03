package georgikoemdzhiev.activeminutes.application.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;
import georgikoemdzhiev.activeminutes.services.scheduler.SleepingHoursJobCreator;

/**
 * Created by Georgi Koemdzhiev on 02/03/2017.
 */

@Module
public class ServiceSchedulerModule {

    @Provides
    @ApplicationScope
    SleepingHoursJobCreator provideSHJobCreator(Context context) {
        return new SleepingHoursJobCreator(context);
    }
}
