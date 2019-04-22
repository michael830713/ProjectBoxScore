package com.mike.projectboxscore.EditTeam;

import android.util.Log;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditTeamPresenter implements EditTeamContract.Presenter {

    private static final String TAG = "EditTeamPresenter";

    EditTeamContract.View mView;
    private ArrayList<Team> mMyTeams;
    private Team mTeam;
    private Team mSelectedTeam;
    ArrayList<Player> mTeamPlayer = new ArrayList<>();
    Player mNewPlayer;
    private Game mNewGame;

    @Override
    public void start() {

    }

    public EditTeamPresenter(EditTeamContract.View view, Team team) {
        mView = checkNotNull(view, "view cannot be null!");
        mTeam = team;
        Log.d(TAG, "Team: " + mTeam);
        mTeamPlayer=mTeam.getmPlayers();
        mView.setPresenter(this);
//        mMyTeams = new ArrayList<>();

    }

    @Override
    public void showEditPlayerDialog(Player player) {
        mView.showEditPlayerUi(player);
    }

    @Override
    public Team getTeam() {
        return mTeam;
    }

    @Override
    public void createNewTeam() {
        String teamName = mView.getTeamName();
        if (teamName.isEmpty()) {
            showToast("Please enter team name!");
        } else {
            Team team = new Team(teamName);
            team.setmPlayers(mTeamPlayer);
            mMyTeams.add(team);
        }

    }

    @Override
    public void setNewPlayer(String name, String email, String onCourtPosition, int backNumber) {
        Player player = new Player(name, email, backNumber, onCourtPosition);
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
    public void openMyTeamFragment() {
        mView.openMyTeamFragmentUi();
    }

    @Override
    public ArrayList<Team> getTeams() {
//        return mMyTeams;
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
