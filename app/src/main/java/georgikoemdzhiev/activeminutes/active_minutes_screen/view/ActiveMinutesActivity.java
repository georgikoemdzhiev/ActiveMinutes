package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.IActiveMinutesPresenter;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.authentication_screen.view.AuthenticationActivity;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.initial_setup_screen.view.InitialSetupActivity;
import georgikoemdzhiev.activeminutes.services.ActiveMinutesService;
import georgikoemdzhiev.activeminutes.services.scheduler.CheckUserSHJob;

public class ActiveMinutesActivity extends AppCompatActivity implements IActiveMinutesView {
    private static final String TAG = ActiveMinutesActivity.class.getSimpleName();
    public static String CHECK_SH_JOB_ID_KEY = "check_sh_job_id_key";
    @Inject
    IActiveMinutesPresenter mPresenter;
    @Inject
    SharedPreferences mSharedPreferences;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private JobManager mJobManager;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_minutes);
        ButterKnife.bind(this);
        satisfyDependencies();
        setSupportActionBar(mToolbar);
        mJobManager = JobManager.instance();

        mPresenter.setView(this);

        mPresenter.isUserLoggedIn();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ActiveMinutesApplication) getApplication())
                .releaseActiveMinutesComp();
        mPresenter.releaseView();

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

    private void setUpNavigationDrawer() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.account_header_bg)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(mUser.getUsername())
                                .withEmail(String.valueOf(mUser.getUserId()))
                                .withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        // Create drawer menu items
        PrimaryDrawerItem todayItem = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_today);
        SecondaryDrawerItem historyItem = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_history);
        PrimaryDrawerItem logOutItem = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_log_out);
        SecondaryDrawerItem settingsItem = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_settings);
        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        todayItem,
                        new DividerDrawerItem(),
                        historyItem,
                        settingsItem
                )
                .addStickyDrawerItems(
                        new SectionDrawerItem().withDivider(false).withName(R.string.drawer_sticky_header_title)
                        , logOutItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            loadScreen((int) drawerItem.getIdentifier());
                        }
                        return false;
                    }
                })
                .build();
    }

    private void loadScreen(int item) {
        Fragment fragment = null;

        switch (item) {
            case 1:
                fragment = TodayFragment.newInstance();
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(getString(R.string.drawer_item_today));
                break;
            case 3:
                fragment = HistoryFragment.newInstance();
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(getString(R.string.drawer_item_history));
                break;
            case 4:
                fragment = SettingsFragment.newInstance();
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(getString(R.string.drawer_item_settings));
                break;
            case 5:
                mPresenter.logOutUser();
                stopService(new Intent(this, ActiveMinutesService.class));

                mJobManager.cancel(mSharedPreferences.getInt(CHECK_SH_JOB_ID_KEY, -1));

                startActivity(new Intent(ActiveMinutesActivity.this, AuthenticationActivity.class));
                finish();
                break;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (fragment != null)
            ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void userNotLoggedIn() {
        startActivity(new Intent(this, AuthenticationActivity.class));
        finish();
    }

    /***
     * Method called upon successful check for logged in user
     *
     * @param user the logged in user passes as a parameter
     */
    @Override
    public void setLoggedInUser(User user) {
        this.mUser = user;
        // check if to show the initial set up screen
        mPresenter.isFirstTimeLaunch();
        Log.e(TAG, "Logged in user info: " + user.toString());

    }

    @Override
    public void showInitialSetup() {
        startActivity(new Intent(this, InitialSetupActivity.class));
    }

    @Override
    public void loadDrawerScreens() {
        setUpNavigationDrawer();
        // Load today screen...
        loadScreen(1);
    }

    @Override
    public void startService() {
        // start the ActiveMinutesService
        startService(new Intent(this, ActiveMinutesService.class));
    }

    @Override
    public void scheduleService() {
        Date startSleepingHours = mUser.getStartSleepingHours();
        Date stopSleepingHours = mUser.getStopSleepingHours();

        CheckUserSHJob checkUserSHJob = new CheckUserSHJob(this);

        int jobId = checkUserSHJob.scheduleSleepingHoursCheckJob(startSleepingHours, stopSleepingHours);
        mSharedPreferences.edit().putInt(CHECK_SH_JOB_ID_KEY, jobId).apply();

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
