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
    Player mNewPlayer;
    private Game mNewGame;

    @Override
    public void start() {

    }

    public MyTeamPresenter(MyTeamContract.View view, ArrayList<Team> teams) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mTeams = teams;

    }

    @Override
    public void showNewPlayer() {
        mView.showNewPlayerUi();
    }

    @Override
    public void setNewPlayer(String name, String email, String onCourtPosition, int backNumber, Team selectedTeam) {
        mNewPlayer = new Player(name, email, backNumber, onCourtPosition);
        selectedTeam.addmPlayers(mNewPlayer);
    }

    @Override
    public Player getNewPlayer() {
        return mNewPlayer;
    }

    @Override
    public void showEditPlayer(Player player) {
        mView.showEditPlayerUi(player);
    }

    @Override
    public void showEditTeam() {
mView.showEditTeamUi();
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
    public void openBoxScore(Game game) {
        mView.openBoxScoreUi(game);
    }

    @Override
    public void updateData() {
        mView.updateDataUi();
    }

    @Override
    public ArrayList<Team> getTeams() {
//        return mMyTeams;
        return mTeams;
    }

    @Override
    public void showToast(String message) {
        mView.showToastMessageUi(message);
    }
}
