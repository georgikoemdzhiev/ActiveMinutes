package georgikoemdzhiev.activeminutes.initial_setup_screen.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.IPaGoalPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaGoalFragment extends Fragment implements IPaGoalView {
    @Inject
    IPaGoalPresenter mPresenter;

    public PaGoalFragment() {
        // Required empty public constructor
    }

    public static PaGoalFragment newInstance() {
        PaGoalFragment fragment = new PaGoalFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pa_goal, container, false);
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

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication())
                .getInitialSetupComponent().inject(this);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.setPaButton)
    public void onSetPaGoalClicked() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.set_pa_goal_dialog_title)
                .items(R.array.pa_goal_list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        int paGoalMin = Integer.parseInt((String) text);
                        mPresenter.setSetPa(paGoalMin);
                    }
                })
                .show();
    }
}
