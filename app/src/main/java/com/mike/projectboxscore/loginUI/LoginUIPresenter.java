package com.mike.projectboxscore.loginUI;

import android.support.v4.app.FragmentTransaction;

import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginUIPresenter implements LoginUIViewContract.Presenter {

    LoginUIViewContract.View mView;
    ArrayList<Team> mTeams = new ArrayList<>();

    public LoginUIPresenter(LoginUIViewContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);

    }

    @Override
    public void demoNewGameView() {
        mView.demoNewGameViewUi();
    }

    @Override
    public void demoMyTeamView() {
        if (mTeams.size()==0){
            Team team = new Team("Spurs");
            addTeam(team);
        }

        mView.demoMyTeamViewUi();
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
