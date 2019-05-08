package com.mike.projectboxscore.MainPage;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.mike.projectboxscore.CallBacks.TeamsDataCallback;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.FirebaseDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainPagePresenter implements MainPageContract.Presenter {

    private static final String TAG = "MainPagePresenter";
    private final Context mContext;

    MainPageContract.View mView;
    ArrayList<Team> mTeams = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    FirebaseFirestore mFirebaseFirestore;
    CollectionReference mUsersCollection;
    String mUserId;

    public MainPagePresenter(MainPageContract.View view, Context context) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mContext = context;
//        mAuth = FirebaseAuth.getInstance();
//        mCurrentUser = mAuth.getCurrentUser();
//        mFirebaseFirestore = FirebaseFirestore.getInstance();
//        mUsersCollection = mFirebaseFirestore.collection("users");
//        mUserId = mCurrentUser.getUid();

    }

    @Override
    public void demoNewGameView() {
        mView.demoNewGameViewUi();
    }

    @Override
    public void demoMyTeamView() {

        mView.demoMyTeamViewUi();
    }

    @Override
    public void demoLoginView() {
        mView.demoLoginViewUi();
    }

    @Override
    public void checkFirebaseData() {
        FirebaseDataSource.checkFirebaseData(mContext, teams -> mTeams = teams);

//        ArrayList<Team> myTeams = new ArrayList<>();
//        ProgressDialog pd = new ProgressDialog(mContext, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
//        pd.setMessage("Fetching data...");
//        pd.show();
//        mUsersCollection.document(mUserId).collection("teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                pd.dismiss();
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "onComplete task: " + task.getResult());
//                    if (task.getResult().isEmpty()) {
//                        Log.d(TAG, "task is empty: ");
//                    } else {
//                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                            Team test = documentSnapshot.toObject(Team.class);
//                            myTeams.add(test);
//                        }
//                        mTeams = myTeams;
//                    }
//                }
//            }
//        });
    }

    @Override
    public ArrayList<Team> getTeams() {
        return mTeams;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {

    }
}
