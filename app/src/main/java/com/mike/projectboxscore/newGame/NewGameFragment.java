package com.mike.projectboxscore.newGame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;

import java.util.ArrayList;

public class NewGameFragment extends Fragment implements NewGameContract.View {

    private static final String TAG = "NewGameFragment";

    NewGameContract.Presenter mPresenter;
    private RecyclerView mTeamRecyclerView;
    private RecyclerView mPlayerRecyclerView;
    private TeamAdapter mTeamAdapter;
    private PlayerAdapter mPlayerAdapter;
    private ImageView mNextButton;

    @Override
    public void setPresenter(NewGameContract.Presenter surfaceViewPresenter) {

    }

    public static NewGameFragment newInstance() {
        return new NewGameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NewGamePresenter(this);
        mTeamAdapter = new TeamAdapter(mPresenter);
        mPlayerAdapter=new PlayerAdapter(mPresenter);

        Team a = new Team("Lakers");
        PlayerStats playerStats = new PlayerStats("Mike", 23, getString(R.string.gaurd));
        PlayerStats playerStats1 = new PlayerStats("Mikey", 33, getString(R.string.gaurd));
        PlayerStats playerStats2 = new PlayerStats("Jack", 43, getString(R.string.forward));
        PlayerStats playerStats3 = new PlayerStats("Jacky", 53, getString(R.string.forward));
        PlayerStats playerStats4 = new PlayerStats("Chris", 63, getString(R.string.gaurd));
        PlayerStats playerStats5 = new PlayerStats("Chrissy", 73, getString(R.string.gaurd));
        PlayerStats playerStats6 = new PlayerStats("Jordan", 83, getString(R.string.forward));
        PlayerStats playerStats7 = new PlayerStats("Jordanio", 93, getString(R.string.forward));
        PlayerStats playerStats8 = new PlayerStats("Jerry", 24, getString(R.string.center));
        PlayerStats playerStats9 = new PlayerStats("Jefferson", 37, getString(R.string.center));
        PlayerStats playerStats10 = new PlayerStats("John", 43, getString(R.string.center));

       a.addmPlayers(playerStats);
       a.addmPlayers(playerStats1);
       a.addmPlayers(playerStats2);
       a.addmPlayers(playerStats3);
       a.addmPlayers(playerStats4);
       a.addmPlayers(playerStats5);
       a.addmPlayers(playerStats6);
       a.addmPlayers(playerStats7);
       a.addmPlayers(playerStats8);
       a.addmPlayers(playerStats9);
       a.addmPlayers(playerStats10);


        Team b = new Team("Spurs");
        mPresenter.setupNewTeam(a);
        mPresenter.setupNewTeam(b);
        Log.d(TAG, "onCreate: ");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout_new_game, container, false);

        mTeamRecyclerView = root.findViewById(R.id.teamRecyclerView);
        LinearLayoutManager teamLayoutManager = new LinearLayoutManager(getContext());
        mTeamRecyclerView.setLayoutManager(teamLayoutManager);
        mTeamRecyclerView.setAdapter(mTeamAdapter);

        mPlayerRecyclerView = root.findViewById(R.id.playerRecyclerView);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(playerLayoutManager);
        mPlayerRecyclerView.setAdapter(mPlayerAdapter);

        mNextButton = root.findViewById(R.id.imageViewNext);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTeamAdapter.setmTeams(mPresenter.getTeams());

        Log.d(TAG, "onViewCreated again: ");

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openMainConsole();
            }
        });

    }

    @Override
    public void openMainConsoleUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainConsoleFragment fragment = MainConsoleFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();
    }



    @Override
    public void showPlayersOnTeamUi(ArrayList<PlayerStats> playerStats) {
        mPlayerAdapter.setPlayers(playerStats);
    }
}























