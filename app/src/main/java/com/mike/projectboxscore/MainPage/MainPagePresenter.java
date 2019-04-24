package com.mike.projectboxscore.MainPage;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainPagePresenter implements MainPageContract.Presenter {

    private static final String TAG = "MainPagePresenter";

    MainPageContract.View mView;
    ArrayList<Team> mTeams = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    FirebaseFirestore mFirebaseFirestore;
    CollectionReference mUsersCollection;

    public MainPagePresenter(MainPageContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUsersCollection = mFirebaseFirestore.collection("users");

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
        Team allStar = new Team("All-stars");

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
        Map<String, Object> data1 = new HashMap<>();

        data1.put(allStar.getmName(), allStar);
        mUsersCollection.document(mCurrentUser.getUid()).collection("teams").document(allStar.getmName()).set(allStar, SetOptions.merge());

    }

    @Override
    public void demoLoginView() {
        mView.demoLoginViewUi();
    }

    @Override
    public void checkFirebaseData() {
        ArrayList<Team> myTeams = new ArrayList<>();
        mUsersCollection.document(mCurrentUser.getUid()).collection("teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Gson gSon = new Gson();
//                        docume
//                        Object object=document.
//                        String string = document.getData().toString();
                        Team team = gSon.fromJson(document.getData().toString(), Team.class);
//                        Team team = document.toObject(Team.class);
                        myTeams.add(team);
                    }
                    mTeams = myTeams;
                    Log.d(TAG, "onComplete team arraylist: " + myTeams.get(0).getmPlayers().get(1).getName());
                    Log.d(TAG, "onComplete team arraylist size: " + myTeams.size());
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
