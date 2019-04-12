package com.mike.projectboxscore.newGame;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;
import com.mike.projectboxscore.mainConsole.MainConsoleViewContract;

import java.util.ArrayList;

public interface NewGameContract {

    interface View extends BaseView<NewGameContract.Presenter> {

        void openMainConsoleUi();

        void showPlayersOnTeamUi(ArrayList<PlayerStats> playerStats);

    }

    interface Presenter extends BasePresenter {
        void setupNewTeam(Team team);

        void openMainConsole();

        void showPlayersOnTeam(int teamPosition);

        ArrayList<Team> getTeams();
    }
}
