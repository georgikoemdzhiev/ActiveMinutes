package georgikoemdzhiev.activeminutes.initial_setup_screen.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.ISleepingHoursPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SleepingHoursFragment extends Fragment
        implements TimePickerDialog.OnTimeSetListener, ISleepingHoursView {
    public static final String TIMERANGEPICKER_TAG = "timerangepicker";

    @Inject
    ISleepingHoursPresenter mPresenter;

    public SleepingHoursFragment() {
        // Required empty public constructor
    }


    public static SleepingHoursFragment newInstance() {
        SleepingHoursFragment fragment = new SleepingHoursFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleeping_hours, container, false);
        // Provide this layout for injecting views to this fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satisfyDependencies();
        mPresenter.setView(this);
    }


    @OnClick(R.id.openDialog)
    public void onOpenDialogClicked() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dialog = TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                false
        );
        dialog.show(getActivity().getFragmentManager(), TIMERANGEPICKER_TAG);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {

        mPresenter.onSleepingHoursSet(hourOfDay, minute, hourOfDayEnd, minuteEnd);

    }

    @Override
    public void showMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication())
                .getInitialSetupComponent().inject(this);
    }

}
