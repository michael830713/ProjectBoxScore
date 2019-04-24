package com.mike.projectboxscore.MyTeam;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MyTeamPresenter implements MyTeamContract.Presenter {

    private static final String TAG = "MyTeamPresenter";

    MyTeamContract.View mView;
    ArrayList<Player> mTeamPlayer = new ArrayList<>();
    ArrayList<Team> mTeams;
    Player mNewPlayer;
    Team mSelectedTeam;
    private Game mNewGame;
    CollectionReference mUsersCollection;
    String mUserId;

    @Override
    public void start() {

    }

    public MyTeamPresenter(MyTeamContract.View view, ArrayList<Team> teams) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mTeams = teams;
        mUsersCollection = FirebaseFirestore.getInstance().collection("users");
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public void showNewPlayer() {
        mView.showNewPlayerUi();
    }

    @Override
    public void setNewPlayer(String name, String email, String onCourtPosition, int backNumber, Team selectedTeam) {
        mSelectedTeam = selectedTeam;
        mNewPlayer = new Player(name, email, backNumber, onCourtPosition);
        Log.d(TAG, "updatePlayerToFirebase before: "+selectedTeam.getmPlayers().size());
        selectedTeam.addmPlayers(mNewPlayer);
        updatePlayerToFirebase(selectedTeam);
    }

    @Override
    public void updatePlayerToFirebase(Team team) {
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("mPlayers", team);
        Log.d(TAG, "updatePlayerToFirebase after: "+team.getmPlayers().size());
        team.getmPlayers().size();
        mUsersCollection.document(mUserId).collection("teams").document(team.getmName()).set(team, SetOptions.merge());
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
