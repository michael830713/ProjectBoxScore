package com.mike.projectboxscore.mainConsole.substituteDialog;

import android.util.Log;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.Data.PlayerStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class SubDialogPresenter implements SubContract.Presenter {
    private static final String TAG = "SubDialogPresenter";
    SubContract.View mView;
    private ArrayList<PlayerStats> mPlayerOnBench;
    private PlayerStats mTobeReplacedPlayer;

    public SubDialogPresenter(SubContract.View view) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
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
    public void setToBeReplacedPlayer(PlayerStats playerToEnterGame) {
        mTobeReplacedPlayer = playerToEnterGame;
    }

    @Override
    public void showPlayer() {
        mView.showPlayerUi(mPlayerOnBench);
    }

    public void setBenchPlayer(ArrayList<PlayerStats> player) {
        mPlayerOnBench = player;
    }
}
