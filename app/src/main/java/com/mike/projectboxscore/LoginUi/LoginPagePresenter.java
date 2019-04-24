package com.mike.projectboxscore.LoginUi;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginPagePresenter implements LoginPageContract.Presenter {
    private static final String TAG = "LoginPagePresenter";
    LoginPageContract.View mView;
    FirebaseFirestore mFirebaseFirestore;

    ArrayList<Team> mTeams = new ArrayList<>();

    public LoginPagePresenter(LoginPageContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
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
    public void setSampleTeam() {
        Team allStar = new Team("All-star");

        allStar.addmPlayers(new Player("Jordan", 23, "G"));
        allStar.addmPlayers(new Player("Pippen", 4, "F"));
        allStar.addmPlayers(new Player("Kobe", 24, "G"));
        allStar.addmPlayers(new Player("Lebron", 6, "F"));
        allStar.addmPlayers(new Player("Harden", 13, "G"));
        allStar.addmPlayers(new Player("Curry", 30, "G"));
        allStar.addmPlayers(new Player("O'neal", 34, "C"));
        allStar.addmPlayers(new Player("Duncan", 21, "C"));
        allStar.addmPlayers(new Player("Parker", 9, "G"));
        allStar.addmPlayers(new Player("McGrady", 1, "G"));
        allStar.addmPlayers(new Player("Allen", 20, "G"));
        addTeam(allStar);
    }

    @Override
    public void setupGoogleSignIn() {

        mView.setupGoogleSignInUi();
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        mView.firebaseAuthWithGoogleUi(account);

    }

    @Override
    public void demoMainPage() {
        mView.demoMainPageUi();
    }

    @Override
    public void googleSignIn() {
        mView.googleSignInUi();
    }

    @Override
    public void getFireStoreData(FirebaseUser account, FirebaseDataCallback callback) {

        Map<String, Object> data = new HashMap<>();
        data.put("capital", true);

        mFirebaseFirestore.collection("users").document(account.getUid())
                .set(data, SetOptions.merge());

        DocumentReference docRef = mFirebaseFirestore.collection("users").document(account.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        callback.firebaseDataCallBack(document);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    } else {
                        Map<String, Object> data = new HashMap<>();

                        callback.firebaseDataCallBack(null);
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public ArrayList<Team> getTeams() {
        return mTeams;
    }

    @Override
    public void addTeam(Team team) {
        mTeams.add(team);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void doNormalMode(int screenWidth, int screenHeight) {

    }

    @Override
    public void start() {

    }
}
