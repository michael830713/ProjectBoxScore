package com.mike.projectboxscore.NewTeam;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewTeamPresenter implements NewTeamContract.Presenter {

    private static final String TAG = "NewTeamPresenter";

    NewTeamContract.View mView;
    private ArrayList<Team> mMyTeams;
    private Team mSelectedTeam;
    ArrayList<Player> mTeamPlayer = new ArrayList<>();
    Player mNewPlayer;
    private Game mNewGame;
    CollectionReference mUsersCollection;
    String mUserId;

    @Override
    public void start() {

    }

    public NewTeamPresenter(NewTeamContract.View view, ArrayList<Team> teams) {
        mView = checkNotNull(view, "view cannot be null!");
        mMyTeams = teams;
        mView.setPresenter(this);
        mUsersCollection = FirebaseFirestore.getInstance().collection("users");
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        mMyTeams = new ArrayList<>();

    }

    @Override
    public void createNewTeam() {
        String teamName = mView.getTeamName();
        if (teamName.isEmpty()) {
            showToast("Please enter team name!");
        } else {
            Team team = new Team(teamName);
            team.setmPlayers(mTeamPlayer);
            mMyTeams.add(team);
            addTeamToFirebase(team);

        }

    }

    @Override
    public void setNewPlayer(String name, String email, String onCourtPosition, int backNumber, String imageUrl) {
        Player player;
        if (imageUrl != null) {
            player = new Player(name, email, backNumber, onCourtPosition, imageUrl);
        } else {
            player = new Player(name, email, backNumber, onCourtPosition);
        }
        mNewPlayer = player;
    }

    @Override
    public Player getNewPlayer() {
        return mNewPlayer;
    }

    @Override
    public ArrayList<Player> getTeamPlayer() {
        return mTeamPlayer;
    }

    @Override
    public void showNewPlayerDialog() {
        mView.showNewPlayerUi();
    }

    @Override
    public void setupNewTeam(Team team) {
//        mMyTeams.add(team);
    }

    @Override
    public void updateData() {
        mView.updateDataUi();
    }

    @Override
    public void addTeamToFirebase(Team team) {
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put(team.getName(), team);
        mUsersCollection.document(mUserId).collection("teams").document(team.getName()).set(team, SetOptions.merge());
    }

    @Override
    public void openMyTeamFragment() {
        mView.openMyTeamFragmentUi();
    }

    @Override
    public ArrayList<Team> getTeams() {
//        return mMyTeams;
        return mMyTeams;
    }

    public Team getmSelectedTeam() {
        return mSelectedTeam;
    }

    @Override
    public void showToast(String message) {
        mView.showToastMessageUi(message);
    }
}
