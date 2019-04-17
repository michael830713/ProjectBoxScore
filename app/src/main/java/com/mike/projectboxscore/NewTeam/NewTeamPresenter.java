package com.mike.projectboxscore.NewTeam;

import android.util.Log;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewTeamPresenter implements NewTeamContract.Presenter {

    private static final String TAG = "NewTeamPresenter";

    NewTeamContract.View mView;
    //    private ArrayList<Team> mMyTeams;
    private Team mSelectedTeam;
    ArrayList<Player> mTeamPlayer = new ArrayList<>();
    Player mNewPlayer;
    private Game mNewGame;

    @Override
    public void start() {

    }

    public NewTeamPresenter(NewTeamContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
//        mMyTeams = new ArrayList<>();

    }

    @Override
    public void createNewTeam() {
        String teamName = mView.getTeamName();
        if (teamName.isEmpty()) {
            showToast("Please enter team name!");
        } else {
            Team team = new Team(teamName);
        }

    }

    @Override
    public void setNewPlayer() {
        Player player = new Player();
        mNewPlayer = player;
    }

    @Override
    public Player getNewPlayer() {
        return mNewPlayer;
    }

    @Override
    public ArrayList<Player> getTeamPlayer() {
        return mTeamPlayer;
    }

    @Override
    public void openMainConsole() {
//        mView.openMainConsoleUi();
    }

    @Override
    public void setupGameData() {
//        mView.getGameDataAndSetNewGame();
    }

    public Game getmNewGame() {
        return mNewGame;
    }

    @Override
    public void showPlayersOnTeam(int teamPosition) {
//        mSelectedTeam = mMyTeams.get(teamPosition);
//        mView.showPlayersOnTeamUi(mMyTeams.get(teamPosition).getmPlayers());
    }

    @Override
    public void showNewPlayerDialog() {
        mView.showNewPlayerUi();
    }

    @Override
    public void setupNewTeam(Team team) {
//        mMyTeams.add(team);
    }

    @Override
    public void updateData() {
        mView.updateDataUi();
    }

    @Override
    public ArrayList<Team> getTeams() {
//        return mMyTeams;
        return null;
    }

    public Team getmSelectedTeam() {
        return mSelectedTeam;
    }

    @Override
    public void showToast(String message) {
        mView.showToastMessageUi(message);
    }
}
