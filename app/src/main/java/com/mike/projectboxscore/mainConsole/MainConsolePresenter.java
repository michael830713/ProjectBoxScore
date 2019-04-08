package com.mike.projectboxscore.mainConsole;

import com.mike.projectboxscore.Data.PlayerOnCourtStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsolePresenter implements MainConsoleViewContract.Presenter {

    MainConsoleViewContract.View mView;
    private PlayerOnCourtStats mSelectedPlayer;
    private ArrayList<PlayerOnCourtStats> mPlayers;

    public MainConsolePresenter(MainConsoleViewContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mPlayers = new ArrayList<PlayerOnCourtStats>();

    }

    @Override
    public void setupNewPlayer(String name, int backNumber, String onCourtPosition) {
        mPlayers.add(new PlayerOnCourtStats(name, backNumber, onCourtPosition));
    }

    @Override
    public ArrayList<PlayerOnCourtStats> getPlayers() {
        return mPlayers;
    }

    @Override
    public void setSelectedPlayer(PlayerOnCourtStats playerOnCourtStats) {
        mSelectedPlayer = playerOnCourtStats;
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
    public void calculateAndUpdateScore(int addScore) {
        mView.updateScoreUi(addScore);
    }

    @Override
    public void showMadeOrMissDialog(int rowIndex, int addPoints) {
        mView.showMadeOrMissDialogUi(addPoints);

    }

    @Override
    public PlayerOnCourtStats getSelectedPlayer() {
        return mSelectedPlayer;
    }

    @Override
    public void requestPortrait() {

    }

    @Override
    public void updatePlayerScores(int addPoints) {
        int currentPoints = mSelectedPlayer.getPoints();
        int newPoint = currentPoints + addPoints;
        mSelectedPlayer.setPoints(newPoint);
        if (addPoints != 1) {
            playerMadeShot();
            playerShoot();

            if (addPoints == 3) {
                playerMade3PtShot();
                player3ptShoot();
            }
        }


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
    public void start() {

    }
}
