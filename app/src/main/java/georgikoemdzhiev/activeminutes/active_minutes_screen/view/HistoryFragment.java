package georgikoemdzhiev.activeminutes.active_minutes_screen.view;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.HistoryAdapterDaily;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.HistoryAdapterWeekly;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.IHistoryPresenter;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.data_layer.db.Activity;

import static georgikoemdzhiev.activeminutes.R.string.pa_goal_reached_goal_min;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements IHistoryView {
    @BindView(R.id.activity_list)
    RecyclerView mActivityList;

    @BindView(R.id.pa_goal_label)
    TextView mPaGoalLabel;

    @BindView(R.id.daily_button)
    Button mDailyBtn;
    @BindView(R.id.weekly_button)
    Button mWeeklyBtn;

    @Inject
    IHistoryPresenter mPresenter;

    private LinearLayoutManager mLayoutManager;
    private HistoryAdapterDaily mAdapterDaily;
    private HistoryAdapterWeekly mAdapterWeekly;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HistoryFragment.
     */
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satisfyDependencies();
        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mActivityList.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mActivityList.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getDailyActivityData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.releaseView();
    }

    @OnClick(R.id.daily_button)
    public void onDailyButtonClick(Button button) {
        mPresenter.getDailyActivityData();
    }

    @OnClick(R.id.weekly_button)
    public void onWeeklyButtonClick(Button button) {
        mPresenter.getWeeklyActivityData();
    }

    @Override
    public void setDailyActivityData(List<Activity> activityData) {
        // set up the list adapter with the requested activity data
        mAdapterDaily = new HistoryAdapterDaily(getActivity(), activityData);
        mActivityList.setAdapter(mAdapterDaily);

        // set the PA to be measured in minutes
        mPaGoalLabel.setText(String.format(getString(pa_goal_reached_goal_min), "min"));
        // Set up buttons colour
        mDailyBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightGreen));
        mDailyBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.dirtyWhite));
        mWeeklyBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.dirtyWhite));
        mWeeklyBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.lightGreen));
    }

    @Override
    public void setWeeklyActivityData(List<List<Activity>> activities) {
        mAdapterWeekly = new HistoryAdapterWeekly(getActivity(), activities);
        mActivityList.setAdapter(mAdapterWeekly);
        // set the PA to be measured in hours
        mPaGoalLabel.setText(String.format(getString(pa_goal_reached_goal_min), "hour"));
        // Set up buttons colour
        mWeeklyBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightGreen));
        mWeeklyBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.dirtyWhite));
        mDailyBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.dirtyWhite));
        mDailyBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.lightGreen));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication()).buildActiveMinutesComp().inject(this);
    }
}
