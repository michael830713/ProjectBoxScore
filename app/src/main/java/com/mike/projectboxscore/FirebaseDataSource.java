package com.mike.projectboxscore;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mike.projectboxscore.CallBacks.TeamsDataCallback;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;

public class FirebaseDataSource {
    private static final String TAG = "FirebaseDataSource";

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser mCurrentUser = mAuth.getCurrentUser();
    private static FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private static CollectionReference mUsersCollection = mFirebaseFirestore.collection("users");
    private static String mUserId = mCurrentUser.getUid();
    private static DocumentReference documentReference = mUsersCollection.document(mUserId);
    private static ArrayList<Team> mTeams = new ArrayList<>();

    public static void checkFirebaseData(Context context, TeamsDataCallback teamsDataCallback) {
        ArrayList<Team> myTeams = new ArrayList<>();
        ProgressDialog pd = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        pd.setMessage("Fetching data...");
        pd.show();
        Log.d(TAG, "mUserId: " + mUserId);
        Log.d(TAG, "documentReference: " + documentReference);
        documentReference.collection("teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
            }
        });
        Log.d(TAG, "mTeams: " + mTeams.size());
    }

}
