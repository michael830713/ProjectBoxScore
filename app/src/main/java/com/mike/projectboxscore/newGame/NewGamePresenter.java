package com.mike.projectboxscore.newGame;

import android.util.Log;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Team;

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

    public NewGamePresenter(NewGameContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mMyTeams = new ArrayList<>();

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
        Log.d(TAG, "selectedTeam: " + getmSelectedTeam());
        mNewGame = new Game(opponent, tournament, getmSelectedTeam());
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
    public void setupNewTeam(Team team) {
        mMyTeams.add(team);
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
