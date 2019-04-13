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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;
import com.mike.projectboxscore.mainConsole.MainConsolePresenter;

import java.util.ArrayList;

public class NewGameFragment extends Fragment implements NewGameContract.View {

    private static final String TAG = "NewGameFragment";

    NewGameContract.Presenter mPresenter;
    private RecyclerView mTeamRecyclerView;
    private RecyclerView mPlayerRecyclerView;
    private TeamAdapter mTeamAdapter;
    private PlayerAdapter mPlayerAdapter;
    private ImageView mNextButton;
    private EditText mOpponent;
    private MainConsolePresenter mMainConsolePresenter;

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
        mPlayerAdapter = new PlayerAdapter(mPresenter);

        Team lakers = new Team("Lakers");

        lakers.addmPlayers(new PlayerStats("Mike", 23, getString(R.string.gaurd)));
        lakers.addmPlayers(new PlayerStats("Mikey", 33, getString(R.string.gaurd)));
        lakers.addmPlayers(new PlayerStats("Jack", 43, getString(R.string.forward)));
        lakers.addmPlayers(new PlayerStats("Jacky", 53, getString(R.string.forward)));
        lakers.addmPlayers(new PlayerStats("Chris", 63, getString(R.string.gaurd)));
        lakers.addmPlayers(new PlayerStats("Chrissy", 73, getString(R.string.gaurd)));
        lakers.addmPlayers(new PlayerStats("Jordan", 83, getString(R.string.forward)));
        lakers.addmPlayers(new PlayerStats("Jordanio", 93, getString(R.string.forward)));
        lakers.addmPlayers(new PlayerStats("Jerry", 24, getString(R.string.center)));
        lakers.addmPlayers(new PlayerStats("Jefferson", 37, getString(R.string.center)));
        lakers.addmPlayers(new PlayerStats("John", 43, getString(R.string.center)));

        Team spurs = new Team("Spurs");
        spurs.addmPlayers(new PlayerStats("Bieber", 37, getString(R.string.center)));
        spurs.addmPlayers(new PlayerStats("Duncan", 23, getString(R.string.gaurd)));
        spurs.addmPlayers(new PlayerStats("Parker", 33, getString(R.string.gaurd)));
        spurs.addmPlayers(new PlayerStats("James", 43, getString(R.string.forward)));
        spurs.addmPlayers(new PlayerStats("Kobe", 53, getString(R.string.forward)));
        spurs.addmPlayers(new PlayerStats("Davis", 63, getString(R.string.gaurd)));
        spurs.addmPlayers(new PlayerStats("Cousins", 73, getString(R.string.gaurd)));
        spurs.addmPlayers(new PlayerStats("Barea", 83, getString(R.string.forward)));
        spurs.addmPlayers(new PlayerStats("Fisher", 93, getString(R.string.forward)));
        spurs.addmPlayers(new PlayerStats("Harry", 24, getString(R.string.center)));
        spurs.addmPlayers(new PlayerStats("Rudy", 43, getString(R.string.center)));

        mPresenter.setupNewTeam(lakers);
        mPresenter.setupNewTeam(spurs);
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
        mOpponent = root.findViewById(R.id.editTextOpponent);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTeamAdapter.setmTeams(mPresenter.getTeams());

        Log.d(TAG, "onViewCreated again: ");

        mNextButton.setOnClickListener(nextOnClickListener);

    }

    private View.OnClickListener nextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            if (mPresenter.getmSelectedTeam() != null) {
//                int f = 0;
//                for (int i = 0; i < mPresenter.getmSelectedTeam().getmPlayers().size(); i++) {
//                    if (mPresenter.getmSelectedTeam().getmPlayers().get(i).isOnCourt()) {
//                        f += 1;
//                    }
//                }
//                if (f < 5) {
//                    mPresenter.showToast("please select 5 Starters");
//                } else {
            mPresenter.openMainConsole();
//                }
//            } else {
//                mPresenter.showToast("please select a team");
//            }
        }
    };

    @Override
    public void openMainConsoleUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainConsoleFragment fragment = MainConsoleFragment.newInstance();
        Log.d(TAG, "openMainConsoleUi: " + mPresenter.getmSelectedTeam().getmPlayers());
        mMainConsolePresenter = new MainConsolePresenter(fragment, mPresenter.getmSelectedTeam().getmPlayers());
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void showPlayersOnTeamUi(ArrayList<PlayerStats> playerStats) {
        mPlayerAdapter.setPlayers(playerStats);
    }

    @Override
    public void showToastMessageUi(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}























