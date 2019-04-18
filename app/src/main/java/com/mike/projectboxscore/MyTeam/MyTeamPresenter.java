package com.mike.projectboxscore.MyTeam;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MyTeamPresenter implements MyTeamContract.Presenter {

    private static final String TAG = "MyTeamPresenter";

    MyTeamContract.View mView;
    ArrayList<Player> mTeamPlayer = new ArrayList<>();
    ArrayList<Team> mTeams;
    private Game mNewGame;

    @Override
    public void start() {

    }

    public MyTeamPresenter(MyTeamContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
//        mMyTeams = new ArrayList<>();

    }

    @Override
    public void openNewTeamFragment() {
        mView.openNewTeamFragmentUi();
    }

    @Override
    public ArrayList<Player> getTeamPlayer() {
        return mTeamPlayer;
    }

    @Override
    public void setupNewTeam(Team team) {

    }

    @Override
    public void updateData() {
        mView.updateDataUi();
    }

    @Override
    public ArrayList<Team> getTeams() {
//        return mMyTeams;
        return null;
    }

    @Override
    public void showToast(String message) {
        mView.showToastMessageUi(message);
    }
}
