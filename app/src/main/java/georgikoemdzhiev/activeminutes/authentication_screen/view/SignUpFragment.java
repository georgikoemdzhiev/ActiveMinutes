package georgikoemdzhiev.activeminutes.authentication_screen.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import georgikoemdzhiev.activeminutes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment signUpFragment = new SignUpFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", someInt);
//        args.putString("someString", someString);
//        // Put any other arguments
//        loginFragment.setArguments(args);
        return signUpFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

}
