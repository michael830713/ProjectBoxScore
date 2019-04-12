package com.mike.projectboxscore.mainConsole;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubContract;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubDialogPresenter;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubstituteDialog;

import java.util.ArrayList;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsoleFragment extends Fragment implements MainConsoleViewContract.View {

    private static final String TAG = "MainConsoleFragment";

    MainConsoleViewContract.Presenter mPresenter;
    SubContract.Presenter mSubPresenter;
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
    private Button mSubstitute;
    private Button mFoul;
    private Button mBlock;
    private Button mSettings;
    private ImageView mBackButton;

    private static final String TWO_POINTS_MADE = "2 Pts Made";
    private static final String THREE_POINTS_MADE = "3 Pts Made";
    private static final String TWO_POINTS_MISS = "2 Pts Miss";
    private static final String THREE_POINTS_MISS = "3 Pts Miss";
    private static final String FREE_THROW_MADE = "Free Throw Made";
    private static final String FREE_THROW_MISS = "Free Throw Miss";
    private static final String OFFENSIVE_REBOUND = "O Rebound";
    private static final String DEFENSIVE_REBOUND = "D Rebound";
    private static final String ASSIST = "Assist";
    private static final String TURN_OVER = "Turn Over";
    private static final String FOUL = "Foul";
    private static final String STEAL = "Steal";
    private static final String BLOCK = "Block";

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
        mMainLogAdapter = new MainLogAdapter(mPresenter);
        mPresenter.setOpponent("Pistons");
        Log.d(TAG, "onCreate: ");

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

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);

        mLogRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        m2Pts = root.findViewById(R.id.button2Pts);
        m3Pts = root.findViewById(R.id.button3Pts);
        mAssist = root.findViewById(R.id.buttonAssist);
        mBlock = root.findViewById(R.id.buttonBlock);
        mTurnOver = root.findViewById(R.id.buttonTurnOver);
        mFreeThrows = root.findViewById(R.id.buttonFreeThrow);
        mFoul = root.findViewById(R.id.buttonFoul);
        mSubstitute = root.findViewById(R.id.buttonSub);
        mDreb = root.findViewById(R.id.buttonDefensiveRebound);
        mOreb = root.findViewById(R.id.buttonOffensiveRebound);
        mSteal = root.findViewById(R.id.buttonSteal);
        mTextViewAwayScore = root.findViewById(R.id.textViewAwayScore);
        mTextViewHomeScore = root.findViewById(R.id.textViewHomeScore);
        mBackButton = root.findViewById(R.id.imageViewReturnStep);
        mSettings = root.findViewById(R.id.buttonSetting);
//        mJoystickView = root.findViewById(R.id.joy_stick_controller);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mPresenter.setupNewPlayer("Mike", 23, getString(R.string.gaurd));
//        mPresenter.setupNewPlayer("Jordan", 24, getString(R.string.gaurd));
//        mPresenter.setupNewPlayer("Chris", 25, getString(R.string.forward));
//        mPresenter.setupNewPlayer("Paul", 26, getString(R.string.forward));
//        mPresenter.setupNewPlayer("Gasol", 27, getString(R.string.center));
//        mPresenter.setupNewPlayer("Opponent", -1, "O", true);
//        mPresenter.setupNewPlayer("Mikey", 33, getString(R.string.gaurd));
//        mPresenter.setupNewPlayer("Jordany", 34, getString(R.string.gaurd));
//        mPresenter.setupNewPlayer("Chrissy", 35, getString(R.string.forward));
//        mPresenter.setupNewPlayer("Paully", 66, getString(R.string.forward));
//        mPresenter.setupNewPlayer("Gasolly", 77, getString(R.string.center));
//
//        mPresenter.setPlayerOnCourt(33);
//        mPresenter.setPlayerOnCourt(24);
//        mPresenter.setPlayerOnCourt(66);
//        mPresenter.setPlayerOnCourt(35);
//        mPresenter.setPlayerOnCourt(27);

        Log.d(TAG, "onViewCreated players: "+mPresenter.getPlayers());


        mPresenter.setOnCourtPlayers();


        mOnCourtPlayerAdapter.setPlayers(mPresenter.getPlayers());

        mPresenter.getPlayers();
        m2Pts.setOnClickListener(awesomeOnClickListener);
        m3Pts.setOnClickListener(awesomeOnClickListener);
        mAssist.setOnClickListener(awesomeOnClickListener);
        mBlock.setOnClickListener(awesomeOnClickListener);
        mTurnOver.setOnClickListener(awesomeOnClickListener);
        mFreeThrows.setOnClickListener(awesomeOnClickListener);
        mFoul.setOnClickListener(awesomeOnClickListener);
        mSubstitute.setOnClickListener(awesomeOnClickListener);
        mDreb.setOnClickListener(awesomeOnClickListener);
        mOreb.setOnClickListener(awesomeOnClickListener);
        mSteal.setOnClickListener(awesomeOnClickListener);
        mBackButton.setOnClickListener(awesomeOnClickListener);
        mSettings.setOnClickListener(awesomeOnClickListener);

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
            PlayerStats selectedPlayer = mPresenter.getPlayers().get(rowIndex);
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
                    mPresenter.updateLog(OFFENSIVE_REBOUND);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.offensive_rebound));
                    break;

                case R.id.buttonDefensiveRebound:
                    mPresenter.playerDefensiveRebounded(1);
                    mPresenter.updateLog(DEFENSIVE_REBOUND);

