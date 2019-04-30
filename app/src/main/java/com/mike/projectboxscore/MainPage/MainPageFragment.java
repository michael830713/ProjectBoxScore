package com.mike.projectboxscore.MainPage;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.mike.projectboxscore.LoginUi.LoginPageFragment;
import com.mike.projectboxscore.LoginUi.LoginPagePresenter;
import com.mike.projectboxscore.MyTeam.MyTeamFragment;
import com.mike.projectboxscore.MyTeam.MyTeamPresenter;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.newGame.NewGameFragment;
import com.mike.projectboxscore.newGame.NewGamePresenter;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainPageFragment extends Fragment implements MainPageContract.View, FirebaseAuth.AuthStateListener {

    private static final String TAG = "MainPageFragment";
    private static final int PICK_IMAGE_REQUEST = 10;

    MainPageContract.Presenter mPresenter;
    private Button mNewGameButton;
    private Button mMyTeamButton;
    private Button mSignOutButton;
    private Button mOpenGalleryButton;
    FirebaseAuth mFirebaseAuth;
    private Activity mActivity;

    LoginPageFragment mLoginFragment;

    private MyTeamPresenter mMyTeamPresenter;
    private NewGamePresenter mNewGamePresenter;
    private LoginPagePresenter mLoginPresenter;

    public MainPageFragment() {
        // Requires empty public constructor
    }

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    public void setPresenter(MainPageContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter.setSampleTeam();

        // TODO: 2019-04-30 there is a crash here nullPointer
        mPresenter.checkFirebaseData();
//        mActivity = getActivity();
        mFirebaseAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mNewGameButton = root.findViewById(R.id.button_new_game);
        mMyTeamButton = root.findViewById(R.id.button_my_team);
        mSignOutButton = root.findViewById(R.id.buttonSignOut);
//        mOpenGalleryButton = root.findViewById(R.id.buttonOpenGallery);
        mActivity = getActivity();
        Log.d(TAG, "onCreateView getActivity: " + getActivity());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNewGameButton.setOnClickListener(v -> mPresenter.demoNewGameView());
        mMyTeamButton.setOnClickListener(v -> mPresenter.demoMyTeamView());
        mSignOutButton.setOnClickListener(v -> {
            mFirebaseAuth.signOut();
            mPresenter.demoLoginView();
        });
//        mOpenGalleryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, PICK_IMAGE_REQUEST);
//            }
//        });

        mFirebaseAuth.addAuthStateListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
//            mImageUri = data.getData();
            Log.d(TAG, "onActivityResult: " + data.getData());
//            Picasso.with(this).load(mImageUri).into(mImageView);
        }

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
    }

    @Override
    public void demoLoginViewUi() {
        FragmentManager fragmentManager = getFragmentManager();
        Log.d(TAG, "getFragmentManager: " + getFragmentManager());
        Log.d(TAG, "demoLoginViewUi the activity: " + getActivity());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getFragmentManager().findFragmentByTag("login") == null) {
            mLoginFragment = LoginPageFragment.newInstance();
            fragmentTransaction.replace(R.id.container, mLoginFragment, "login");
        } else {
            fragmentTransaction.show(mLoginFragment);
        }

        fragmentTransaction.commit();
        mLoginPresenter = new LoginPagePresenter(mLoginFragment);
    }

    public FragmentManager getHostFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm == null) {
            fm = getActivity().getSupportFragmentManager();
        }
        return fm;
    }

    @Override
    public void demoNewGameViewUi() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        NewGameFragment fragment = NewGameFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack("NewGame");
        fragmentTransaction.commit();
        mNewGamePresenter = new NewGamePresenter(fragment, mPresenter.getTeams());
    }

    @Override
    public void demoMyTeamViewUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MyTeamFragment fragment = MyTeamFragment.newInstance();
        mMyTeamPresenter = new MyTeamPresenter(fragment, mPresenter.getTeams());
        fragment.setPresenter(mMyTeamPresenter);
        fragmentTransaction.replace(R.id.container, fragment, "MyTeam").addToBackStack("MyTeam");
        fragmentTransaction.commit();
    }

    @Override
    public void demoMainPageUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        NewTeamFragment fragment = NewTeamFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void initView() {

    }
}
