package com.mike.projectboxscore.NewTeam;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialog;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialogPresenter;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsolePresenter;

import java.util.ArrayList;

public class NewTeamFragment extends Fragment implements NewTeamContract.View {

    private static final String TAG = "NewTeamFragment";

    NewTeamContract.Presenter mPresenter;
    private RecyclerView mPlayerRecyclerView;
    private EditText mTeamName;
    private ImageView mTeamLogo;
    private ImageView mButtonAddPlayer;
    private ImageView mButtonFinishCreateTeam;

    private NewPlayerAdapter mPlayerAdapter;
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
        mPlayerAdapter = new NewPlayerAdapter(mPresenter);

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
        mButtonAddPlayer = root.findViewById(R.id.imageViewAddButton);
        mButtonFinishCreateTeam = root.findViewById(R.id.imageViewNext);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonAddPlayer.setOnClickListener(newTeamOnClickListener);
        mButtonFinishCreateTeam.setOnClickListener(newTeamOnClickListener);

    }

    private View.OnClickListener newTeamOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageViewAddButton:
                    mPresenter.showNewPlayerDialog();
                    break;

                case R.id.imageViewNext:
                    mPresenter.createNewTeam();
                    break;

            }
        }
    };

    @Override
    public void showNewPlayerUi() {
        NewPlayerDialog newPlayerDialog = new NewPlayerDialog();
        mPresenter.setNewPlayer();
        NewPlayerDialogPresenter newPlayerDialogPresenter = new NewPlayerDialogPresenter(newPlayerDialog, mPresenter.getNewPlayer());
        newPlayerDialog.setPresenter(newPlayerDialogPresenter);
        FragmentManager fm = getFragmentManager();
        newPlayerDialog.show(fm, "createPlayer");
        fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
                //do sth
                if (mPresenter.getNewPlayer() != null) {
                    mPresenter.getTeamPlayer().add(mPresenter.getNewPlayer());
                    mPresenter.updateData();
                }
                fm.unregisterFragmentLifecycleCallbacks(this);
            }
        }, false);

//        fm.executePendingTransactions();
//
//        newPlayerDialog.getDialog().setOnDismissListener(dialog -> {
//
//        });
    }

    @Override
    public void updateDataUi() {
        mPlayerAdapter.updateData(mPresenter.getTeamPlayer());
    }

    @Override
    public String getTeamName() {
        return mTeamName.getText().toString();
    }

    @Override
    public void onPause() {

        mButtonAddPlayer.setOnClickListener(null);
        Log.d(TAG, "onPause: ");
        super.onPause();
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























