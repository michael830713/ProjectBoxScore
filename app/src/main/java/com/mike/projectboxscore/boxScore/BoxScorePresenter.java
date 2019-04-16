package com.mike.projectboxscore.boxScore;

import android.util.Log;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.PlayerStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class BoxScorePresenter implements BoxScoreViewContract.Presenter {

    private static final String TAG = "BoxScorePresenter";

    BoxScoreViewContract.View mView;
    private PlayerStats mSelectedPlayer;
    private ArrayList<PlayerStats> mOnCourtPlayers;
    private ArrayList<PlayerStats> mOnBenchPlayers;
    private ArrayList<PlayerStats> mTeamPlayers;

    public BoxScorePresenter(BoxScoreViewContract.View view, Game game) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mTeamPlayers = game.getmPlayerStats();
        Log.d(TAG, "mTeamPlayers: " + mTeamPlayers);
        mOnCourtPlayers = new ArrayList<>();
        mOnBenchPlayers = new ArrayList<>();

    }



    @Override
    public void result(int requestCode, int resultCode) {

    }



    @Override
    public void start() {

    }
}
