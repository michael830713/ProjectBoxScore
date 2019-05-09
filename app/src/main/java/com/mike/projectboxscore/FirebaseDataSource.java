package com.mike.projectboxscore;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mike.projectboxscore.CallBacks.TeamsDataCallback;
import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.TeamMain.GamesDataCallback;
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.PlayerAvatarUploadCallback;

import java.util.ArrayList;

public class FirebaseDataSource {
    private static final String TAG = "FirebaseDataSource";

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser mCurrentUser = mAuth.getCurrentUser();
    private static FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private static CollectionReference mUsersCollection = mFirebaseFirestore.collection("users");
    private static String mUserId = mCurrentUser.getUid();
    private static DocumentReference documentReference = mUsersCollection.document(mUserId);
    private static CollectionReference teamCollectionReference = documentReference.collection("teams");
    private static StorageReference mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
    private static ArrayList<Team> mTeams = new ArrayList<>();

    public static void checkFirebaseData(Context context, TeamsDataCallback teamsDataCallback) {
        ArrayList<Team> myTeams = new ArrayList<>();
        ProgressDialog pd = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        pd.setMessage("Fetching data...");

        pd.show();
        Log.d(TAG, "mUserId: " + mUserId);
        Log.d(TAG, "documentReference: " + documentReference);
        teamCollectionReference.get().addOnCompleteListener(task -> {
            pd.dismiss();
            if (task.isSuccessful()) {
                Log.d(TAG, "onComplete task: " + task.getResult());
                if (task.getResult().isEmpty()) {
                    Log.d(TAG, "task is empty: ");
                } else {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Team test = documentSnapshot.toObject(Team.class);
                        Log.d(TAG, "test: " + test);
                        myTeams.add(test);
                        Log.d(TAG, "myteam size: " + myTeams.size());
                    }
                    mTeams = myTeams;
                    teamsDataCallback.teamsCallback(mTeams);
                }
            }
        });
        Log.d(TAG, "mTeams: " + mTeams.size());
    }

    public static void updateTeamInfo(Team team) {
        teamCollectionReference.document(team.getName()).set(team, SetOptions.merge());
    }

    public static void loadGameData(int i, GamesDataCallback callback) {
        ArrayList<Game> games = new ArrayList<>();
        teamCollectionReference.document(mTeams.get(i).getName())
                .collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Game test = document.toObject(Game.class);
                        games.add(test);
                    }

                    callback.loadGameCallBack(games);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public static void uploadTeamLogoFile(Context context, Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback) {
        if (imageUri != null) {
            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                    + "." + fileExtention);

            ProgressDialog pd = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
            pd.setMessage("uploading image...");
            pd.show();

            StorageTask mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(context, "Upload successful", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pd.dismiss();
                                    Log.d(TAG, "upload URL: " + uri);
                                    callback.loadGameCallBack(uri.toString());
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}
