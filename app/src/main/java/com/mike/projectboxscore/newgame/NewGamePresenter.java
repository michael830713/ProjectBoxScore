package com.mike.projectboxscore.newgame;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.datas.PlayerStats;
import com.mike.projectboxscore.datas.Team;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewGamePresenter implements NewGameContract.Presenter {

    private static final String TAG = "NewGamePresenter";

    NewGameContract.View mView;
    private ArrayList<Team> mMyTeams;
    private Team mSelectedTeam;
    private Game mNewGame;

    @Override
    public void start() {

    }

    public NewGamePresenter(NewGameContract.View view, ArrayList<Team> myTeams) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
        mView.setPresenter(this);
        mMyTeams = myTeams;

    }

    @Override
    public void openMainConsole() {
        mView.openMainConsoleUi();
    }

    @Override
    public void setupGameData() {
        mView.getGameDataAndSetNewGame();
    }

    @Override
    public void setNewGame(String opponent, String tournament) {
        mNewGame = new Game(opponent, tournament, getmSelectedTeam().getName());
    }

    @Override
    public void setPlayerStats() {
        ArrayList<PlayerStats> playerStats = new ArrayList<>();
        for (Player player : mSelectedTeam.getmPlayers()) {
            PlayerStats playerStat = new PlayerStats(player.getName(), player.getBackNumber(), player.getOnCourtPosition(), player.isOnCourt(), player.getImageUrl());
            playerStats.add(playerStat);
        }
        mNewGame.addmPlayerStats(playerStats);
        mNewGame.addOpponentToPlayerStats();
    }

    public Game getmNewGame() {
        return mNewGame;
    }

    @Override
    public void showPlayersOnTeam(int teamPosition) {
        mSelectedTeam = mMyTeams.get(teamPosition);
        mView.showPlayersOnTeamUi(mMyTeams.get(teamPosition).getmPlayers());
    }


    @Override
    public ArrayList<Team> getTeams() {
        return mMyTeams;
    }

    public Team getmSelectedTeam() {
        return mSelectedTeam;
    }

    @Override
    public void showToast(String message) {
        mView.showToastMessageUi(message);
    }
}
