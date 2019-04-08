package com.mike.projectboxscore.mainConsole;

import android.app.AlertDialog;
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
import android.widget.ImageView;
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
    private ImageView mBackButton;

    public MainConsoleFragment() {
        // Requires empty public constructor
    }

    public static MainConsoleFragment newInstance() {
        return new MainConsoleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainConsolePresenter(this);
        mOnCourtPlayerAdapter = new OnCourtPlayerAdapter(mPresenter);
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
        mBackButton = root.findViewById(R.id.imageViewReturnStep);
//        mJoystickView = root.findViewById(R.id.joy_stick_controller);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.setupNewPlayer("Mike", 23, getString(R.string.gaurd));
        mPresenter.setupNewPlayer("Jordan", 24, getString(R.string.gaurd));
        mPresenter.setupNewPlayer("Chris", 25, getString(R.string.forward));
        mPresenter.setupNewPlayer("Paul", 26, getString(R.string.forward));
        mPresenter.setupNewPlayer("Gasol", 27, getString(R.string.center));
        mPresenter.setupNewPlayer("Opponent", -1, "O");

        mOnCourtPlayerAdapter.setPlayers(mPresenter.getPlayers());

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
        mBackButton.setOnClickListener(awesomeOnClickListener);

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
            int rowIndex = mOnCourtPlayerAdapter.getRow_index();
            PlayerOnCourtStats selectedPlayer = mPresenter.getPlayers().get(rowIndex);
            mPresenter.setSelectedPlayer(selectedPlayer);

            switch (v.getId()) {
                case R.id.button2Pts:
                    mPresenter.showMadeOrMissDialog(rowIndex, 2);
                    break;

                case R.id.button3Pts:
                    mPresenter.showMadeOrMissDialog(rowIndex, 3);
                    Log.d(TAG, "button3Pts: FG" + selectedPlayer.getThreePointShotMade()
                            + "-"
                            + selectedPlayer.getThreePointShotTaken());
                    break;

                case R.id.buttonFreeThrow:
                    mPresenter.showMadeOrMissDialog(rowIndex, 1);
                    break;

                case R.id.buttonOffensiveRebound:
                    mPresenter.playerOffensiveRebounded(1);
                    mPresenter.updateLog(getString(R.string.offensive_rebound));
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.offensive_rebound));
                    break;

                case R.id.buttonDefensiveRebound:
                    mPresenter.playerDefensiveRebounded(1);
                    mPresenter.updateLog(getString(R.string.defensive_rebound));

//                    selectedPlayer.setDefensiveRebounds(selectedPlayer.getDefensiveRebounds() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.defensive_rebound));
                    break;

                case R.id.buttonAssist:
                    mPresenter.playerAssisted(1);
                    mPresenter.updateLog(getString(R.string.assist));
                    Log.d(TAG, "assist: " + mPresenter.getSelectedPlayer().getAssists());
//                    selectedPlayer.setAssists(selectedPlayer.getAssists() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.assist));
                    break;

                case R.id.buttonTurnOver:
                    mPresenter.playerTurnedOver(1);
                    mPresenter.updateLog(getString(R.string.turn_over));

//                    selectedPlayer.setTurnOvers(selectedPlayer.getTurnOvers() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.turn_over));
                    break;

                case R.id.buttonFoul:
                    mPresenter.playerFouled(1);
                    mPresenter.updateLog(getString(R.string.foul_made));

//                    selectedPlayer.setFouls(selectedPlayer.getFouls() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.foul_made));
                    break;

                case R.id.buttonSteal:
                    mPresenter.playerstealed(1);
                    mPresenter.updateLog(getString(R.string.steal));

//                    selectedPlayer.setSteals(selectedPlayer.getSteals() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.steal));
                    break;

                case R.id.buttonBlock:
                    mPresenter.playerBlocked(1);
                    mPresenter.updateLog(getString(R.string.block));

