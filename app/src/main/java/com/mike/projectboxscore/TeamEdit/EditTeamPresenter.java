package com.mike.projectboxscore.TeamEdit;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.FirebaseDataSource;
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.PlayerAvatarUploadCallback;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditTeamPresenter implements EditTeamContract.Presenter {

    private static final String TAG = "EditTeamPresenter";
    private final Context mContext;

    EditTeamContract.View mView;
    private ArrayList<Team> mMyTeams;
    private Team mTeam;
    ArrayList<Player> mTeamPlayer = new ArrayList<>();
    Player mNewPlayer;
//    CollectionReference mUsersCollection;
//    String mUserId;
//
//    private StorageTask mUploadTask;
//
//    private StorageReference mStorageReference;

    @Override
    public void start() {

    }

    public EditTeamPresenter(Context context, EditTeamContract.View view, Team team, ArrayList<Team> myTeams) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
        mTeam = team;
        mContext = context;
        mMyTeams = myTeams;
        Log.d(TAG, "Team: " + mTeam);
//        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        mTeamPlayer = mTeam.getmPlayers();
        mView.setPresenter(this);
//        mUsersCollection = FirebaseFirestore.getInstance().collection("users");
//        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public void showEditPlayerDialog(Player player) {
        mView.showEditPlayerUi(player);
    }

    @Override
    public Team getTeam() {
        return mTeam;
    }

    @Override
    public void setNewPlayer(String name, String onCourtPosition, int backNumber, String imageUrl) {
        Player player;
        if (imageUrl != null) {
            player = new Player(name, backNumber, onCourtPosition, imageUrl);
        } else {
            player = new Player(name, backNumber, onCourtPosition);
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
    public void showConfirmDeleteDialog(boolean isPlayer) {
        mView.showConfirmDeleteDialogUi(isPlayer);
    }

    @Override
    public void updateFirebaseData() {
//        mUsersCollection.document(mUserId).collection("teams").document(mTeam.getName()).set(mTeam, SetOptions.merge());

        FirebaseDataSource.updateTeamInfo(mTeam);

        Log.d(TAG, "updateFirebaseData image Url: " + mTeam.getmPlayers().get(0).getImageUrl());
        Log.d(TAG, "updateFirebaseData player name: " + mTeam.getmPlayers().get(0).getName());

    }

    @Override
    public void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback) {
        if (imageUri != null) {

            FirebaseDataSource.uploadTeamLogoFile(mContext, imageUri, fileExtention, callback);

        } else {
            Toast.makeText(mContext, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteTeamFromFirebase() {

        FirebaseDataSource.deleteTeam(mTeam);

//        mUsersCollection.document(mUserId).collection("teams").document(mTeam.getName()).delete();

    }

    @Override
    public void updateTeamInfo(String teamName, String imageLink) {
        mTeam.setName(teamName);
        if (imageLink != null) {
            mTeam.setmTeamLogoUrl(imageLink);
        }
        FirebaseDataSource.updateTeamInfo(mTeam);

    }

    public void updateTeamToFirebase(Team team) {
//        mUsersCollection.document(mUserId).collection("teams").document(team.getName()).set(team, SetOptions.merge());
    }

    @Override
    public void updateData() {
        mView.updateDataUi();
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
}
