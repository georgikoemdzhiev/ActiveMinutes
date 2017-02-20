package georgikoemdzhiev.activeminutes.active_minutes_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.authentication_screen.view.AuthenticationActivity;
import georgikoemdzhiev.activeminutes.data_layer.IUserManager;

public class ActiveMinutesActivity extends AppCompatActivity {
    @Inject
    IUserManager mUserManager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_minutes);
        ButterKnife.bind(this);
        satisfyDependencies();
        setSupportActionBar(mToolbar);

        // Check if the user is logged in...
        if (!mUserManager.isLoggedIn()) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
        }

        setUpNavigationDrawer();
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

    private void setUpNavigationDrawer() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.account_header_bg)
                .addProfiles(
                        new ProfileDrawerItem().withName("Georgi Koemdzhiev").withEmail("koemdzhiev@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile))
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
        SecondaryDrawerItem historyItem = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_history);
        PrimaryDrawerItem logOutItem = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_log_out);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        todayItem,
                        new DividerDrawerItem(),
                        historyItem,
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings)
                )
                .addStickyDrawerItems(
                        new SectionDrawerItem().withDivider(false).withName(R.string.drawer_sticky_header_title)
                        , logOutItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                })
                .build();
    }
}