//                    selectedPlayer.setBlocks(selectedPlayer.getBlocks() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.block));
                    break;

                case R.id.buttonSub:
                    break;

                case R.id.imageViewReturnStep:

                    mPresenter.returnLastStep();

                    break;
            }
            mLogRecyclerView.smoothScrollToPosition(0);
        }
    };

    private int updateAddAwayScore(int addPoints) {
        mAwayScore = mAwayScore + addPoints;
        return mAwayScore;
    }

    private int updateAddHomeScore(int addPoints) {
        mHomeScore = mHomeScore + addPoints;
        return mHomeScore;
    }

    private int updateMinusAwayScore(int addPoints) {
        mAwayScore = mAwayScore - addPoints;
        return mAwayScore;
    }

    private int updateMinusHomeScore(int addPoints) {
        mHomeScore = mHomeScore - addPoints;
        return mHomeScore;
    }

    @Override
    public void returnLastStepUi() {
        if (mMainLogAdapter.getmPlayers().size() != 0) {

            PlayerOnCourtStats player = mMainLogAdapter.getmPlayers().get(0);
            mPresenter.setSelectedPlayer(player);
            String currentAction = mMainLogAdapter.getmActions().get(0);
            Log.d(TAG, "returnLastStepUi: " + currentAction);
            switch (currentAction) {
                case "2pts made":
                    mPresenter.updateScoreboardReturn(2);
                    mPresenter.updatePlayerScores(-2);
                    mPresenter.removeLog();
                    break;
                case "2pts miss":
                    mPresenter.updatePlayerMisses(-2);
                    mPresenter.removeLog();

                    break;
                case "3pts made":
                    mPresenter.updateScoreboardReturn(3);
                    mPresenter.updatePlayerScores(-3);
                    mPresenter.removeLog();

                    break;
                case "3pts miss":
                    mPresenter.updatePlayerMisses(-3);
                    mPresenter.removeLog();

                    break;
                case "free throw made":
                    mPresenter.updateScoreboardReturn(1);
                    mPresenter.updatePlayerScores(-1);
                    mPresenter.removeLog();

                    break;
                case "free throw miss":
                    mPresenter.removeLog();

                    break;
                case "O rebound":
                    mPresenter.playerOffensiveRebounded(-1);
                    Log.d(TAG, "playerOffensiveRebounded: " + mPresenter.getSelectedPlayer().getOffensiveRebounds());
                    mPresenter.removeLog();

                    break;
                case "D rebound":
                    mPresenter.playerDefensiveRebounded(-1);
                    mPresenter.removeLog();

                    break;
                case "assist":
                    mPresenter.playerAssisted(-1);
                    Log.d(TAG, "assist after remove: " + mPresenter.getSelectedPlayer().getAssists());

                    mPresenter.removeLog();

                    break;
                case "turn over":
                    mPresenter.playerTurnedOver(-1);
                    mPresenter.removeLog();

                    break;
                case "foul":
                    mPresenter.playerFouled(-1);
                    mPresenter.removeLog();

                    break;
                case "steal":
                    mPresenter.playerstealed(-1);
                    mPresenter.removeLog();

                    break;
                case "block":
                    mPresenter.playerBlocked(-1);
                    mPresenter.removeLog();

                    break;

            }
        }
    }

    @Override
    public void showMadeOrMissDialogUi(int addPoints) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Made or miss?")
                .setCancelable(true)
                .setPositiveButton("Made", (dialog, id) -> {

                    //player made shot
                    mPresenter.updatePlayerScores(addPoints);
                    mPresenter.updateLog(addPoints, true);
                    mPresenter.updateScoreboard(addPoints);
                    dialog.dismiss();
                })
                .setNegativeButton("Miss", (dialog, id) -> {

                    //player missed shot
                    mPresenter.updatePlayerMisses(addPoints);
                    mPresenter.updateLog(addPoints, false);
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void updateLogUi(int addScore, boolean isShotMade) {
        if (isShotMade) {
            if (addScore == 2) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), getString(R.string.two_points_made));
            } else if (addScore == 3) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), getString(R.string.three_points_made));
            } else {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), getString(R.string.free_throw_made));
            }
        } else {
            if (addScore == 2) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), getString(R.string.two_points_miss));
            } else if (addScore == 3) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), getString(R.string.three_points_miss));
            } else {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), getString(R.string.free_throw_miss));
            }
        }

        mLogRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void updateLogUi(String action) {
        mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);
    }

    @Override
    public void removeLogUi() {
        ArrayList<PlayerOnCourtStats> players = mMainLogAdapter.getmPlayers();
        players.remove(0);
        mMainLogAdapter.setmPlayers(players);
        ArrayList<String> actions = mMainLogAdapter.getmActions();
        actions.remove(0);
        mMainLogAdapter.setmActions(actions);
    }

    @Override
    public void updateScoreboardUi(int addScore) {
        if (mOnCourtPlayerAdapter.getRow_index() != 5) {
            mTextViewAwayScore.setText(Integer.toString(updateAddAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateAddHomeScore(addScore)));
        }
    }

    @Override
    public void updateScoreboardReturnUi(int addScore) {
        if (mOnCourtPlayerAdapter.getRow_index() != 5) {
            mTextViewAwayScore.setText(Integer.toString(updateMinusAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateMinusHomeScore(addScore)));
        }
    }

    @Override
    public void showSelectedPlayer() {
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
    public void setPresenter(MainConsoleViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }
}
