package com.mike.projectboxscore.newGame;

import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.loginUI.LoginUIViewContract;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewGamePresenter implements NewGameContract.Presenter {

    NewGameContract.View mView;
    private ArrayList<Team> mTeams;

    @Override
    public void start() {

    }

    public NewGamePresenter(NewGameContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mTeams = new ArrayList<>();

    }

    @Override
    public void openMainConsole() {
        mView.openMainConsoleUi();
    }

    @Override
    public void showPlayersOnTeam(int teamPosition) {

        mView.showPlayersOnTeamUi(mTeams.get(teamPosition).getmPlayers());
    }

    @Override
    public void setupNewTeam(Team team) {
        mTeams.add(team);
    }

    @Override
    public ArrayList<Team> getTeams() {
        return mTeams;
    }
}
