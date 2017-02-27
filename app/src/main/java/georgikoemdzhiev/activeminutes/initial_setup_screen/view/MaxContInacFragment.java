package georgikoemdzhiev.activeminutes.initial_setup_screen.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.initial_setup_screen.presenter.IMaxContInacPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaxContInacFragment extends Fragment implements IMaxContInacView {
    @Inject
    IMaxContInacPresenter mPresenter;

    public MaxContInacFragment() {
        // Required empty public constructor
    }

    public static MaxContInacFragment newInstance() {
        MaxContInacFragment fragment = new MaxContInacFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_max_cont_inac, container, false);
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

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.setMaxContInacButton)
    public void onMaxContInacButtonClicked() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.set_mci_dialog_title)
                .items(R.array.mci_list)
                .theme(Theme.LIGHT)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        int mciMin = Integer.parseInt((String) text);
                        // convert the selected goal in minutes to seconds...
                        mPresenter.setMCI(mciMin * 60);
                    }
                })
                .show();
    }


    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication())
                .getInitialSetupComponent().inject(this);
    }
}
