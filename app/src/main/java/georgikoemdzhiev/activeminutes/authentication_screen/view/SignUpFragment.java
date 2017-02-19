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
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.authentication_screen.presenter.ISignUpPresenter;
import georgikoemdzhiev.activeminutes.today_screen.view.ActiveMinutesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements ISignUpView {
    @BindView(R.id.activeMinutesTextView)
    TextView mActiveMinutesTextView;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password1)
    EditText mPassword;
    @BindView(R.id.passwordConf)
    EditText mPasswordConfirm;

    @Inject
    ISignUpPresenter mPresenter;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment signUpFragment = new SignUpFragment();
        return signUpFragment;
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        // Provide this layout for injecting views to this fragment
        ButterKnife.bind(this, view);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SignPainter-HouseScript.ttf");
        mActiveMinutesTextView.setTypeface(face);
        return view;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.releaseView();
        ((ActiveMinutesApplication) getActivity()
                .getApplication())
                .releaseAuthComp();
    }

    @OnClick(R.id.signupBtn)
    public void onSignUpClicked() {
        mPresenter.signUp(mUsername.getText().toString()
                , mPassword.getText().toString()
                , mPasswordConfirm.getText().toString());
    }

    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getActivity()
                .getApplication()).buildAuthComp().inject(this);
    }
}
