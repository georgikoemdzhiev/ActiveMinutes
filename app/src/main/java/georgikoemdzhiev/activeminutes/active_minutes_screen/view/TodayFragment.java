package georgikoemdzhiev.activeminutes.active_minutes_screen.view;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.ITodayPresenter;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment implements ITodayView {
    @BindView(R.id.activeTime)
    TextView mActivityTime;
    @BindView(R.id.activeTimeProgressBar)
    ProgressBar mActiveTimeProgressBar;
    @BindView(R.id.paGoal)
    TextView mPaGoal;
    @BindView(R.id.longestInacInterval)
    TextView mLongestInacInterval;
    @BindView(R.id.averageInacInterval)
    TextView mAverageInacInterval;
    @BindView(R.id.maxContInactTarget)
    TextView mMaxContInactTarget;
    @BindView(R.id.timesTargetExceeded)
    TextView mTimesTargetExceeded;

    @Inject
    ITodayPresenter mPresenter;

    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TodayFragment.
     */
    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satisfyDependencies();
        Log.e("TodayFragment", "OnCreate called");
        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getActivityInformation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.releaseView();
    }

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication()).buildActiveMinutesComp().inject(this);
    }

    @Override
    public void setData(int paGoal,
                        String maxContInacTarget,
                        String timesTargetExceeded,
                        int activeTime,
                        String longestInacInter,
                        String averageIacInter) {
        // seconds to minutes convection
        mActivityTime.setText(String.valueOf(activeTime / 60));
        // seconds to minutes convection
        mPaGoal.setText(String.valueOf(paGoal / 60));
        mActiveTimeProgressBar.setMax(paGoal);
        mActiveTimeProgressBar.setProgress(activeTime);
        mLongestInacInterval.setText(longestInacInter);
        mAverageInacInterval.setText(averageIacInter);
        mMaxContInactTarget.setText(maxContInacTarget);
        mTimesTargetExceeded.setText(timesTargetExceeded);

        System.out.println("TodayFragment setData method. User Pa goal: " + paGoal);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
