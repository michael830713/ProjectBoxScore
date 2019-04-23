package com.mike.projectboxscore.LoginUi;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.MainPage.MainPageContract;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginPagePresenter implements LoginPageContract.Presenter {
    private static final String TAG = "LoginPagePresenter";
    LoginPageContract.View mView;

    ArrayList<Team> mTeams = new ArrayList<>();

    public LoginPagePresenter(LoginPageContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);

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
