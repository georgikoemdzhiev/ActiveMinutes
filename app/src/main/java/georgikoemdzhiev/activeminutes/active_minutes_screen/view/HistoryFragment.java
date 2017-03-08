package georgikoemdzhiev.activeminutes.active_minutes_screen.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.model.HistoryAdapter;
import georgikoemdzhiev.activeminutes.active_minutes_screen.presenter.IHistoryPresenter;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.data_layer.db.Activity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements IHistoryView {
    @BindView(R.id.activity_list)
    RecyclerView mActivityList;
    @BindView(R.id.daily_button)
    Button mDailyBtn;
    @BindView(R.id.weekly_button)
    Button mWeeklyBtn;

    @Inject
    IHistoryPresenter mPresenter;

    private LinearLayoutManager mLayoutManager;
    private HistoryAdapter mAdapter;

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
        mLayoutManager = new LinearLayoutManager(getContext());
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

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication()).buildActiveMinutesComp().inject(this);
    }

    @Override
    public void setDailyActivityData(List<Activity> activityData) {
        // set up the list adapter with the requested activity data
        mAdapter = new HistoryAdapter(getContext(), activityData);
        mActivityList.setAdapter(mAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
