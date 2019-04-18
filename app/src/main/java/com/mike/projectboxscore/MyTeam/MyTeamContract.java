package com.mike.projectboxscore.MyTeam;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface MyTeamContract {

    interface View extends BaseView<MyTeamContract.Presenter> {

        void showPlayersOnTeamUi(ArrayList<Player> playerStats);

        void showToastMessageUi(String message);

        String getTeamName();

        void showNewPlayerUi();

        void updateDataUi();

    }

    interface Presenter extends BasePresenter {
        void setupNewTeam(Team team);

        void setNewPlayer(String name, String email, String onCourtPosition, int backNumber);

        Player getNewPlayer();

        ArrayList<Player> getTeamPlayer();

        void openMainConsole();

        void updateData();

        void setupGameData();

        void showPlayersOnTeam(int teamPosition);

        void createNewTeam();

        Game getmNewGame();

        void showNewPlayerDialog();

        ArrayList<Team> getTeams();

        Team getmSelectedTeam();

        void showToast(String message);
    }
}
