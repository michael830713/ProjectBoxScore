package com.mike.projectboxscore.mainConsole;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.IOnBackPressed;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.boxScore.BoxScorePresenter;
import com.mike.projectboxscore.boxScore.BoxSoreFragment;
import com.mike.projectboxscore.mainConsole.MadeOrMissDialog.MadeOrMissCallback;
import com.mike.projectboxscore.mainConsole.MadeOrMissDialog.MadeOrMissDialog;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubContract;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubDialogPresenter;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubstituteDialog;

import java.util.ArrayList;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsoleFragment extends Fragment implements MainConsoleViewContract.View, IOnBackPressed {

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
    private ImageView m2Pts;
    private ImageView m3Pts;
    private ImageView mFreeThrows;
    private ImageView mDreb;
    private ImageView mOreb;
    private ImageView mSteal;
    private ImageView mAssist;
    private ImageView mTurnOver;
    private ImageView mSubstitute;
    private ImageView mFoul;
    private ImageView mBlock;
    private ImageView mSettings;
    private ImageView mBackButton;
    private ImageView mBoxScore;

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
    public void setPresenter(MainConsoleViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOnCourtPlayerAdapter = new OnCourtPlayerAdapter(mPresenter,getActivity());
        mMainLogAdapter = new MainLogAdapter(mPresenter);
//        mPresenter.setOpponent("Pistons");
        Log.d(TAG, "onCreate: ");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_console, container, false);

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
        mSettings = root.findViewById(R.id.buttonExit);
        mBoxScore = root.findViewById(R.id.buttonBoxScore);
//        mJoystickView = root.findViewById(R.id.joy_stick_controller);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated players: " + mPresenter.getPlayers());

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
        mBoxScore.setOnClickListener(awesomeOnClickListener);

        mTextViewAwayScore.setText(String.valueOf(mAwayScore));
        mTextViewHomeScore.setText(String.valueOf(mHomeScore));

    }

    private View.OnClickListener awesomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PlayerStats selectedPlayer = mOnCourtPlayerAdapter.getCurrentPlayer();
            mPresenter.setSelectedPlayer(selectedPlayer);

            switch (v.getId()) {
                case R.id.button2Pts:
                    mPresenter.showMadeOrMissDialog(2);
                    break;

                case R.id.button3Pts:
                    mPresenter.showMadeOrMissDialog(3);
                    Log.d(TAG, "button3Pts: FG" + selectedPlayer.getThreePointShotMade()
                            + "-"
                            + selectedPlayer.getThreePointShotTaken());
                    break;

                case R.id.buttonFreeThrow:
                    mPresenter.showMadeOrMissDialog(1);
                    break;

                case R.id.buttonOffensiveRebound:
                    mPresenter.playerOffensiveRebounded(1);
                    mPresenter.updateLog(OFFENSIVE_REBOUND);
                    break;

                case R.id.buttonDefensiveRebound:
                    mPresenter.playerDefensiveRebounded(1);
                    mPresenter.updateLog(DEFENSIVE_REBOUND);

                    break;

                case R.id.buttonAssist:
                    mPresenter.playerAssisted(1);
                    mPresenter.updateLog(ASSIST);
                    Log.d(TAG, "assist: " + mPresenter.getSelectedPlayer().getAssists());
                    break;

                case R.id.buttonTurnOver:
                    mPresenter.playerTurnedOver(1);
                    mPresenter.updateLog(TURN_OVER);

                    break;

                case R.id.buttonFoul:
                    mPresenter.playerFouled(1);
                    mPresenter.updateLog(FOUL);

                    break;

                case R.id.buttonSteal:
                    mPresenter.playerstealed(1);
                    mPresenter.updateLog(STEAL);

                    break;

                case R.id.buttonBlock:
                    mPresenter.playerBlocked(1);
                    mPresenter.updateLog(BLOCK);

                    break;

                case R.id.buttonSub:
                    mPresenter.showSubstituteDialog();
                    break;

                case R.id.buttonExit:
                    mPresenter.showConfirmExitDialog();

                    break;

                case R.id.imageViewReturnStep:
                    mPresenter.returnLastStep();
                    break;

                case R.id.buttonBoxScore:
                    mPresenter.openBoxScore();
                    break;
            }
            mLogRecyclerView.smoothScrollToPosition(0);
        }
    };

    private int updateAddAwayScore(int addPoints) {
        mAwayScore = mAwayScore + addPoints;
        mPresenter.getGame().setmMyScore(mAwayScore);
        return mAwayScore;
    }

    private int updateAddHomeScore(int addPoints) {
        mHomeScore = mHomeScore + addPoints;
        mPresenter.getGame().setmOpponentScore(mHomeScore);
        return mHomeScore;
    }

    private int updateMinusAwayScore(int addPoints) {
        mAwayScore = mAwayScore - addPoints;
        mPresenter.getGame().setmMyScore(mAwayScore);
        return mAwayScore;
    }

    private int updateMinusHomeScore(int addPoints) {
        mHomeScore = mHomeScore - addPoints;
        mPresenter.getGame().setmOpponentScore(mHomeScore);
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

        MadeOrMissDialog madeOrMissDialog = new MadeOrMissDialog();
        madeOrMissDialog.setMadeOrMissCallback(new MadeOrMissCallback() {
            @Override
            public void madeOrMissCallBack(String madeOrMiss) {
                if (madeOrMiss.equals("made")) {
                    mPresenter.updatePlayerScores(addPoints);
                    mPresenter.updateLog(addPoints, true);
                    mPresenter.updateScoreboard(addPoints);
                    Log.d(TAG, "current FT: " + mPresenter.getSelectedPlayer().getFreeThrowMade() + "-" + mPresenter.getSelectedPlayer().getFreeThrowTaken());
                    madeOrMissDialog.dismiss();
                } else {
                    mPresenter.updatePlayerMisses(addPoints);
                    mPresenter.updateLog(addPoints, false);
                    Log.d(TAG, "current FT: " + mPresenter.getSelectedPlayer().getFreeThrowMade() + "-" + mPresenter.getSelectedPlayer().getFreeThrowTaken());
                    madeOrMissDialog.dismiss();
                }
            }
        });
        FragmentManager fm = getFragmentManager();
        madeOrMissDialog.show(fm, "wierd");

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

        fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
                Log.d(TAG, "onFragmentViewDestroyed: ");
                mPresenter.setOnCourtPlayers();
                mOnCourtPlayerAdapter.setPlayers(mPresenter.getPlayers());
                //do sth
                fm.unregisterFragmentLifecycleCallbacks(this);
            }
        }, false);

    }

    @Override
    public void showConfirmExitDialogUi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure to end game?")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, id) -> {
                    mPresenter.openExitBoxScore();
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void updateLogUi(int addScore, boolean isShotMade) {
        if (isShotMade) {
            if (addScore == 2) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), TWO_POINTS_MADE);
            } else if (addScore == 3) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), THREE_POINTS_MADE);
            } else {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), FREE_THROW_MADE);
            }

        } else {
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
    public int getAwayScore() {
        return mAwayScore;
    }

    @Override
    public int getHomeScore() {
        return mHomeScore;
    }

    @Override
    public void showNormalModeUi(int videoWidth, int videoHeight) {

    }

    @Override
    public void openBoxScoreUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BoxSoreFragment fragment = BoxSoreFragment.newInstance();
        BoxScorePresenter boxScorePresenter;
        boxScorePresenter = new BoxScorePresenter(fragment, mPresenter.getGame(), false);
        Log.d(TAG, "openBoxScoreUi player size: " + mPresenter.getGame().getmPlayerStats().size());
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack("BoxScore");
        fragmentTransaction.commit();
    }

    @Override
    public void openExitBoxScoreUi() {
        mPresenter.addNewGame();
        mPresenter.updateFirebaseData();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BoxSoreFragment fragment = BoxSoreFragment.newInstance();
        BoxScorePresenter boxScorePresenter;
        boxScorePresenter = new BoxScorePresenter(fragment, mPresenter.getGame(), true);
        fragmentTransaction.replace(R.id.container, fragment, "Surface");
        fragmentTransaction.commit();
    }

    @Override
    public void addSurfaceHolderCallback(SurfaceHolder.Callback callback) {

    }

    long lastPress;

    @Override
    public boolean onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPress > 3000) {
            Toast.makeText(getActivity(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            lastPress = currentTime;
            return true;
        } else {
            return false;
        }
    }
}
