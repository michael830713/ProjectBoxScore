package com.mike.projectboxscore.console;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.mike.projectboxscore.datas.Action;
import com.mike.projectboxscore.datas.PlayerStats;
import com.mike.projectboxscore.IOnBackPressed;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.boxxscore.BoxScorePresenter;
import com.mike.projectboxscore.boxxscore.BoxSoreFragment;
import com.mike.projectboxscore.callback.MadeOrMissCallback;
import com.mike.projectboxscore.console.confirmshot.MadeOrMissDialog;
import com.mike.projectboxscore.console.substitution.SubContract;
import com.mike.projectboxscore.console.substitution.SubDialogPresenter;
import com.mike.projectboxscore.console.substitution.SubstituteDialog;

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

            Action action;

            switch (v.getId()) {
                case R.id.button2Pts:
                    mPresenter.showMadeOrMissDialog(Constants.TWO_POINT);
                    break;

                case R.id.button3Pts:
                    mPresenter.showMadeOrMissDialog(Constants.THREE_POINT);
                    break;

                case R.id.buttonFreeThrow:
                    mPresenter.showMadeOrMissDialog(Constants.ONE);
                    break;

                case R.id.buttonOffensiveRebound:
                    mPresenter.playerOffensiveRebounded(Constants.ONE);
//                    Action action = new Action(Constants.CODE_OFFENSIVE_REBOUND, Constants.OFFENSIVE_REBOUND);
                    action = mPresenter.setNewAction(Constants.CODE_OFFENSIVE_REBOUND, Constants.OFFENSIVE_REBOUND);
                    mPresenter.updateLog(action);
                    break;

                case R.id.buttonDefensiveRebound:
                    mPresenter.playerDefensiveRebounded(Constants.ONE);
                    action = mPresenter.setNewAction(Constants.CODE_DEFENSIVE_REBOUND, Constants.DEFENSIVE_REBOUND);

                    mPresenter.updateLog(action);

                    break;

                case R.id.buttonAssist:
                    mPresenter.playerAssisted(Constants.ONE);

                    action = mPresenter.setNewAction(Constants.CODE_ASSIST, Constants.ASSIST);

                    mPresenter.updateLog(action);
                    break;

                case R.id.buttonTurnOver:
                    mPresenter.playerTurnedOver(Constants.ONE);

                    action = mPresenter.setNewAction(Constants.CODE_TURN_OVER, Constants.TURN_OVER);

                    mPresenter.updateLog(action);

                    break;

                case R.id.buttonFoul:
                    mPresenter.playerFouled(Constants.ONE);

                    action = mPresenter.setNewAction(Constants.CODE_FOUL, Constants.FOUL);

                    mPresenter.updateLog(action);

                    break;

                case R.id.buttonSteal:
                    mPresenter.playerStealed(Constants.ONE);

                    action = mPresenter.setNewAction(Constants.CODE_STEAL, Constants.STEAL);

                    mPresenter.updateLog(action);

                    break;

                case R.id.buttonBlock:
                    mPresenter.playerBlocked(Constants.ONE);

                    action = mPresenter.setNewAction(Constants.CODE_BLOCK, Constants.BLOCK);

                    mPresenter.updateLog(action);

                    break;

                case R.id.buttonSub:
                    if (mPresenter.getSelectedPlayer().getBackNumber() != Constants.OPPONENT_BACK_NUMBER) {
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
            mLogRecyclerView.smoothScrollToPosition(Constants.ADAPTER_TOP_POSITION);
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
//            String currentAction = mMainLogAdapter.getmActions().get(i);
            int currentActionCode = mMainLogAdapter.getmActions().get(i).getActionCode();
            switch (currentActionCode) {
                case Constants.CODE_TWO_POINTS_MADE:
                    mPresenter.updateScoreboardReturn(Constants.TWO_POINT);
                    mPresenter.updatePlayerScores(Constants.RETURN_TWO_POINTS);
                    mPresenter.removeLog(i);
                    break;
                case Constants.CODE_TWO_POINTS_MISS:
                    mPresenter.updatePlayerMisses(Constants.RETURN_TWO_POINTS);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_THREE_POINTS_MADE:
                    mPresenter.updateScoreboardReturn(Constants.THREE_POINT);
                    mPresenter.updatePlayerScores(Constants.RETURN_THREE_POINTS);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_THREE_POINTS_MISS:
                    mPresenter.updatePlayerMisses(Constants.RETURN_THREE_POINTS);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_FREE_THROW_MADE:
                    mPresenter.updateScoreboardReturn(Constants.ONE);
                    mPresenter.updatePlayerScores(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);

                    break;
                case Constants.CODE_FREE_THROW_MISS:
                    mPresenter.updatePlayerMisses(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_OFFENSIVE_REBOUND:
                    mPresenter.playerOffensiveRebounded(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);

                    break;
                case Constants.CODE_DEFENSIVE_REBOUND:
                    mPresenter.playerDefensiveRebounded(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_ASSIST:
                    mPresenter.playerAssisted(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_TURN_OVER:
                    mPresenter.playerTurnedOver(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_FOUL:
                    mPresenter.playerFouled(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_STEAL:
                    mPresenter.playerStealed(Constants.RETURN_ONE_STAT);
                    mPresenter.removeLog(i);
                    break;

                case Constants.CODE_BLOCK:
                    mPresenter.playerBlocked(Constants.RETURN_ONE_STAT);
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
            if (addScore == Constants.TWO_POINT) {

                Action action = new Action(Constants.CODE_TWO_POINTS_MADE, Constants.TWO_POINTS_MADE);
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);

            } else if (addScore == Constants.THREE_POINT) {

                Action action = new Action(Constants.CODE_THREE_POINTS_MADE, Constants.THREE_POINTS_MADE);
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);

            } else {

                Action action = new Action(Constants.CODE_FREE_THROW_MADE, Constants.FREE_THROW_MADE);
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);

            }

        } else {
            if (addScore == Constants.TWO_POINT) {

                Action action = new Action(Constants.CODE_TWO_POINTS_MISS, Constants.TWO_POINTS_MISS);
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);

            } else if (addScore == Constants.THREE_POINT) {

                Action action = new Action(Constants.CODE_THREE_POINTS_MISS, Constants.THREE_POINTS_MISS);
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);

            } else {

                Action action = new Action(Constants.CODE_FREE_THROW_MISS, Constants.FREE_THROW_MISS);
                mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);

            }

        }

        mLogRecyclerView.smoothScrollToPosition(Constants.ADAPTER_TOP_POSITION);
    }

    @Override
    public void updateLogUi(Action action) {
        mMainLogAdapter.setLog(mPresenter.getSelectedPlayer(), action);
    }

    @Override
    public void removeLogUi(int i) {
        ArrayList<PlayerStats> players = mMainLogAdapter.getmPlayers();
        players.remove(i);
        mMainLogAdapter.setmPlayers(players);
        ArrayList<Action> actions = mMainLogAdapter.getmActions();
        actions.remove(i);
        mMainLogAdapter.setmActionsRemoved(actions, i);
    }

    @Override
    public void updateScoreboardUi(int addScore) {
        if (mPresenter.getSelectedPlayer().getBackNumber() != Constants.OPPONENT_BACK_NUMBER) {
            mTextViewAwayScore.setText(Integer.toString(updateAddAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateAddHomeScore(addScore)));

        }
    }

    @Override
    public void updateScoreboardReturnUi(int addScore) {
        if (mPresenter.getSelectedPlayer().getBackNumber() != Constants.OPPONENT_BACK_NUMBER) {
            mTextViewAwayScore.setText(Integer.toString(updateMinusAwayScore(addScore)));
        } else {
            mTextViewHomeScore.setText(Integer.toString(updateMinusHomeScore(addScore)));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
