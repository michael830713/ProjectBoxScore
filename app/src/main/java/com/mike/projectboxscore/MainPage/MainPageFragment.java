package com.mike.projectboxscore.MainPage;

import android.support.v4.app.Fragment;
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
import com.mike.projectboxscore.MyTeam.MyTeamFragment;
import com.mike.projectboxscore.MyTeam.MyTeamPresenter;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.newGame.NewGameFragment;
import com.mike.projectboxscore.newGame.NewGamePresenter;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainPageFragment extends Fragment implements MainPageContract.View {

    private static final String TAG = "MainPageFragment";

    MainPageContract.Presenter mPresenter;
    private Button mLoginButton;
    private Button mMyTeamButton;

    private MyTeamPresenter mMyTeamPresenter;
    private NewGamePresenter mNewGamePresenter;

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
        mPresenter.setSampleTeam();
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mLoginButton = root.findViewById(R.id.button_new_game);
        mMyTeamButton = root.findViewById(R.id.button_my_team);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoginButton.setOnClickListener(v -> mPresenter.demoNewGameView());
        mMyTeamButton.setOnClickListener(v -> mPresenter.demoMyTeamView());

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
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        NewTeamFragment fragment = NewTeamFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void initView() {

    }
}
