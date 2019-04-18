package com.mike.projectboxscore.NewTeam;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface NewTeamContract {

    interface View extends BaseView<NewTeamContract.Presenter> {

        void showPlayersOnTeamUi(ArrayList<Player> playerStats);

        void showToastMessageUi(String message);

        String getTeamName();

        ArrayList<Player> getPlayers();

        void showNewPlayerUi();
        void openMyTeamFragmentUi();

        void updateDataUi();

    }

    interface Presenter extends BasePresenter {
        void setupNewTeam(Team team);

        void setNewPlayer(String name, String email, String onCourtPosition, int backNumber);

        Player getNewPlayer();

        ArrayList<Player> getTeamPlayer();

        void updateData();

        void openMyTeamFragment();

        void createNewTeam();

        void showNewPlayerDialog();

        ArrayList<Team> getTeams();

        void showToast(String message);
    }
}
