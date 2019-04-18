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

        void openNewTeamFragmentUi();

        void updateDataUi();

    }

    interface Presenter extends BasePresenter {
        void setupNewTeam(Team team);

        ArrayList<Player> getTeamPlayer();


        void openNewTeamFragment();

        void updateData();

        ArrayList<Team> getTeams();

        void showToast(String message);
    }
}
