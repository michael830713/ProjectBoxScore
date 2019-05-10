package com.mike.projectboxscore.teams;

import android.util.Log;

import com.mike.projectboxscore.callback.GamesDataCallback;
import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.datas.Team;
import com.mike.projectboxscore.FirebaseDataSource;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MyTeamPresenter implements MyTeamContract.Presenter {

    private static final String TAG = "MyTeamPresenter";

    MyTeamContract.View mView;
    ArrayList<Team> mTeams;
    ArrayList<Game> mGames = new ArrayList<>();
    Player mNewPlayer;
    Team mSelectedTeam;

    @Override
    public void start() {

    }

    public MyTeamPresenter(MyTeamContract.View view, ArrayList<Team> teams) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mTeams = teams;

    }

    @Override
    public void setNewPlayer(String name, String email, String onCourtPosition, int backNumber, Team selectedTeam) {
        mSelectedTeam = selectedTeam;
        mNewPlayer = new Player(name, email, backNumber, onCourtPosition);
        Log.d(TAG, "updatePlayerToFirebase before: " + selectedTeam.getmPlayers().size());
        selectedTeam.addmPlayers(mNewPlayer);
        updatePlayerToFirebase(selectedTeam);
    }

    @Override
    public void updatePlayerToFirebase(Team team) {
        Log.d(TAG, "updatePlayerToFirebase after: " + team.getmPlayers().size());
        FirebaseDataSource.updateTeamInfo(team);
    }

    @Override
    public Player getNewPlayer() {
        return mNewPlayer;
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
    public void openBoxScore(Game game) {
        mView.openBoxScoreUi(game);
    }

    @Override
    public void loadGameData(int i, GamesDataCallback callback) {
        FirebaseDataSource.loadGameData(i, games -> {
            mGames = games;
            callback.loadGameCallBack(mGames);
        });

    }

    @Override
    public ArrayList<Game> getGames() {
        return mGames;
    }

    @Override
    public void updateTeamData() {
        mView.updateTeamDataUi();
    }

    @Override
    public ArrayList<Team> getTeams() {
        return mTeams;
    }

}
