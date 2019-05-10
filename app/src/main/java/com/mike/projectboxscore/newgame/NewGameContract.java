package com.mike.projectboxscore.newgame;

import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.datas.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface NewGameContract {

    interface View extends BaseView<NewGameContract.Presenter> {

        void openMainConsoleUi();

        void showPlayersOnTeamUi(ArrayList<Player> playerStats);

        void showToastMessageUi(String message);

        void getGameDataAndSetNewGame();

    }

    interface Presenter extends BasePresenter {

        void openMainConsole();

        void setupGameData();

        void setNewGame(String opponent, String tournament);

        void showPlayersOnTeam(int teamPosition);

        Game getmNewGame();

        ArrayList<Team> getTeams();

        Team getmSelectedTeam();

        void showToast(String message);

        void setPlayerStats();
    }
}
