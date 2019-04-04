package com.mike.projectboxscore.mainConsole;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mike.projectboxscore.R;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsoleFragment extends Fragment implements MainConsoleViewContract.View {

    private static final String TAG = "MainConsoleFragment";

    MainConsoleViewContract.Presenter mPresenter;
    private OnCourtPlayerAdapter mOnCourtPlayerAdapter;
    RecyclerView mPlayerRecyclerView;
    private JoystickView mJoystickView;
    private Button m2Pts;
    private Button m3Pts;
    private Button mFreeThrows;
    private Button mDreb;
    private Button mOreb;
    private Button mSteal;
    private Button mAssist;
    private Button mTurnOver;
    private Button mSubstitude;
    private Button mFoul;
    private Button mBlock;

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
        mPlayerRecyclerView = root.findViewById(R.id.recyclerview_onCourt_players);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(linearLayoutManager);
        mPlayerRecyclerView.setAdapter(mOnCourtPlayerAdapter);

        m2Pts = root.findViewById(R.id.button2Pts);
        m2Pts.setOnClickListener(awesomeOnClickListener);

        m3Pts = root.findViewById(R.id.button3Pts);
        m3Pts.setOnClickListener(awesomeOnClickListener);

        mAssist = root.findViewById(R.id.buttonAssist);
        mAssist.setOnClickListener(awesomeOnClickListener);

        mBlock = root.findViewById(R.id.buttonBlock);
        mBlock.setOnClickListener(awesomeOnClickListener);

        mTurnOver = root.findViewById(R.id.buttonTurnOver);
        mTurnOver.setOnClickListener(awesomeOnClickListener);

        mFreeThrows = root.findViewById(R.id.buttonFreeThrow);
        mFreeThrows.setOnClickListener(awesomeOnClickListener);

        mFoul = root.findViewById(R.id.buttonFoul);
        mFoul.setOnClickListener(awesomeOnClickListener);

        mSubstitude = root.findViewById(R.id.buttonSub);
        mSubstitude.setOnClickListener(awesomeOnClickListener);

        mDreb = root.findViewById(R.id.buttonDefensiveRebound);
        mDreb.setOnClickListener(awesomeOnClickListener);

        mOreb = root.findViewById(R.id.buttonOffensiveRebound);
        mOreb.setOnClickListener(awesomeOnClickListener);

        mSteal = root.findViewById(R.id.buttonSteal);
        mSteal.setOnClickListener(awesomeOnClickListener);

//        mJoystickView = root.findViewById(R.id.joy_stick_controller);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mJoystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
//            int mAngle;
//
//            @Override
//            public void onMove(int angle, int strength) {
//
//                Log.d(TAG, "angle: " + angle);
//                Log.d(TAG, "strength: " + strength);
//                Log.d(TAG, "mAngle: " + mAngle);
//            }
//        }, 100);

    }

    private View.OnClickListener awesomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button2Pts:
                    Log.d(TAG, "onClick: button2Pts");
                    break;
                case R.id.button3Pts:
                    break;
                case R.id.buttonFreeThrow:
                    break;
                case R.id.buttonOffensiveRebound:
                    break;
                case R.id.buttonDefensiveRebound:
                    break;
                case R.id.buttonAssist:
                    break;
                case R.id.buttonTurnOver:
                    break;
                case R.id.buttonFoul:
                    break;
                case R.id.buttonSub:
                    break;
                case R.id.buttonSteal:
                    break;
                case R.id.buttonBlock:
                    break;
            }
        }
    };

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
