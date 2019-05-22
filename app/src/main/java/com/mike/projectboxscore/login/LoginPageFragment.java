package com.mike.projectboxscore.login;

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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.main.MainPageFragment;
import com.mike.projectboxscore.main.MainPagePresenter;
import com.mike.projectboxscore.R;

import static android.app.Activity.RESULT_CANCELED;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginPageFragment extends Fragment implements LoginPageContract.View, View.OnClickListener {

    private static final String TAG = "LoginPageFragment";
    private static final int RC_SIGN_IN = 500;

    LoginPageContract.Presenter mPresenter;
    Button mSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

// ...
// Initialize Firebase Auth

    private MainPagePresenter mMainPagePresenter;
    private View mView;

    public LoginPageFragment() {
        // Requires empty public constructor
    }

    public static LoginPageFragment newInstance() {
        return new LoginPageFragment();
    }

    @Override
    public void setPresenter(LoginPageContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mPresenter.setupGoogleSignIn();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mView = root;
        mSignInButton = root.findViewById(R.id.sign_in_button);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSignInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mPresenter.googleSignIn();

    }

    private void updateUI(FirebaseUser account) {

        String name = account.getDisplayName();
        Snackbar.make(mView, "Welcome " + name + "!", Snackbar.LENGTH_SHORT).show();

        mPresenter.demoMainPage();
    }

    @Override
    public void demoMainPageUi() {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainPageFragment fragment = MainPageFragment.newInstance();
        mMainPagePresenter = new MainPagePresenter(fragment, getActivity());
        fragment.setPresenter(mMainPagePresenter);
        fragmentTransaction.replace(R.id.container, fragment, Constants.FRAGMENT_MAIN_PAGE);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void googleSignInUi() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
                    mPresenter.firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    // ...
                }
            }
        }
    }

    @Override
    public void setupGoogleSignInUi() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_google_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = (GoogleSignInClient) GoogleSignIn.getClient(getActivity(), gso);

    }

    @Override
    public void firebaseAuthWithGoogleUi(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(mView, "Login Failed.", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

}

















