package com.mike.projectboxscore.teams;

import com.mike.projectboxscore.callback.GamesDataCallback;
import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.datas.Team;
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

        void loadGameData(int i, GamesDataCallback callback);

        ArrayList<Game> getGames();
    }
}
