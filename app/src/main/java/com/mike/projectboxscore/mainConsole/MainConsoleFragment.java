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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Constants;
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
    private ImageView mTutorial;
    private ImageView mBoxScore;

    private static final String TWO_POINTS_MADE = "2 Points Made";
    private static final String THREE_POINTS_MADE = "3 Points Made";
    private static final String TWO_POINTS_MISS = "2 Points Miss";
    private static final String THREE_POINTS_MISS = "3 Points Miss";
    private static final String FREE_THROW_MADE = "Free Throw Made";
    private static final String FREE_THROW_MISS = "Free Throw Miss";
    private static final String OFFENSIVE_REBOUND = "Rebound(Offensive)";
    private static final String DEFENSIVE_REBOUND = "Rebound(Defensive)";
    private static final String ASSIST = "Assist";
    private static final String TURN_OVER = "Turn Over";
    private static final String FOUL = "Foul";
    private static final String STEAL = "Steal";
    private static final String BLOCK = "Block";

    private static final int twoPoint = 2;
    private static final int threePoint = 3;
    private static final int one = 1;
    private static final int adapterTopPosition = 0;
    private static final int opponentBackNumber = -1;
    private static final int returnOneStat = -1;
    private static final int returnTwoPoints = -2;
    private static final int returnThreePoints = -3;
    private int doubleTapBackTime = 3000;

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

        mOnCourtPlayerAdapter = new OnCourtPlayerAdapter(mPresenter, getActivity());
        mMainLogAdapter = new MainLogAdapter(mPresenter);

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
        enableSwipeToDeleteAndUndo();

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
        mTutorial = root.findViewById(R.id.buttonTutorial);
        mSettings = root.findViewById(R.id.buttonExit);
        mBoxScore = root.findViewById(R.id.buttonBoxScore);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        mTutorial.setOnClickListener(awesomeOnClickListener);
        mSettings.setOnClickListener(awesomeOnClickListener);
        mBoxScore.setOnClickListener(awesomeOnClickListener);

        mTextViewAwayScore.setText(String.valueOf(mAwayScore));
        mTextViewHomeScore.setText(String.valueOf(mHomeScore));

    }

    private View.OnClickListener awesomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.image_click));

            PlayerStats selectedPlayer = mOnCourtPlayerAdapter.getCurrentPlayer();
            mPresenter.setSelectedPlayer(selectedPlayer);
            switch (v.getId()) {
                case R.id.button2Pts:
                    mPresenter.showMadeOrMissDialog(twoPoint);
                    break;

                case R.id.button3Pts:
                    mPresenter.showMadeOrMissDialog(threePoint);
                    break;

                case R.id.buttonFreeThrow:
                    mPresenter.showMadeOrMissDialog(one);
                    break;

                case R.id.buttonOffensiveRebound:
                    mPresenter.playerOffensiveRebounded(one);
                    mPresenter.updateLog(OFFENSIVE_REBOUND);
                    break;

                case R.id.buttonDefensiveRebound:
                    mPresenter.playerDefensiveRebounded(one);
                    mPresenter.updateLog(DEFENSIVE_REBOUND);

                    break;

                case R.id.buttonAssist:
                    mPresenter.playerAssisted(one);
                    mPresenter.updateLog(ASSIST);
                    break;

                case R.id.buttonTurnOver:
                    mPresenter.playerTurnedOver(one);
                    mPresenter.updateLog(TURN_OVER);

                    break;

                case R.id.buttonFoul:
                    mPresenter.playerFouled(one);
                    mPresenter.updateLog(FOUL);

                    break;

                case R.id.buttonSteal:
                    mPresenter.playerstealed(one);
                    mPresenter.updateLog(STEAL);

                    break;

                case R.id.buttonBlock:
                    mPresenter.playerBlocked(one);
                    mPresenter.updateLog(BLOCK);

                    break;

                case R.id.buttonSub:
                    if (mPresenter.getSelectedPlayer().getBackNumber() != opponentBackNumber) {
                        mPresenter.showSubstituteDialog();
                    }

                    break;

                case R.id.buttonExit:
                    mPresenter.showConfirmExitDialog();

                    break;

                case R.id.buttonTutorial:
                    mPresenter.showTutorialDialog();

                    break;

                case R.id.buttonBoxScore:
                    mPresenter.openBoxScore();
                    break;
            }
            mLogRecyclerView.smoothScrollToPosition(adapterTopPosition);
        }
    };

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                mPresenter.returnLastStep(position);
                Log.d(TAG, "onSwiped position: " + position);
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mLogRecyclerView);
    }

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
    public void returnLastStepUi(int i) {
        if (mMainLogAdapter.getmPlayers().size() != 0) {
            PlayerStats player = mMainLogAdapter.getmPlayers().get(i);
            mPresenter.setSelectedPlayer(player);
            String currentAction = mMainLogAdapter.getmActions().get(i);
            switch (currentAction) {
                case TWO_POINTS_MADE:
                    mPresenter.updateScoreboardReturn(twoPoint);
                    mPresenter.updatePlayerScores(returnTwoPoints);
                    mPresenter.removeLog(i);
                    break;
                case TWO_POINTS_MISS:
                    mPresenter.updatePlayerMisses(returnTwoPoints);
                    mPresenter.removeLog(i);
                    break;

                case THREE_POINTS_MADE:
                    mPresenter.updateScoreboardReturn(threePoint);
                    mPresenter.updatePlayerScores(returnThreePoints);
                    mPresenter.removeLog(i);
                    break;

                case THREE_POINTS_MISS:
                    mPresenter.updatePlayerMisses(returnThreePoints);
                    mPresenter.removeLog(i);
                    break;

                case FREE_THROW_MADE:
                    mPresenter.updateScoreboardReturn(one);
                    mPresenter.updatePlayerScores(returnOneStat);
                    mPresenter.removeLog(i);

                    break;
                case FREE_THROW_MISS:
                    mPresenter.updatePlayerMisses(returnOneStat);
                    mPresenter.removeLog(i);
                    break;

                case OFFENSIVE_REBOUND:
                    mPresenter.playerOffensiveRebounded(returnOneStat);
                    mPresenter.removeLog(i);

                    break;
                case DEFENSIVE_REBOUND:
                    mPresenter.playerDefensiveRebounded(returnOneStat);
                    mPresenter.removeLog(i);
                    break;

                case ASSIST:
                    mPresenter.playerAssisted(returnOneStat);
                    mPresenter.removeLog(i);
                    break;

                case TURN_OVER:
                    mPresenter.playerTurnedOver(returnOneStat);
                    mPresenter.removeLog(i);
                    break;

                case FOUL:
                    mPresenter.playerFouled(returnOneStat);
                    mPresenter.removeLog(i);
                    break;

                case STEAL:
                    mPresenter.playerstealed(returnOneStat);
                    mPresenter.removeLog(i);
                    break;

                case BLOCK:
                    mPresenter.playerBlocked(returnOneStat);
                    mPresenter.removeLog(i);
                    break;

            }
            mPresenter.setRebound();
        }
    }

    @Override
    public void showMadeOrMissDialogUi(int addPoints) {

        MadeOrMissDialog madeOrMissDialog = new MadeOrMissDialog();
        madeOrMissDialog.setMadeOrMissCallback(new MadeOrMissCallback() {
            @Override
            public void madeOrMissCallBack(String madeOrMiss) {
                if (madeOrMiss.equals(Constants.MADE)) {
                    mPresenter.updatePlayerScores(addPoints);
                    mPresenter.updateLog(addPoints, true);
                    mPresenter.updateScoreboard(addPoints);

                    madeOrMissDialog.dismiss();
                } else {
                    mPresenter.updatePlayerMisses(addPoints);
                    mPresenter.updateLog(addPoints, false);
                    madeOrMissDialog.dismiss();
                }
                mPresenter.setShotPersentage();
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
    public void showTutorialUi() {
        TutorialDialog tutorialDialog = new TutorialDialog();

        FragmentManager fm = getFragmentManager();
        tutorialDialog.show(fm, "wierd");

    }

    @Override
    public void showConfirmExitDialogUi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        builder.setMessage(getString(R.string.endGameConfirmation))
                .setCancelable(true)
                .setPositiveButton(Constants.YES, (dialog, id) -> {
                    mPresenter.openExitBoxScore();
                    dialog.dismiss();
                })
                .setNegativeButton(Constants.NO, (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void updateLogUi(int addScore, boolean isShotMade) {
        if (isShotMade) {
            if (addScore == twoPoint) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), TWO_POINTS_MADE);
            } else if (addScore == threePoint) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), THREE_POINTS_MADE);
            } else {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), FREE_THROW_MADE);
            }

        } else {
            if (addScore == twoPoint) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), TWO_POINTS_MISS);
            } else if (addScore == threePoint) {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), THREE_POINTS_MISS);
            } else {
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), FREE_THROW_MISS);
            }

        }

        mLogRecyclerView.smoothScrollToPosition(adapterTopPosition);
    }

    @Override
    public void updateLogUi(String action) {
        mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);
    }

    @Override
    public void removeLogUi(int i) {
        ArrayList<PlayerStats> players = mMainLogAdapter.getmPlayers();
        players.remove(i);
        mMainLogAdapter.setmPlayers(players);
        ArrayList<String> actions = mMainLogAdapter.getmActions();
        actions.remove(i);
        mMainLogAdapter.setmActionsRemoved(actions, i);
    }

    @Override
    public void updateScoreboardUi(int addScore) {
        if (mPresenter.getSelectedPlayer().getBackNumber() != opponentBackNumber) {
            mTextViewAwayScore.setText(Integer.toString(updateAddAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateAddHomeScore(addScore)));

        }
    }

    @Override
    public void updateScoreboardReturnUi(int addScore) {
//        Log.d(TAG, "currentACtiomn: " + mMainLogAdapter.getmActions().get(0));
        if (mPresenter.getSelectedPlayer().getBackNumber() != opponentBackNumber) {
            mTextViewAwayScore.setText(Integer.toString(updateMinusAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateMinusHomeScore(addScore)));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
//
        super.onResume();
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
    public void openBoxScoreUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BoxSoreFragment fragment = BoxSoreFragment.newInstance();
        BoxScorePresenter boxScorePresenter;
        boxScorePresenter = new BoxScorePresenter(fragment, mPresenter.getGame(), false);
        fragmentTransaction.replace(R.id.container, fragment, Constants.SURFACE).addToBackStack(Constants.FRAGMENT_BOX_SCORE);
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
        fragmentTransaction.replace(R.id.container, fragment, Constants.SURFACE);
        fragmentTransaction.commit();
    }

    long lastPress;

    @Override
    public boolean onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastPress > doubleTapBackTime) {
            Toast.makeText(getActivity(), Constants.BACK_EXIT_CONFIRM, Toast.LENGTH_SHORT).show();
            lastPress = currentTime;
            return true;
        } else {
            return false;
        }
    }
}
