package com.mike.projectboxscore.LoginUi;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.MainPage.MainPageContract;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginPagePresenter implements MainPageContract.Presenter {

    MainPageContract.View mView;
    ArrayList<Team> mTeams = new ArrayList<>();

    public LoginPagePresenter(MainPageContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);

    }

    @Override
    public void demoNewGameView() {
        mView.demoNewGameViewUi();
    }

    @Override
    public void demoMyTeamView() {

        mView.demoMyTeamViewUi();
    }

    @Override
    public void setSampleTeam() {
        Team allStar = new Team("All-star");

        allStar.addmPlayers(new Player("Jordan", 23, "G"));
        allStar.addmPlayers(new Player("Pippen", 4, "F"));
        allStar.addmPlayers(new Player("Kobe", 24, "G"));
        allStar.addmPlayers(new Player("Lebron", 6, "F"));
        allStar.addmPlayers(new Player("Harden", 13, "G"));
        allStar.addmPlayers(new Player("Curry", 30, "G"));
        allStar.addmPlayers(new Player("O'neal", 34, "C"));
        allStar.addmPlayers(new Player("Duncan", 21, "C"));
        allStar.addmPlayers(new Player("Parker", 9, "G"));
        allStar.addmPlayers(new Player("McGrady", 1, "G"));
        allStar.addmPlayers(new Player("Allen", 20, "G"));
        addTeam(allStar);
    }

    @Override
    public ArrayList<Team> getTeams() {
        return mTeams;
    }

    @Override
    public void addTeam(Team team) {
        mTeams.add(team);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void doNormalMode(int screenWidth, int screenHeight) {

    }

    @Override
    public void start() {

    }
}
