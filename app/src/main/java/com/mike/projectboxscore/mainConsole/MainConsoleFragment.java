package com.mike.projectboxscore.mainConsole;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import com.mike.projectboxscore.R;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsoleFragment extends Fragment implements MainConsoleViewContract.View {

    private static final String TAG = "MainConsoleFragment";

    MainConsoleViewContract.Presenter mPresenter;
    private OnCourtPlayerAdapter mOnCourtPlayerAdapter;
    private JoystickView mJoystickView;

    public MainConsoleFragment() {
        // Requires empty public constructor
    }

    public static MainConsoleFragment newInstance() {
        return new MainConsoleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOnCourtPlayerAdapter = new OnCourtPlayerAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.console_fragment, container, false);
        RecyclerView playerRecyclerView = root.findViewById(R.id.recyclerview_onCourt_players);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        playerRecyclerView.setLayoutManager(linearLayoutManager);
        playerRecyclerView.setAdapter(mOnCourtPlayerAdapter);
        mJoystickView = root.findViewById(R.id.joy_stick_controller);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mJoystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
            int mAngle;

            @Override
            public void onMove(int angle, int strength) {

                Log.d(TAG, "angle: " + angle);
                Log.d(TAG, "strength: " + strength);
                Log.d(TAG, "mAngle: " + mAngle);
            }
        }, 100);


    }

    @Override
    public void showSelectedPlayer() {
//mOnCourtPlayerAdapter
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
    public void setPresenter(MainConsoleViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }
}
