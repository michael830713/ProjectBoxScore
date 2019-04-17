package com.mike.projectboxscore.NewTeam.NewPlayerDialog;

import android.util.Log;

import com.mike.projectboxscore.Data.PlayerStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialogPresenter implements NewPlayerDialogContract.Presenter {
    private static final String TAG = "NewPlayerDialogPresenter";
    NewPlayerDialogContract.View mView;
    private ArrayList<PlayerStats> mPlayerOnBench;
    private PlayerStats mTobeReplacedPlayer;

    public NewPlayerDialogPresenter(NewPlayerDialogContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mPlayerOnBench = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void changePlayer(int rowIndex) {
        mPlayerOnBench.get(rowIndex).setOnCourt(true);
        mTobeReplacedPlayer.setOnCourt(false);
        mView.changePlayerUi(mPlayerOnBench.get(rowIndex));
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
        Log.d(TAG, "showPlayer: " + mPlayerOnBench);
        mView.showPlayerUi(mPlayerOnBench);
    }

    public void setBenchPlayer(ArrayList<PlayerStats> player) {
        mPlayerOnBench = player;
    }
}