//                    selectedPlayer.setDefensiveRebounds(selectedPlayer.getDefensiveRebounds() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.defensive_rebound));
                    break;

                case R.id.buttonAssist:
                    mPresenter.playerAssisted(1);
                    mPresenter.updateLog(ASSIST);
                    Log.d(TAG, "assist: " + mPresenter.getSelectedPlayer().getAssists());
//                    selectedPlayer.setAssists(selectedPlayer.getAssists() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.assist));
                    break;

                case R.id.buttonTurnOver:
                    mPresenter.playerTurnedOver(1);
                    mPresenter.updateLog(TURN_OVER);

//                    selectedPlayer.setTurnOvers(selectedPlayer.getTurnOvers() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.turn_over));
                    break;

                case R.id.buttonFoul:
                    mPresenter.playerFouled(1);
                    mPresenter.updateLog(FOUL);

//                    selectedPlayer.setFouls(selectedPlayer.getFouls() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.foul_made));
                    break;

                case R.id.buttonSteal:
                    mPresenter.playerstealed(1);
                    mPresenter.updateLog(STEAL);

//                    selectedPlayer.setSteals(selectedPlayer.getSteals() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.steal));
                    break;

                case R.id.buttonBlock:
                    mPresenter.playerBlocked(1);
                    mPresenter.updateLog(BLOCK);

