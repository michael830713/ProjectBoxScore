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

import com.mike.projectboxscore.Data.PlayerOnCourtStats;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsoleFragment extends Fragment implements MainConsoleViewContract.View {

    private static final String TAG = "MainConsoleFragment";

    MainConsoleViewContract.Presenter mPresenter;
    private OnCourtPlayerAdapter mOnCourtPlayerAdapter;
    private MainLogAdapter mMainLogAdapter;
    RecyclerView mPlayerRecyclerView;
    RecyclerView mLogRecyclerView;
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
    private ArrayList<PlayerOnCourtStats> mPlayers;

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
        mPlayers = new ArrayList<PlayerOnCourtStats>();
        mMainLogAdapter = new MainLogAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.console_fragment, container, false);

        mPlayerRecyclerView = root.findViewById(R.id.recyclerview_onCourt_players);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(playerLayoutManager);
        mPlayerRecyclerView.setAdapter(mOnCourtPlayerAdapter);

        mLogRecyclerView = root.findViewById(R.id.recyclerView_log);
        LinearLayoutManager logLayoutManager = new LinearLayoutManager(getContext());
        mLogRecyclerView.setLayoutManager(logLayoutManager);
        mLogRecyclerView.setAdapter(mMainLogAdapter);

        m2Pts = root.findViewById(R.id.button2Pts);
        m3Pts = root.findViewById(R.id.button3Pts);
        mAssist = root.findViewById(R.id.buttonAssist);
        mBlock = root.findViewById(R.id.buttonBlock);
        mTurnOver = root.findViewById(R.id.buttonTurnOver);
        mFreeThrows = root.findViewById(R.id.buttonFreeThrow);
        mFoul = root.findViewById(R.id.buttonFoul);
        mSubstitude = root.findViewById(R.id.buttonSub);
        mDreb = root.findViewById(R.id.buttonDefensiveRebound);
        mOreb = root.findViewById(R.id.buttonOffensiveRebound);
        mSteal = root.findViewById(R.id.buttonSteal);
//        mJoystickView = root.findViewById(R.id.joy_stick_controller);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPlayers.add(new PlayerOnCourtStats("Mike", 23, getString(R.string.gaurd)));
        mPlayers.add(new PlayerOnCourtStats("Jordan", 24, getString(R.string.gaurd)));
        mPlayers.add(new PlayerOnCourtStats("Chris", 25, getString(R.string.forward)));
        mPlayers.add(new PlayerOnCourtStats("Paul", 26, getString(R.string.forward)));
        mPlayers.add(new PlayerOnCourtStats("Gasol", 27, getString(R.string.center)));
        mOnCourtPlayerAdapter.setPlayers(mPlayers);

        m2Pts.setOnClickListener(awesomeOnClickListener);
        m3Pts.setOnClickListener(awesomeOnClickListener);
        mAssist.setOnClickListener(awesomeOnClickListener);
        mBlock.setOnClickListener(awesomeOnClickListener);
        mTurnOver.setOnClickListener(awesomeOnClickListener);
        mFreeThrows.setOnClickListener(awesomeOnClickListener);
        mFoul.setOnClickListener(awesomeOnClickListener);
        mSubstitude.setOnClickListener(awesomeOnClickListener);
        mDreb.setOnClickListener(awesomeOnClickListener);
        mOreb.setOnClickListener(awesomeOnClickListener);
        mSteal.setOnClickListener(awesomeOnClickListener);

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
                    int added2points = playerScored(2);
                    mMainLogAdapter.setLog(mPlayers.get(mOnCourtPlayerAdapter.getRow_index()), getString(R.string.two_points_made));
                    mLogRecyclerView.smoothScrollToPosition(0);
                    Log.d(TAG, mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getBackNumber() + "scored: " + added2points);
                    break;
                case R.id.button3Pts:
                    int added3points = playerScored(3);
                    Log.d(TAG, mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getBackNumber() + "scored: " + added3points);
                    break;
                case R.id.buttonFreeThrow:
                    int added1points = playerScored(1);
                    Log.d(TAG, mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getBackNumber() + "scored: " + added1points);
                    break;
                case R.id.buttonOffensiveRebound:
                    int currentOffensiveRebounds = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getOffensiveRebounds();
                    mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setOffensiveRebounds(currentOffensiveRebounds + 1);
                    break;
                case R.id.buttonDefensiveRebound:
                    int currentDefensiveRebounds = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getDefensiveRebounds();
                    mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setDefensiveRebounds(currentDefensiveRebounds + 1);
                    break;
                case R.id.buttonAssist:
                    int currentAssist = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getAssists();
                    mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setAssists(currentAssist + 1);
                    break;
                case R.id.buttonTurnOver:
                    int currentTurnOver = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getTurnOvers();
                    mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setTurnOvers(currentTurnOver + 1);
                    break;
                case R.id.buttonFoul:
                    int currentFouls = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getFouls();
                    mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setFouls(currentFouls + 1);
                    break;
                case R.id.buttonSub:
                    break;
                case R.id.buttonSteal:
                    int currentSteals = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getSteals();
                    mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setSteals(currentSteals + 1);
                    break;
                case R.id.buttonBlock:
                    int currentBlocks = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getBlocks();
                    mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setBlocks(currentBlocks + 1);
                    break;
            }
        }
    };

    private int playerScored(int point) {
        int currentPoints = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getPoints();
        int newPoint = currentPoints + point;
        mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setPoints(newPoint);
        return newPoint;
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
