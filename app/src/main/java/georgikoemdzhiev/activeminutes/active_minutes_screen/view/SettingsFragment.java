package georgikoemdzhiev.activeminutes.active_minutes_screen.view;


import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.ISettingsPresenter;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import georgikoemdzhiev.activeminutes.services.scheduler.CheckUserSHJob;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener,
        TimePickerDialog.OnTimeSetListener, ISettingsView {
    public static final String TIMERANGEPICKER_TAG = "timerangepicker";
    private final String SLEEPING_HOURS_KEY = "pref_key_sleeping_hours";
    private final String PA_GOAL_KEY = "pref_key_pa_goal";
    private final String ST_GOAL_KEY = "pref_key_st_goal";
    private final String NOTIFICATION_CB_KEY = "pref_key_notify_when_inactive";
    @Inject
    ISettingsPresenter mPresenter;
    @Inject
    IAuthDataManager mIAuthDataManager;
    private CheckBoxPreference mCheckBoxPreference;
    private Preference mSleepingHours;
    private Preference mPaGoal;
    private Preference mStGoal;


    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);
        satisfyDependencies();

        mPresenter.setView(this);

        mSleepingHours = findPreference(SLEEPING_HOURS_KEY);
        mSleepingHours.setOnPreferenceClickListener(this);
        mPaGoal = findPreference(PA_GOAL_KEY);
        mPaGoal.setOnPreferenceClickListener(this);
        mStGoal = findPreference(ST_GOAL_KEY);
        mStGoal.setOnPreferenceClickListener(this);

        mCheckBoxPreference = (CheckBoxPreference) findPreference(NOTIFICATION_CB_KEY);
        mCheckBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.e("Settings Fragment", "CB: " + (boolean) newValue);
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getSettingsInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.releaseView();
        System.out.println("SettingsFragment onDestroy called");

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case SLEEPING_HOURS_KEY:
                Calendar now = Calendar.getInstance();
                TimePickerDialog dialog = TimePickerDialog.newInstance(this,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        false
                );
                dialog.show(getActivity().getFragmentManager(), TIMERANGEPICKER_TAG);

                return true;
            case PA_GOAL_KEY:
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.set_pa_goal_dialog_title)
                        .items(R.array.pa_goal_list)
                        .theme(Theme.LIGHT)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                int paGoalMin = Integer.parseInt((String) text);
                                // convert the selected goal in minutes to seconds...
                                mPresenter.setPaGoal(paGoalMin * 60);
                                mPaGoal.setSummary(String.valueOf(paGoalMin));
                            }
                        })
                        .show();
                return true;
            case ST_GOAL_KEY:
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.set_mci_dialog_title)
                        .items(R.array.mci_list)
                        .theme(Theme.LIGHT)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                int mciMin = Integer.parseInt((String) text);
                                // convert the selected goal in minutes to seconds...
                                mPresenter.setStGoal(mciMin * 60);
                                mStGoal.setSummary(String.valueOf(mciMin));
                            }
                        }).show();
                return true;
        }
        return false;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute,
                          int hourOfDayEnd, int minuteEnd) {
        mPresenter.onSleepingHoursSet(hourOfDay, minute, hourOfDayEnd, minuteEnd);
        // call the presenter's method again to refresh the UI with the changed data
        mPresenter.getSettingsInfo();

        rescheduleSleepingHoursTask();
    }

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication()).buildActiveMinutesComp().inject(this);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSettingsData(String sleepingHours, String paGoal, String stGoal) {
        System.out.println("SetSettingsData called!");
        mSleepingHours.setSummary(sleepingHours);
        mPaGoal.setSummary(paGoal);
        mStGoal.setSummary(stGoal);
    }

    private void rescheduleSleepingHoursTask() {
        User user = mIAuthDataManager.getLoggedInUser();
        System.out.println("rescheduleSleepingHoursTask called! User: " + user.toString());
        Date startSleepingHours = user.getStartSleepingHours();
        Date stopSleepingHours = user.getStopSleepingHours();

        CheckUserSHJob checkUserSHJob = new CheckUserSHJob(getActivity());

        int jobId = checkUserSHJob.scheduleSleepingHoursCheckJob(startSleepingHours, stopSleepingHours);
        getPreferenceScreen().getSharedPreferences().edit()
                .putInt(ActiveMinutesActivity.CHECK_SH_JOB_ID_KEY, jobId).apply();
    }
}
