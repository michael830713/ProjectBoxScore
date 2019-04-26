package com.mike.projectboxscore.MyTeam;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
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
    ArrayList<Game> mGames = new ArrayList<>();
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
        Log.d(TAG, "updatePlayerToFirebase before: " + selectedTeam.getmPlayers().size());
        selectedTeam.addmPlayers(mNewPlayer);
        updatePlayerToFirebase(selectedTeam);
    }

    @Override
    public void updatePlayerToFirebase(Team team) {
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("mPlayers", team);
        Log.d(TAG, "updatePlayerToFirebase after: " + team.getmPlayers().size());
        team.getmPlayers().size();
        mUsersCollection.document(mUserId).collection("teams").document(team.getName()).set(team, SetOptions.merge());
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
    public void loadGameData(int i,GamesDataCallback callback) {
        ArrayList<Game> games = new ArrayList<>();
        mUsersCollection.document(mUserId)
                .collection("teams").document(mTeams.get(i).getName())
                .collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Game test= document.toObject(Game.class);


//                        Gson gSon = new Gson();
//                        Game game = gSon.fromJson(document.getData().toString(), Game.class);

                        games.add(test);

                    }
                    mGames = games;
                    callback.loadGameCallBack(mGames);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
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
    public void updateGameData() {
        mView.upDateGameDataUi();
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
