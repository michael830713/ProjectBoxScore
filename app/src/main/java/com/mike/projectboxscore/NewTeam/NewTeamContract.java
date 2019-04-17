package com.mike.projectboxscore.NewTeam;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface NewTeamContract {

    interface View extends BaseView<NewTeamContract.Presenter> {

        void openMainConsoleUi();

        void showPlayersOnTeamUi(ArrayList<Player> playerStats);

        void showToastMessageUi(String message);

        void getGameDataAndSetNewGame();

    }

    interface Presenter extends BasePresenter {
        void setupNewTeam(Team team);

        void openMainConsole();

        void setupGameData();

        void setNewGame(String opponent, String tournament);

        void showPlayersOnTeam(int teamPosition);

        Game getmNewGame();

        ArrayList<Team> getTeams();

        Team getmSelectedTeam();

        void showToast(String message);
    }
}
