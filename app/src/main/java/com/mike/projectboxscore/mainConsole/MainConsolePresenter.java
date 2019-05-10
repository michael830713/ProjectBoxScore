package com.mike.projectboxscore.mainConsole;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.Data.Action;
import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.FirebaseDataSource;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsolePresenter implements MainConsoleViewContract.Presenter {

    private static final String TAG = "MainConsolePresenter";

    MainConsoleViewContract.View mView;
    private PlayerStats mSelectedPlayer;
    private Game mGame;
    private ArrayList<PlayerStats> mOnCourtPlayers;
    private ArrayList<PlayerStats> mOnBenchPlayers;
    private ArrayList<PlayerStats> mTeamPlayers;

    public MainConsolePresenter(MainConsoleViewContract.View view, Game game) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
        mView.setPresenter(this);
        mTeamPlayers = game.getmPlayerStats();
        mGame = game;
        Log.d(TAG, "mTeamPlayers: " + mTeamPlayers);
        mOnCourtPlayers = new ArrayList<>();
        mOnBenchPlayers = new ArrayList<>();

    }

    @Override
    public void setOnCourtPlayers() {
        ArrayList<PlayerStats> playerStats = new ArrayList<>();

        for (int i = 0; i < mTeamPlayers.size(); i++) {
            if (mTeamPlayers.get(i).isOnCourt()) {
                playerStats.add(mTeamPlayers.get(i));
            }
        }
        mOnCourtPlayers = playerStats;
    }

    @Override
    public void setOnBenchPlayers() {
        ArrayList<PlayerStats> playerStats = new ArrayList<>();

        for (int i = 0; i < mTeamPlayers.size(); i++) {
            if (!mTeamPlayers.get(i).isOnCourt()) {
                playerStats.add(mTeamPlayers.get(i));
            }
        }
        mOnBenchPlayers = playerStats;
    }

    @Override
    public ArrayList<PlayerStats> getPlayers() {
        return mOnCourtPlayers;
    }

    @Override
    public ArrayList<PlayerStats> getOnBenchPlayers() {
        return mOnBenchPlayers;
    }

    @Override
    public void setSelectedPlayer(PlayerStats playerStats) {
        mSelectedPlayer = playerStats;
    }

    @Override
    public Game getGame() {
        return mGame;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void showMadeOrMissDialog(int addPoints) {
        mView.showMadeOrMissDialogUi(addPoints);

    }

    @Override
    public void showSubstituteDialog() {
        mView.showSubstituteDialogUi();
    }

    @Override
    public void returnLastStep(int i) {
        mView.returnLastStepUi(i);
    }

    @Override
    public PlayerStats getSelectedPlayer() {
        return mSelectedPlayer;
    }

    @Override
    public void updateLog(int addPoint, boolean isShotMade) {
        mView.updateLogUi(addPoint, isShotMade);
    }

    @Override
    public void updateLog(Action action) {
        mView.updateLogUi(action);
    }

    @Override
    public void removeLog(int i) {
        mView.removeLogUi(i);
    }

    @Override
    public void updatePlayerScores(int addPoints) {
        int currentPoints = mSelectedPlayer.getPoints();
        int newPoint = currentPoints + addPoints;
        mSelectedPlayer.setPoints(newPoint);
        if (addPoints > 0) {
            if (addPoints == Constants.ONE) {
                playerMadeFreeThrow();
                playerFreeThrowShoot();
            }

            if (addPoints != Constants.ONE) {
                playerMadeShot();
                playerShoot();

                if (addPoints == Constants.THREE_POINT) {
                    playerMade3PtShot();
                    player3ptShoot();
                }
            }
        } else if (addPoints == Constants.RETURN_TWO_POINTS || addPoints == Constants.RETURN_THREE_POINTS) {

            playerMadeShotReturn();
            playerShootReturn();
            if (addPoints == Constants.RETURN_THREE_POINTS) {
                player3ptShootReturn();
                playerMade3PtShotReturn();
            }
        } else if (addPoints == Constants.RETURN_ONE_STAT) {
            playerFreeThrowReturn();
            playerMadeFreeThrowReturn();
        }
        setShotPersentage();
    }

    @Override
    public void updatePlayerMisses(int addPoints) {
        if (addPoints == Constants.ONE) {
            playerFreeThrowShoot();
        } else if (addPoints > 0) {
            playerShoot();

            if (addPoints == Constants.THREE_POINT) {
                player3ptShoot();
            }
        } else if (addPoints == Constants.RETURN_TWO_POINTS || addPoints == Constants.RETURN_THREE_POINTS) {
            playerShootReturn();
            if (addPoints == Constants.RETURN_THREE_POINTS) {
                player3ptShootReturn();
            }
        } else if (addPoints == Constants.RETURN_ONE_STAT) {
            playerFreeThrowReturn();
        }
        setShotPersentage();
    }

    @Override
    public void updateScoreboard(int addPoint) {
        mView.updateScoreboardUi(addPoint);
    }

    @Override
    public void updateScoreboardReturn(int addPoint) {
        mView.updateScoreboardReturnUi(addPoint);
    }

    @Override
    public void playerMadeShot() {
        int currentShotMade = mSelectedPlayer.getShotMade();
        int newShotMade = currentShotMade + Constants.ONE;
        mSelectedPlayer.setShotMade(newShotMade);
    }

    @Override
    public void playerShoot() {
        int currentShotTaken = mSelectedPlayer.getShotTaken();
        int newTakenShot = currentShotTaken + Constants.ONE;
        mSelectedPlayer.setShotTaken(newTakenShot);
    }

    @Override
    public void playerMade3PtShot() {
        int currentShotMade = mSelectedPlayer.getThreePointShotMade();
        int newShotMade = currentShotMade + Constants.ONE;
        mSelectedPlayer.setThreePointShotMade(newShotMade);
    }

    @Override
    public void player3ptShoot() {
        int currentShotTaken = mSelectedPlayer.getThreePointShotTaken();
        int newTakenShot = currentShotTaken + Constants.ONE;
        mSelectedPlayer.setThreePointShotTaken(newTakenShot);
    }

    @Override
    public void playerMadeFreeThrow() {
        int currentShotTaken = mSelectedPlayer.getFreeThrowMade();
        int newTakenShot = currentShotTaken + Constants.ONE;
        mSelectedPlayer.setFreeThrowMade(newTakenShot);
    }

    @Override
    public void playerFreeThrowShoot() {
        int currentShotTaken = mSelectedPlayer.getFreeThrowTaken();
        int newTakenShot = currentShotTaken + Constants.ONE;
        mSelectedPlayer.setFreeThrowTaken(newTakenShot);
    }

    @Override
    public void playerMadeShotReturn() {
        int currentShotMade = mSelectedPlayer.getShotMade();
        int newShotMade = currentShotMade - Constants.ONE;
        mSelectedPlayer.setShotMade(newShotMade);
    }

    @Override
    public void playerShootReturn() {
        int currentShotTaken = mSelectedPlayer.getShotTaken();
        int newTakenShot = currentShotTaken - Constants.ONE;
        mSelectedPlayer.setShotTaken(newTakenShot);
    }

    @Override
    public void playerMade3PtShotReturn() {
        int currentShotMade = mSelectedPlayer.getThreePointShotMade();
        int newShotMade = currentShotMade - Constants.ONE;
        mSelectedPlayer.setThreePointShotMade(newShotMade);
    }

    @Override
    public void player3ptShootReturn() {
        int currentShotTaken = mSelectedPlayer.getThreePointShotTaken();
        int newTakenShot = currentShotTaken - Constants.ONE;
        mSelectedPlayer.setThreePointShotTaken(newTakenShot);
    }

    @Override
    public void playerMadeFreeThrowReturn() {
        int currentShotMade = mSelectedPlayer.getFreeThrowMade();
        int newShotMade = currentShotMade - Constants.ONE;
        mSelectedPlayer.setFreeThrowMade(newShotMade);
    }

    @Override
    public void playerFreeThrowReturn() {
        int currentShotMade = mSelectedPlayer.getFreeThrowTaken();
        int newShotMade = currentShotMade - Constants.ONE;
        mSelectedPlayer.setFreeThrowTaken(newShotMade);
    }

    @Override
    public void playerOffensiveRebounded(int amount) {
        int newRebounds = mSelectedPlayer.getOffensiveRebounds() + amount;

        mSelectedPlayer.setOffensiveRebounds(newRebounds);
        setRebound();

    }

    @Override
    public void playerDefensiveRebounded(int amount) {
        int newRebounds = mSelectedPlayer.getDefensiveRebounds() + amount;

        mSelectedPlayer.setDefensiveRebounds(newRebounds);
    }

    @Override
    public void playerAssisted(int amount) {
        int newAmount = mSelectedPlayer.getAssists() + amount;

        mSelectedPlayer.setAssists(newAmount);
    }

    @Override
    public void playerTurnedOver(int amount) {
        int newAmount = mSelectedPlayer.getTurnOvers() + amount;

        mSelectedPlayer.setTurnOvers(newAmount);
    }

    @Override
    public void playerFouled(int amount) {
        int newAmount = mSelectedPlayer.getFouls() + amount;

        mSelectedPlayer.setFouls(newAmount);
    }

    @Override
    public void playerstealed(int amount) {
        int newAmount = mSelectedPlayer.getSteals() + amount;

        mSelectedPlayer.setSteals(newAmount);
    }

    @Override
    public void playerBlocked(int amount) {
        int newAmount = mSelectedPlayer.getBlocks() + amount;
        Log.d(TAG, mSelectedPlayer.getName() + " playerBlocked: " + newAmount);
        mSelectedPlayer.setBlocks(newAmount);
    }

    @Override
    public void setRebound() {
        for (PlayerStats playerStats : mTeamPlayers) {
            playerStats.setTotalRebound();
        }

    }

    @Override
    public void setShotPersentage() {
        for (PlayerStats playerStats : mTeamPlayers) {
            playerStats.setShotPercentage();
        }
    }

    @Override
    public void showTutorialDialog() {
        mView.showTutorialUi();
    }

    @Override
    public Action setNewAction(int actionCode, String action) {
        Action action1 = new Action(actionCode, action);
        return action1;
    }

    @Override
    public void openBoxScore() {
        mView.openBoxScoreUi();
    }

    @Override
    public void openExitBoxScore() {
        mView.openExitBoxScoreUi();
    }

    @Override
    public void addNewGame() {
        mGame.setmMyScore(mView.getAwayScore());
        mGame.setmOpponentScore(mView.getHomeScore());
    }

    @Override
    public void updateFirebaseData() {
        FirebaseDataSource.uploadNewGame(mGame);

    }

    @Override
    public void showConfirmExitDialog() {
        mView.showConfirmExitDialogUi();
    }

    @Override
    public void start() {

    }
}
