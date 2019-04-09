package com.mike.projectboxscore.mainConsole.substituteDialog;

import android.util.Log;

import com.mike.projectboxscore.Data.PlayerStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class SubDialogPresenter implements SubContract.Presenter {
    private static final String TAG = "SubDialogPresenter";
    SubContract.View mView;
    private ArrayList<PlayerStats> mPlayerOnBench;

    public SubDialogPresenter(SubContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mPlayerOnBench = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void showPlayer() {
        Log.d(TAG, "showPlayer: "+mPlayerOnBench);
        mView.showPlayerUi(mPlayerOnBench);
    }

    public void setBenchPlayer(ArrayList<PlayerStats> player) {
        mPlayerOnBench = player;
    }
}
