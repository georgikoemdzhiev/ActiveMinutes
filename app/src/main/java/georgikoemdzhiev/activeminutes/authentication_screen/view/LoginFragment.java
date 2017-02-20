package georgikoemdzhiev.activeminutes.authentication_screen.view;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.active_minutes_screen.view.ActiveMinutesActivity;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.authentication_screen.presenter.ILoginPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ILoginView {
    @Inject
    ILoginPresenter mPresenter;
    @BindView(R.id.activeMinutesTextView)
    TextView mActiveMinutesTextView;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satisfyDependencies();
        mPresenter.setView(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Provide this layout for injecting views to this fragment
        ButterKnife.bind(this, view);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SignPainter-HouseScript.ttf");
        mActiveMinutesTextView.setTypeface(face);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.releaseView();
        ((ActiveMinutesApplication) getActivity()
                .getApplication())
                .releaseAuthComp();
    }

    @Override
    public void showDialogMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToTodayScreen() {
        startActivity(new Intent(getActivity(), ActiveMinutesActivity.class));
        getActivity().finish();
    }

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication()).buildAuthComp().inject(this);
    }

    @OnClick(R.id.loginBtn)
    public void onLoginBtnClicked() {
        mPresenter.login(mUsername.getText().toString(), mPassword.getText().toString());
    }

}
