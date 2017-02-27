package georgikoemdzhiev.activeminutes.initial_setup_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ActiveMinutesActivity;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;

public class InitialSetupActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildDependencies();

        addSlide(SleepingHoursFragment.newInstance());
        addSlide(PaGoalFragment.newInstance());
        addSlide(MaxContInacFragment.newInstance());

        // Show Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(this, ActiveMinutesActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ActiveMinutesApplication) getApplication()).releaseInitialSetupComponent();
    }

    private void buildDependencies() {
        ((ActiveMinutesApplication) getApplication()).buildInitialSetupComp();
    }

}