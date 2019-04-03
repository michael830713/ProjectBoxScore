package com.mike.projectboxscore.loginUI;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginUiFragment extends Fragment implements LoginUIViewContract.View {

    private static final String TAG = "LoginUiFragment";

    LoginUIViewContract.Presenter mPresenter;
    private Button mLoginButton;

    public LoginUiFragment() {
        // Requires empty public constructor
    }

    public static LoginUiFragment newInstance() {
        return new LoginUiFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_layout, container, false);

        mLoginButton = root.findViewById(R.id.button_google_login);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoConsoleView();
            }
        });

    }

    private void demoConsoleView() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainConsoleFragment fragment = MainConsoleFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void initView() {

    }

    @Override
    public void showFullscreenModeUi(int videoWidth, int videoHeight) {

    }

    @Override
    public void showNormalModeUi(int videoWidth, int videoHeight) {

    }

    @Override
    public void addSurfaceHolderCallback(SurfaceHolder.Callback callback) {

    }

    @Override
    public void requestLandscapeUi() {

    }

    @Override
    public void requestPortraitUi() {

    }

    @Override
    public void setPresenter(LoginUIViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }
}
