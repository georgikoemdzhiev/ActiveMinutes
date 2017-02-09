package georgikoemdzhiev.activeminutes.application.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Singleton
@Module()
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return this.mContext;
    }
}
