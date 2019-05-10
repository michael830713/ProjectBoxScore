package com.mike.projectboxscore.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.Team;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginPagePresenter implements LoginPageContract.Presenter {
    private static final String TAG = "LoginPagePresenter";
    LoginPageContract.View mView;
    FirebaseFirestore mFirebaseFirestore;

    ArrayList<Team> mTeams = new ArrayList<>();

    public LoginPagePresenter(LoginPageContract.View view) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
        mView.setPresenter(this);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
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
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {

    }
}
