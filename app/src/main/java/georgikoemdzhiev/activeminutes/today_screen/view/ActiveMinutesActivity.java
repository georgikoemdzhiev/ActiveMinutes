package georgikoemdzhiev.activeminutes.today_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.authentication_screen.view.AuthenticationActivity;
import georgikoemdzhiev.activeminutes.data_layer.IUserManager;

public class ActiveMinutesActivity extends AppCompatActivity {
    @Inject
    IUserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_minutes);
        satisfyDependencies();

        if (!mUserManager.isLoggedIn()) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ActiveMinutesApplication) getApplication())
                .releaseActiveMinutesComp();

    }

    /***
     * This method performs the dependency injection
     * and satisfies the dependencies in this class
     */
    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getApplication())
                .buildActiveMinutesComp()
                .inject(this);
    }
}
