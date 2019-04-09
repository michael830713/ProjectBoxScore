package com.mike.projectboxscore.mainConsole;

import com.mike.projectboxscore.Data.PlayerStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsolePresenter implements MainConsoleViewContract.Presenter {

    MainConsoleViewContract.View mView;
    private PlayerStats mSelectedPlayer;
    private ArrayList<PlayerStats> mOnCourtPlayers;
    private ArrayList<PlayerStats> mOnBenchPlayers;
    private ArrayList<PlayerStats> mTeamPlayers;

    public MainConsolePresenter(MainConsoleViewContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mOnCourtPlayers = new ArrayList<>();
        mTeamPlayers = new ArrayList<>();

    }

    @Override
    public void setupNewPlayer(String name, int backNumber, String onCourtPosition) {
        PlayerStats playerStats = new PlayerStats(name, backNumber, onCourtPosition);
        mTeamPlayers.add(playerStats);
    }

    @Override
    public void setupNewPlayer(String name, int backNumber, String onCourtPosition, boolean isOnCourt) {
        PlayerStats playerStats = new PlayerStats(name, backNumber, onCourtPosition, isOnCourt);
        mTeamPlayers.add(playerStats);
    }

    @Override
    public void setPlayerOnCourt(int backNumber) {
        for (int i = 0; i < mTeamPlayers.size(); i++) {
            if (mTeamPlayers.get(i).getBackNumber() == backNumber) {
                mTeamPlayers.get(i).setOnCourt(true);
            }
        }
    }

    @Override
    public void setPlayerOffCourt(int backNumber) {
        for (int i = 0; i < mTeamPlayers.size(); i++) {
            if (mTeamPlayers.get(i).getBackNumber() == backNumber) {
                mTeamPlayers.get(i).setOnCourt(false);
            }
        }
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
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void doNormalMode(int screenWidth, int screenHeight) {

    }

    @Override
    public void doFullscreenMode(int screenWidth, int screenHeight) {

    }

    @Override
    public void setScreenInfo(int screenWidth, int screenHeight) {

    }

    @Override
    public void setVideoInfo(int videoWidth, int videoHeight) {

    }

    @Override
    public void showMadeOrMissDialog(int rowIndex, int addPoints) {
        mView.showMadeOrMissDialogUi(addPoints);

    }

    @Override
    public void showSubstituteDialog() {
        mView.showSubstituteDialogUi();
    }

    @Override
    public void returnLastStep() {
        mView.returnLastStepUi();
    }

    @Override
    public PlayerStats getSelectedPlayer() {
        return mSelectedPlayer;
    }

    @Override
    public void requestPortrait() {

    }

    @Override
    public void updateLog(int addPoint, boolean isShotMade) {
        mView.updateLogUi(addPoint, isShotMade);
    }

    @Override
    public void updateLog(String action) {
        mView.updateLogUi(action);
    }

    @Override
    public void removeLog() {
        mView.removeLogUi();
    }

    @Override
    public void updatePlayerScores(int addPoints) {
        int currentPoints = mSelectedPlayer.getPoints();
        int newPoint = currentPoints + addPoints;
        mSelectedPlayer.setPoints(newPoint);
        if (addPoints > 0) {

            if (addPoints != 1) {
                playerMadeShot();
                playerShoot();

                if (addPoints == 3) {
                    playerMade3PtShot();
                    player3ptShoot();
                }
            }
        } else if (addPoints == -2 || addPoints == -3) {

            playerMadeShotReturn();
            playerShootReturn();
            if (addPoints == -3) {
                player3ptShootReturn();
                playerMade3PtShotReturn();
            }
        } else {

        }
//        updateLog(addPoints);
    }

    @Override
    public void updatePlayerMisses(int addPoints) {
        if (addPoints != 1 && addPoints > 0) {
            playerShoot();

            if (addPoints == 3) {
                player3ptShoot();
            }
        } else if (addPoints == -2 || addPoints == -3) {
            playerShootReturn();
            if (addPoints == -3) {
                player3ptShootReturn();
            }
        }

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
        int newShotMade = currentShotMade + 1;
        mSelectedPlayer.setShotMade(newShotMade);
    }

    @Override
    public void playerShoot() {
        int currentShotTaken = mSelectedPlayer.getShotTaken();
        int newTakenShot = currentShotTaken + 1;
        mSelectedPlayer.setShotTaken(newTakenShot);
    }

    @Override
    public void playerMade3PtShot() {
        int currentShotMade = mSelectedPlayer.getThreePointShotMade();
        int newShotMade = currentShotMade + 1;
        mSelectedPlayer.setThreePointShotMade(newShotMade);
    }

    @Override
    public void player3ptShoot() {
        int currentShotTaken = mSelectedPlayer.getThreePointShotTaken();
        int newTakenShot = currentShotTaken + 1;
        mSelectedPlayer.setThreePointShotTaken(newTakenShot);
    }

    @Override
    public void playerMadeShotReturn() {
        int currentShotMade = mSelectedPlayer.getShotMade();
        int newShotMade = currentShotMade - 1;
        mSelectedPlayer.setShotMade(newShotMade);
    }

    @Override
    public void playerShootReturn() {
        int currentShotTaken = mSelectedPlayer.getShotTaken();
        int newTakenShot = currentShotTaken - 1;
        mSelectedPlayer.setShotTaken(newTakenShot);
    }

    @Override
    public void playerMade3PtShotReturn() {
        int currentShotMade = mSelectedPlayer.getThreePointShotMade();
        int newShotMade = currentShotMade - 1;
        mSelectedPlayer.setThreePointShotMade(newShotMade);
    }

    @Override
    public void player3ptShootReturn() {
        int currentShotTaken = mSelectedPlayer.getThreePointShotTaken();
        int newTakenShot = currentShotTaken - 1;
        mSelectedPlayer.setThreePointShotTaken(newTakenShot);
    }

    @Override
    public void playerOffensiveRebounded(int amount) {
        int newRebounds = mSelectedPlayer.getOffensiveRebounds() + amount;

        mSelectedPlayer.setOffensiveRebounds(newRebounds);

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

        mSelectedPlayer.setBlocks(newAmount);
    }

    @Override
    public void start() {

    }
}
