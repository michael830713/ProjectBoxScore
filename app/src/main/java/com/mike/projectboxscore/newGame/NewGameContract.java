package com.mike.projectboxscore.newGame;

import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;
import com.mike.projectboxscore.mainConsole.MainConsoleViewContract;

import java.util.ArrayList;

public interface NewGameContract {

    interface View extends BaseView<NewGameContract.Presenter> {

        void openMainConsoleUi();

    }

    interface Presenter extends BasePresenter {
        void setupNewTeam(Team team);

        void openMainConsole();

        ArrayList<Team> getTeams();
    }
}
