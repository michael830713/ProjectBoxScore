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
    boolean mIsExit;
    private Game mGame;
    private ArrayList<PlayerStats> mOnCourtPlayers;
    private ArrayList<PlayerStats> mOnBenchPlayers;
    private ArrayList<PlayerStats> mTeamPlayers;

    public BoxScorePresenter(BoxScoreViewContract.View view, Game game, boolean isExit) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mGame = game;
        mTeamPlayers = game.getmPlayerStats();
        Log.d(TAG, "mTeamPlayers: " + mTeamPlayers);
        mIsExit = isExit;
        mOnCourtPlayers = new ArrayList<>();
        mOnBenchPlayers = new ArrayList<>();

    }

    @Override
    public Game getGame() {
        return mGame;
    }

    @Override
    public int getAwayScore() {
        return mGame.getmAwayScore();
    }

    @Override
    public boolean getExit() {
        return mIsExit;
    }

    @Override
    public void openHome() {

    }

    @Override
    public int getHomeScore() {
        return mGame.getmHomeScore();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public ArrayList<PlayerStats> getPlayerStats() {
        return mTeamPlayers;
    }

    @Override
    public void start() {

    }
}