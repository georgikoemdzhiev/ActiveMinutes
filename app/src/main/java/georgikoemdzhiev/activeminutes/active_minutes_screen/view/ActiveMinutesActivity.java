package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.IActiveMinutesPresenter;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.authentication_screen.view.AuthenticationActivity;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.services.ActiveMinutesService;

public class ActiveMinutesActivity extends AppCompatActivity implements IActiveMinutesView {
    @Inject
    IActiveMinutesPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_minutes);
        ButterKnife.bind(this);
        satisfyDependencies();
        setSupportActionBar(mToolbar);
        mPresenter.setView(this);

        mPresenter.isUserLoggedIn();
        // Load today screen...
        loadScreen(1);
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
                break;
            case 3:
                fragment = HistoryFragment.newInstance();
                break;
            case 4:

                break;
            case 5:
                mPresenter.logOutUser();
                stopService(new Intent(this, ActiveMinutesService.class));
                startActivity(new Intent(ActiveMinutesActivity.this, AuthenticationActivity.class));
                finish();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
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
        setUpNavigationDrawer();
        // start the ActiveMinutesService
        startService(new Intent(this, ActiveMinutesService.class));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
