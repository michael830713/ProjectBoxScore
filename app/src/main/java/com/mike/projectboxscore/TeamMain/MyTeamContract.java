package com.mike.projectboxscore.TeamMain;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface MyTeamContract {

    interface View extends BaseView<MyTeamContract.Presenter> {

        void showToastMessageUi(String message);


        void showNewPlayerUi();

        void openNewTeamFragmentUi();

        void updateTeamDataUi();

        void showEditPlayerUi(Player player);

        void showEditTeamUi();

        void openBoxScoreUi(Game game);

        void upDateGameDataUi();
    }

    interface Presenter extends BasePresenter {


        void openNewTeamFragment();

        void updateTeamData();

        ArrayList<Team> getTeams();


        void setNewPlayer(String name, String email, String onCourtPosition, int parseInt, Team selectedTeam);

        void updatePlayerToFirebase(Team team);

        Player getNewPlayer();

        void showEditTeam();

        void openBoxScore(Game game);

        void loadGameData(int i,GamesDataCallback callback);

        ArrayList<Game> getGames();
    }
}