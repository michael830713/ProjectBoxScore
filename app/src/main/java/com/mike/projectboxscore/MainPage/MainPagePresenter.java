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
import com.mike.projectboxscore.Constants;
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

    public MainPagePresenter(MainPageContract.View view, Context context) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
        mView.setPresenter(this);
        mContext = context;

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
