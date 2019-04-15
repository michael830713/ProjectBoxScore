package com.mike.projectboxscore.newGame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mike.projectboxscore.Data.Player;
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
    private EditText mTournament;
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

        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));
        lakers.addmPlayers(new Player("Jordan", 23, "G"));

        Team spurs = new Team("Spurs");
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));
        spurs.addmPlayers(new Player("Duncan", 21, "C"));

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
        mTournament = root.findViewById(R.id.editTextTournamnet);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTeamAdapter.setmTeams(mPresenter.getTeams());

        Log.d(TAG, "onViewCreated again: ");

        Log.d(TAG, "onViewCreated: ");

        mNextButton.setOnClickListener(nextOnClickListener);

    }

    private View.OnClickListener nextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String opponent = mOpponent.getText().toString();
            String tournament = mTournament.getText().toString();

            if (mPresenter.getmSelectedTeam() != null) {

                int f = 0;
                for (int i = 0; i < mPresenter.getmSelectedTeam().getmPlayers().size(); i++) {
                    if (mPresenter.getmSelectedTeam().getmPlayers().get(i).isOnCourt()) {
                        f += 1;
                    }
                }
                if (f < 5) {
                    mPresenter.showToast("please select 5 Starters");

                } else if (opponent.isEmpty()||tournament.isEmpty()) {
                    mPresenter.showToast("Enter Opponent and Tournament!");
                } else {
                    mPresenter.setupGameData();
                    mPresenter.openMainConsole();
                }

            } else {
                mPresenter.showToast("please select a team");
            }
        }
    };

    @Override
    public void openMainConsoleUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainConsoleFragment fragment = MainConsoleFragment.newInstance();
        Log.d(TAG, "newGame: " + mPresenter.getmNewGame());
        mMainConsolePresenter = new MainConsolePresenter(fragment, mPresenter.getmNewGame());
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void getGameDataAndSetNewGame() {
        String opponent = mOpponent.getText().toString();
        String tournament = mTournament.getText().toString();
        Log.d(TAG, "opponent: " + opponent);
        Log.d(TAG, "tournament: " + tournament);
        if (mOpponent.getText().equals("") || mTournament.getText().equals("")) {
            mPresenter.showToast("Enter Opponent and Tournament!");
        } else {
            Log.d(TAG, "setNewGame is running: ");
            mPresenter.setNewGame(opponent, tournament);
        }
    }

    @Override
    public void showPlayersOnTeamUi(ArrayList<Player> playerStats) {
        mPlayerAdapter.setPlayers(playerStats);
    }

    @Override
    public void showToastMessageUi(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}























