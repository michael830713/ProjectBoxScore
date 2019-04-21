package com.mike.projectboxscore.LoginUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mike.projectboxscore.MainPage.MainPageContract;
import com.mike.projectboxscore.MainPage.MainPageFragment;
import com.mike.projectboxscore.MainPage.MainPagePresenter;
import com.mike.projectboxscore.MyTeam.MyTeamFragment;
import com.mike.projectboxscore.MyTeam.MyTeamPresenter;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.newGame.NewGameFragment;
import com.mike.projectboxscore.newGame.NewGamePresenter;

import static android.app.Activity.RESULT_CANCELED;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginPageFragment extends Fragment implements LoginPageContract.View, View.OnClickListener {

    private static final String TAG = "LoginPageFragment";
    private static final int RC_SIGN_IN = 500;

    MainPageContract.Presenter mPresenter;
    private Button mLoginButton;
    private Button mMyTeamButton;
    SignInButton mSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    private MyTeamPresenter mMyTeamPresenter;
    private NewGamePresenter mNewGamePresenter;
    private MainPagePresenter mMainPagePresenter;
    private View mView;

    public LoginPageFragment() {
        // Requires empty public constructor
    }

    public static LoginPageFragment newInstance() {
        return new LoginPageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_google_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = (GoogleSignInClient) GoogleSignIn.getClient(getActivity(), gso);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "email: " + account.getEmail());
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    // ...
                }
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(mView, "Login Failed.", Snackbar.LENGTH_SHORT).show();
//                        updateUI(null);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mView = root;
        mSignInButton = root.findViewById(R.id.sign_in_button);
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSignInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        signIn();
    }

    private void updateUI(FirebaseUser account) {
        String name = account.getDisplayName();
        Snackbar.make(mView, "Welcome " + name + "!", Snackbar.LENGTH_LONG).show();
        demoMainPageUi();
    }

    public void demoMainPageUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainPageFragment fragment = MainPageFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "MainPage");
//        fragmentTransaction.replace(R.id.container, fragment, "MainPage").addToBackStack("MainPage");
        fragmentTransaction.commit();
        mMainPagePresenter = new MainPagePresenter(fragment);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void demoConsoleView() {

    }

    @Override
    public void demoNewGameViewUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        NewGameFragment fragment = NewGameFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();
        mNewGamePresenter = new NewGamePresenter(fragment, mPresenter.getTeams());
    }

    @Override
    public void demoMyTeamViewUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MyTeamFragment fragment = MyTeamFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();
        mMyTeamPresenter = new MyTeamPresenter(fragment, mPresenter.getTeams());
    }

    @Override
    public void demoNewTeamUi() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setPresenter(LoginPageContract.Presenter surfaceViewPresenter) {

    }
}