//                    selectedPlayer.setBlocks(selectedPlayer.getBlocks() + 1);
//                    mMainLogAdapter.setLog(selectedPlayer, getString(R.string.block));
                    break;

                case R.id.buttonSub:
                    mPresenter.showSubstituteDialog();
                    break;

                case R.id.buttonSetting:
                    mPresenter.setPlayerOffCourt(33);
                    mPresenter.setPlayerOnCourt(26);

                    mPresenter.setOnCourtPlayers();

                    mOnCourtPlayerAdapter.setPlayers(mPresenter.getPlayers());
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

            PlayerStats player = mMainLogAdapter.getmPlayers().get(0);
            mPresenter.setSelectedPlayer(player);
            String currentAction = mMainLogAdapter.getmActions().get(0);
            Log.d(TAG, "returnLastStepUi: " + currentAction);
            switch (currentAction) {
                case TWO_POINTS_MADE:
                    mPresenter.updateScoreboardReturn(2);
                    mPresenter.updatePlayerScores(-2);
                    mPresenter.removeLog();
                    break;
                case TWO_POINTS_MISS:
                    mPresenter.updatePlayerMisses(-2);
                    mPresenter.removeLog();
                    break;

                case THREE_POINTS_MADE:
                    mPresenter.updateScoreboardReturn(3);
                    mPresenter.updatePlayerScores(-3);
                    mPresenter.removeLog();
                    break;

                case THREE_POINTS_MISS:
                    mPresenter.updatePlayerMisses(-3);
                    mPresenter.removeLog();
                    break;

                case FREE_THROW_MADE:
                    mPresenter.updateScoreboardReturn(1);
                    mPresenter.updatePlayerScores(-1);
                    mPresenter.removeLog();
                    Log.d(TAG, "current FT: " + mPresenter.getSelectedPlayer().getFreeThrowMade() + "-" + mPresenter.getSelectedPlayer().getFreeThrowTaken());

                    break;
                case FREE_THROW_MISS:
                    mPresenter.updatePlayerMisses(-1);
                    mPresenter.removeLog();
                    Log.d(TAG, "current FT: " + mPresenter.getSelectedPlayer().getFreeThrowMade() + "-" + mPresenter.getSelectedPlayer().getFreeThrowTaken());
                    break;

                case OFFENSIVE_REBOUND:
                    mPresenter.playerOffensiveRebounded(-1);
                    Log.d(TAG, "playerOffensiveRebounded: " + mPresenter.getSelectedPlayer().getOffensiveRebounds());
                    mPresenter.removeLog();

                    break;
                case DEFENSIVE_REBOUND:
                    mPresenter.playerDefensiveRebounded(-1);
                    mPresenter.removeLog();
                    break;

                case ASSIST:
                    mPresenter.playerAssisted(-1);
                    Log.d(TAG, "assist after remove: " + mPresenter.getSelectedPlayer().getAssists());
                    mPresenter.removeLog();
                    break;

                case TURN_OVER:
                    mPresenter.playerTurnedOver(-1);
                    mPresenter.removeLog();
                    break;

                case FOUL:
                    mPresenter.playerFouled(-1);
                    mPresenter.removeLog();
                    break;

                case STEAL:
                    mPresenter.playerstealed(-1);
                    mPresenter.removeLog();
                    break;

                case BLOCK:
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
                    Log.d(TAG, "current FT: " + mPresenter.getSelectedPlayer().getFreeThrowMade() + "-" + mPresenter.getSelectedPlayer().getFreeThrowTaken());
                    dialog.dismiss();
                })
                .setNegativeButton("Miss", (dialog, id) -> {

                    //player missed shot
                    mPresenter.updatePlayerMisses(addPoints);
                    mPresenter.updateLog(addPoints, false);
                    Log.d(TAG, "current FT: " + mPresenter.getSelectedPlayer().getFreeThrowMade() + "-" + mPresenter.getSelectedPlayer().getFreeThrowTaken());
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showSubstituteDialogUi() {

        SubstituteDialog substituteDialog = new SubstituteDialog();
        mSubPresenter = new SubDialogPresenter(substituteDialog);
        mPresenter.setOnBenchPlayers();
        Log.d(TAG, "setBenchPlayer: " + mPresenter.getOnBenchPlayers());
        ((SubDialogPresenter) mSubPresenter).setToBeReplacedPlayer(mPresenter.getSelectedPlayer());
        ((SubDialogPresenter) mSubPresenter).setBenchPlayer(mPresenter.getOnBenchPlayers());
        substituteDialog.setPresenter(mSubPresenter);

        FragmentManager fm = getFragmentManager();
        substituteDialog.show(fm, "wierd");
        fm.executePendingTransactions();

        substituteDialog.getDialog().setOnDismissListener(dialog -> {
            mPresenter.setOnCourtPlayers();
            mOnCourtPlayerAdapter.setPlayers(mPresenter.getPlayers());
        });

    }

    @Override
    public void updateLogUi(int addScore, boolean isShotMade) {
        if (isShotMade) {
//            mMainLogAdapter.setColor(1);
            if (addScore == 2) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), TWO_POINTS_MADE);
            } else if (addScore == 3) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), THREE_POINTS_MADE);
            } else {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), FREE_THROW_MADE);
            }

        } else {
//            mMainLogAdapter.setColor(2);
            if (addScore == 2) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), TWO_POINTS_MISS);
            } else if (addScore == 3) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), THREE_POINTS_MISS);
            } else {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), FREE_THROW_MISS);
            }

        }

        mLogRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void updateLogUi(String action) {
//        mMainLogAdapter.setColor(0);
        mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);
    }

    @Override
    public void removeLogUi() {
        ArrayList<PlayerStats> players = mMainLogAdapter.getmPlayers();
        players.remove(0);
        mMainLogAdapter.setmPlayers(players);
        ArrayList<String> actions = mMainLogAdapter.getmActions();
        actions.remove(0);
        mMainLogAdapter.setmActionsRemoved(actions);
    }

    @Override
    public void updateScoreboardUi(int addScore) {
        if (mPresenter.getSelectedPlayer().getBackNumber() != -1) {
            mTextViewAwayScore.setText(Integer.toString(updateAddAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateAddHomeScore(addScore)));

        }
    }

    @Override
    public void updateScoreboardReturnUi(int addScore) {
        Log.d(TAG, "currentACtiomn: " + mMainLogAdapter.getmActions().get(0));
        if (mMainLogAdapter.getmPlayers().get(0).getBackNumber() != -1) {
            mTextViewAwayScore.setText(Integer.toString(updateMinusAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateMinusHomeScore(addScore)));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
//
//        mPresenter.setOnCourtPlayers();
//
//        mOnCourtPlayerAdapter.setPlayers(mPresenter.getPlayers());
//        Log.d(TAG, "onResume: " + mPresenter.getPlayers());
        super.onResume();
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
