package com.mike.projectboxscore.NewTeam.NewPlayerDialog;

import android.util.Log;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.PlayerStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialogPresenter implements NewPlayerDialogContract.Presenter {
    private static final String TAG = "NewPlayerDialogPresenter";
    NewPlayerDialogContract.View mView;
    private Player mPlayer;
    private PlayerStats mTobeReplacedPlayer;

    public NewPlayerDialogPresenter(NewPlayerDialogContract.View view, Player player) {
        mView = checkNotNull(view, "view cannot be null!");
        mPlayer = player;
    }

    @Override
    public void start() {

    }

    @Override
    public void setNewPlayerInfo(String mName, String mEmail, int backNumber, String onCourtPosition) {
        mPlayer.setmName(mName);
        mPlayer.setmEmail(mEmail);
        mPlayer.setBackNumber(backNumber);
        mPlayer.setOnCourtPosition(onCourtPosition);
    }

    @Override
    public void deletePlayer() {

        mPlayer = null;
        System.gc();
    }

    @Override
    public void changePlayer(int rowIndex) {
    }

    @Override
    public void setPositionSpinner() {
        mView.setPositionSpinnerUi();
    }

    @Override
    public void setToBeReplacedPlayer(PlayerStats playerToEnterGame) {
        mTobeReplacedPlayer = playerToEnterGame;
    }

    @Override
    public void showPlayer() {
    }

    public void setBenchPlayer(ArrayList<PlayerStats> player) {
    }
}
