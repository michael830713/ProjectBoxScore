package com.mike.projectboxscore.newGame;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.RecyclerViewCornerRadius;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;
import com.mike.projectboxscore.mainConsole.MainConsolePresenter;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

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
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    public static NewGameFragment newInstance() {
        return new NewGameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTeamAdapter = new TeamAdapter(mPresenter);
        mPlayerAdapter = new PlayerAdapter(mPresenter,getActivity());

//        Team allStar = new Team("All-star");
//
//        allStar.addmPlayers(new Player("Jordan", 23, "G"));
//        allStar.addmPlayers(new Player("Pippen", 4, "F"));
//        allStar.addmPlayers(new Player("Kobe", 24, "G"));
//        allStar.addmPlayers(new Player("Lebron", 6, "F"));
//        allStar.addmPlayers(new Player("Harden", 13, "G"));
//        allStar.addmPlayers(new Player("Curry", 30, "G"));
//        allStar.addmPlayers(new Player("O'neal", 34, "C"));
//        allStar.addmPlayers(new Player("Duncan", 21, "C"));
//        allStar.addmPlayers(new Player("Parker", 9, "G"));
//        allStar.addmPlayers(new Player("McGrady", 1, "G"));
//        allStar.addmPlayers(new Player("Allen", 20, "G"));
//
//        Team clippers = new Team("Clippers");
//        clippers.addmPlayers(new Player("Alex", 2, "G"));
//        clippers.addmPlayers(new Player("Gallinari", 8, "F"));
//        clippers.addmPlayers(new Player("Zubac", 40, "C"));
//        clippers.addmPlayers(new Player("Shamet", 20, "G"));
//        clippers.addmPlayers(new Player("Beverley", 21, "G"));
//        clippers.addmPlayers(new Player("Harrell", 5, "F"));
//        clippers.addmPlayers(new Player("Williams", 23, "G"));
//        clippers.addmPlayers(new Player("Green", 4, "F"));
//        clippers.addmPlayers(new Player("Chandler", 22, "F"));
//        clippers.addmPlayers(new Player("Temple", 17, "G"));
//
//        mPresenter.setupNewTeam(allStar);
//        mPresenter.setupNewTeam(clippers);
        Log.d(TAG, "onCreate: ");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_game, container, false);

        mTeamRecyclerView = root.findViewById(R.id.teamRecyclerView);
        LinearLayoutManager teamLayoutManager = new LinearLayoutManager(getContext());
        mTeamRecyclerView.setLayoutManager(teamLayoutManager);
        mTeamRecyclerView.setAdapter(mTeamAdapter);

        mPlayerRecyclerView = root.findViewById(R.id.playerRecyclerView);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(playerLayoutManager);
        mPlayerRecyclerView.setAdapter(mPlayerAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_grey);

        mPlayerRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

//        RecyclerViewCornerRadius radiusItemDecoration = new RecyclerViewCornerRadius(mPlayerRecyclerView);
////        radiusItemDecoration.setCornerRadius(10);
////        mPlayerRecyclerView.addItemDecoration(radiusItemDecoration);

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

                } else if (opponent.isEmpty() || tournament.isEmpty()) {
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

        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack("MainConsole");
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























