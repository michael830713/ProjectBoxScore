package com.mike.projectboxscore.newgame;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.console.MainConsoleFragment;
import com.mike.projectboxscore.console.MainConsolePresenter;

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
        mPlayerAdapter = new PlayerAdapter(mPresenter, getActivity());

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

        mNextButton = root.findViewById(R.id.imageViewNext);
        mOpponent = root.findViewById(R.id.editTextOpponent);
        mTournament = root.findViewById(R.id.editTextTournamnet);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTeamAdapter.setmTeams(mPresenter.getTeams());

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
                    mPresenter.showToast(getActivity().getString(R.string.select_5_player));

                } else if (opponent.isEmpty() || tournament.isEmpty()) {
                    mPresenter.showToast(getActivity().getString(R.string.enter_opponent_toast));
                } else {
                    mPresenter.setupGameData();
                    mPresenter.openMainConsole();
                }

            } else {
                mPresenter.showToast(getActivity().getString(R.string.select_team_toast));
            }
        }
    };

    @Override
    public void openMainConsoleUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainConsoleFragment fragment = MainConsoleFragment.newInstance();

        mMainConsolePresenter = new MainConsolePresenter(fragment, mPresenter.getmNewGame());
        fragment.setPresenter(mMainConsolePresenter);
        fragmentTransaction.replace(R.id.container, fragment, Constants.SURFACE).addToBackStack(Constants.FRAGMENT_MAIN_CONSOLE);
        fragmentTransaction.commit();
    }

    @Override
    public void getGameDataAndSetNewGame() {
        String opponent = mOpponent.getText().toString();
        String tournament = mTournament.getText().toString();
        if (mOpponent.getText().equals("") || mTournament.getText().equals("")) {
            mPresenter.showToast(getActivity().getString(R.string.enter_opponent_toast));
        } else {

            mPresenter.setNewGame(opponent, tournament);
            mPresenter.setPlayerStats();
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























