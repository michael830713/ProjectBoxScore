package com.mike.projectboxscore.newGame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;

public class NewGameFragment extends Fragment implements NewGameContract.View {
    NewGameContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private TeamAdapter mTeamAdapter;

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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout_new_game, container, false);

        mRecyclerView = root.findViewById(R.id.teamRecyclerView);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(playerLayoutManager);
        mRecyclerView.setAdapter(mTeamAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Team a = new Team("Lakers");
        Team b = new Team("Spurs");
        mPresenter.setupNewTeam(a);
        mPresenter.setupNewTeam(b);
        mTeamAdapter.setmTeams(mPresenter.getTeams());

    }
}























