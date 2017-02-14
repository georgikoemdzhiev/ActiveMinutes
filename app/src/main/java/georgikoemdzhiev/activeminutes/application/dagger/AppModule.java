package georgikoemdzhiev.activeminutes.application.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import georgikoemdzhiev.activeminutes.application.dagger.scopes.ApplicationScope;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @ApplicationScope
    Context provideContext() {
        return this.mContext;
    }


}
