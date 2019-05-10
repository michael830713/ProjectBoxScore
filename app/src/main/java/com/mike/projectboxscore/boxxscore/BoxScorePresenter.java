package com.mike.projectboxscore.boxxscore;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.datas.PlayerStats;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class BoxScorePresenter implements BoxScoreViewContract.Presenter {

    private static final String TAG = "BoxScorePresenter";

    BoxScoreViewContract.View mView;
    boolean mIsExit;
    private Game mGame;
    private ArrayList<PlayerStats> mTeamPlayers;

    public BoxScorePresenter(BoxScoreViewContract.View view, Game game, boolean isExit) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
        mView.setPresenter(this);
        mGame = game;
        mTeamPlayers = game.getmPlayerStats();
        mIsExit = isExit;

    }

    @Override
    public Game getGame() {
        return mGame;
    }

    @Override
    public int getAwayScore() {
        return mGame.getmMyScore();
    }

    @Override
    public boolean getExit() {
        return mIsExit;
    }

    @Override
    public void openHome() {
        mView.openHomeUi();
    }

    @Override
    public int getHomeScore() {
        return mGame.getmOpponentScore();
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
