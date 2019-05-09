package com.mike.projectboxscore.TeamNew;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.FirebaseDataSource;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.PlayerAvatarUploadCallback;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewTeamPresenter implements NewTeamContract.Presenter {

    private static final String TAG = "NewTeamPresenter";
    private final Context mContext;

    NewTeamContract.View mView;
    private ArrayList<Team> mMyTeams;
    ArrayList<Player> mTeamPlayer = new ArrayList<>();
    Player mNewPlayer;


    @Override
    public void start() {

    }

    public NewTeamPresenter(Context context, NewTeamContract.View view, ArrayList<Team> teams) {
        mView = checkNotNull(view, "view cannot be null!");
        mContext = context;
        mMyTeams = teams;
        mView.setPresenter(this);

    }

    @Override
    public void createNewTeam(String imageLink) {

        String teamName = mView.getTeamName();
        if (teamName.isEmpty()) {
            showToast(mContext.getString(R.string.enter_team_name_toast));
        } else {
            Team team = new Team(teamName);
            if (imageLink != null) {
                team.setmTeamLogoUrl(imageLink);
            }
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
    public void updateData() {
        mView.updateDataUi();
    }

    @Override
    public void addTeamToFirebase(Team team) {

        FirebaseDataSource.updateTeamInfo(team);

    }

    @Override
    public void openMyTeamFragment() {
        mView.openMyTeamFragmentUi();
    }

    @Override
    public ArrayList<Team> getTeams() {
        return mMyTeams;
    }

    @Override
    public void showToast(String message) {
        mView.showToastMessageUi(message);
    }

    @Override
    public void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback) {
        FirebaseDataSource.uploadTeamLogoFile(mContext, imageUri, fileExtention, callback);

    }
}

