package com.mike.projectboxscore.MyTeam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
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

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MyTeamFragment extends Fragment implements MyTeamContract.View {

    private static final String TAG = "MyTeamFragment";

    private static final int TARGET_FRAGMENT_REQUEST_CODE = 1;
    private static final String NEW_PLAYER_NAME = "playerMessage";
    private static final String NEW_PLAYER_EMAIL = "playerEmail";
    private static final String NEW_PLAYER_ONCOURT_POSITION = "playerOnCourtPosition";
    private static final String NEW_PLAYER_BACK_NUMBER = "playerBackNumber";

    MyTeamContract.Presenter mPresenter;
    private RecyclerView mTeamRecyclerView;
    private EditText mTeamName;
    private ImageView mTeamLogo;
    private ImageView mButtonAddPlayer;
    private ImageView mButtonFinishCreateTeam;

    private MyTeamAdapter mPlayerAdapter;

    @Override
    public void setPresenter(MyTeamContract.Presenter surfaceViewPresenter) {

    }

    public static MyTeamFragment newInstance() {
        return new MyTeamFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MyTeamPresenter(this);
        mPlayerAdapter = new MyTeamAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_myteam, container, false);

        mTeamRecyclerView = root.findViewById(R.id.recyclerViewTeam);
        LinearLayoutManager teamLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mTeamRecyclerView.setLayoutManager(teamLayoutManager);
        mTeamRecyclerView.setAdapter(mPlayerAdapter);



        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mTeamRecyclerView);



        ScrollingPagerIndicator recyclerIndicator = root.findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(mTeamRecyclerView);
//        mTeamName = root.findViewById(R.id.editTextNewTeamName);
//        mTeamLogo = root.findViewById(R.id.imageViewLogo);
//        mButtonAddPlayer = root.findViewById(R.id.imageViewAddButton);
//        mButtonFinishCreateTeam = root.findViewById(R.id.imageViewNext);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mButtonAddPlayer.setOnClickListener(newTeamOnClickListener);
//        mButtonFinishCreateTeam.setOnClickListener(newTeamOnClickListener);

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
//        mPresenter.setNewPlayer();
        NewPlayerDialogPresenter newPlayerDialogPresenter = new NewPlayerDialogPresenter(newPlayerDialog);
        newPlayerDialog.setPresenter(newPlayerDialogPresenter);
        newPlayerDialog.setTargetFragment(this, TARGET_FRAGMENT_REQUEST_CODE);
        newPlayerDialog.show(getFragmentManager(), "createPlayer");

//        fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
//            @Override
//            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
//                super.onFragmentViewDestroyed(fm, f);
//                //do sth
//                if (mPresenter.getNewPlayer() != null) {
//                    mPresenter.getTeamPlayer().add(mPresenter.getNewPlayer());
//                    mPresenter.updateData();
//                }
//                fm.unregisterFragmentLifecycleCallbacks(this);
//            }
//        }, false);

//        fm.executePendingTransactions();
//
//        newPlayerDialog.getDialog().setOnDismissListener(dialog -> {
//
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == TARGET_FRAGMENT_REQUEST_CODE) {
            String name = data.getStringExtra(NEW_PLAYER_NAME);
            String email = data.getStringExtra(NEW_PLAYER_EMAIL);
            String onCourtPosition = data.getStringExtra(NEW_PLAYER_ONCOURT_POSITION);
            String backNumber = data.getStringExtra(NEW_PLAYER_BACK_NUMBER);

            Log.d(TAG, "onActivityResult greeting: " + name + "\n" + email + "\n" + onCourtPosition + "\n" + backNumber);
            if (name == null) {
                Log.d(TAG, "it is null: ");
            } else {
                mPresenter.setNewPlayer(name, email, onCourtPosition, Integer.parseInt(backNumber));
                mPresenter.getTeamPlayer().add(mPresenter.getNewPlayer());
                mPresenter.updateData();
            }
        }
    }

    public static Intent newIntent(String name, String email, String onCourtPosition, int backNumber) {
        Intent intent = new Intent();
        intent.putExtra(NEW_PLAYER_NAME, name);
        intent.putExtra(NEW_PLAYER_EMAIL, email);
        intent.putExtra(NEW_PLAYER_ONCOURT_POSITION, onCourtPosition);
        intent.putExtra(NEW_PLAYER_BACK_NUMBER, String.valueOf(backNumber));
        return intent;
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























