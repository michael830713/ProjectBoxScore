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
import android.widget.TextView;

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
    TextView mTextViewAwayScore;
    TextView mTextViewHomeScore;
    private JoystickView mJoystickView;
    private int mAwayScore = 0;
    private int mHomeScore = 0;
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

        //setup the player recyclerView
        mPlayerRecyclerView = root.findViewById(R.id.recyclerview_onCourt_players);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(playerLayoutManager);
        mPlayerRecyclerView.setAdapter(mOnCourtPlayerAdapter);

        //setup the middle log recyclerView
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
        mTextViewAwayScore = root.findViewById(R.id.textViewAwayScore);
        mTextViewHomeScore = root.findViewById(R.id.textViewHomeScore);
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
            PlayerOnCourtStats selectedPlayer = mPlayers.get(mOnCourtPlayerAdapter.getRow_index());
            switch (v.getId()) {

                case R.id.button2Pts:
                    int added2points = playerScored(2);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.two_points_made));
//                    mPresenter.calculateAndUpdateScore(2);
                    mTextViewAwayScore.setText(Integer.toString(updateScore(2)));

                    Log.d(TAG, selectedPlayer.getBackNumber() + "scored: " + added2points);
                    break;

                case R.id.button3Pts:
                    int added3points = playerScored(3);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.three_points_made));
                    mTextViewAwayScore.setText(Integer.toString(updateScore(3)));
//                    mPresenter.calculateAndUpdateScore(3);

                    Log.d(TAG, selectedPlayer.getBackNumber() + "scored: " + added3points);
                    break;

                case R.id.buttonFreeThrow:
                    int added1points = playerScored(1);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.free_throw_made));
                    mTextViewAwayScore.setText(Integer.toString(updateScore(1)));

//                    mPresenter.calculateAndUpdateScore(1);

                    Log.d(TAG, selectedPlayer.getBackNumber() + "scored: " + added1points);
                    break;

                case R.id.buttonOffensiveRebound:
                    selectedPlayer.setOffensiveRebounds(selectedPlayer.getOffensiveRebounds() + 1);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.offensive_rebound));
                    break;

                case R.id.buttonDefensiveRebound:
                    selectedPlayer.setDefensiveRebounds(selectedPlayer.getDefensiveRebounds() + 1);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.defensive_rebound));
                    break;

                case R.id.buttonAssist:
                    selectedPlayer.setAssists(selectedPlayer.getAssists() + 1);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.assist));
                    break;

                case R.id.buttonTurnOver:
                    selectedPlayer.setTurnOvers(selectedPlayer.getTurnOvers() + 1);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.turn_over));
                    break;

                case R.id.buttonFoul:
                    selectedPlayer.setFouls(selectedPlayer.getFouls() + 1);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.foul_made));
                    break;
                case R.id.buttonSteal:
                    selectedPlayer.setSteals(selectedPlayer.getSteals() + 1);
                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.steal));
                    break;

                case R.id.buttonBlock:
                    int currentBlocks = selectedPlayer.getBlocks();
                    selectedPlayer.setBlocks(currentBlocks + 1);
                    break;

                case R.id.buttonSub:
                    break;

            }
            mLogRecyclerView.smoothScrollToPosition(0);
        }
    };

    private int updateScore(int addPoints) {
        mAwayScore = mAwayScore + addPoints;
        return mAwayScore;
    }

    private int playerScored(int point) {
        int currentPoints = mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).getPoints();
        int newPoint = currentPoints + point;
        mPlayers.get(mOnCourtPlayerAdapter.getRow_index()).setPoints(newPoint);
        return newPoint;
    }

    @Override
    public void updateScoreUi(int addScore) {
        mTextViewAwayScore.setText(mTextViewAwayScore.getText() + Integer.toString(addScore));
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
    public void setPresenter(MainConsoleViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }
}
