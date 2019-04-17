package com.mike.projectboxscore.NewTeam;

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

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;
import com.mike.projectboxscore.mainConsole.MainConsolePresenter;

import java.util.ArrayList;

public class NewTeamFragment extends Fragment implements NewTeamContract.View {

    private static final String TAG = "NewTeamFragment";

    NewTeamContract.Presenter mPresenter;
    private RecyclerView mPlayerRecyclerView;
    private EditText mTeamName;
    private ImageView mTeamLogo;
    private PlayerAdapter mPlayerAdapter;
    private ImageView mNextButton;
    private EditText mOpponent;
    private EditText mTournament;
    private MainConsolePresenter mMainConsolePresenter;

    @Override
    public void setPresenter(NewTeamContract.Presenter surfaceViewPresenter) {

    }

    public static NewTeamFragment newInstance() {
        return new NewTeamFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NewTeamPresenter(this);
        mPlayerAdapter = new PlayerAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_team, container, false);

        mPlayerRecyclerView = root.findViewById(R.id.recyclerViewNewPlayerList);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(playerLayoutManager);
        mPlayerRecyclerView.setAdapter(mPlayerAdapter);

        mTeamName = root.findViewById(R.id.editTextNewTeamName);
        mTeamLogo = root.findViewById(R.id.imageViewLogo);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private View.OnClickListener nextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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























