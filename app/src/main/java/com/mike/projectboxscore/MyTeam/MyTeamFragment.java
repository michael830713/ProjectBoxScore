package com.mike.projectboxscore.MyTeam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.EditTeam.EditTeamFragment;
import com.mike.projectboxscore.EditTeam.EditTeamPresenter;
import com.mike.projectboxscore.MyTeam.EditPlayer.EditPlayerDialog;
import com.mike.projectboxscore.MyTeam.EditPlayer.EditPlayerDialogPresenter;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialog;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialogPresenter;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.NewTeam.NewTeamPresenter;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.boxScore.BoxScorePresenter;
import com.mike.projectboxscore.boxScore.BoxSoreFragment;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MyTeamFragment extends Fragment implements MyTeamContract.View {

    private static final String TAG = "MyTeamFragment";

    private static final int NEW_DIALOG_REQUEST_CODE = 1;
    private static final int EDIT_DIALOG_REQUEST_CODE = 2;
    private static final String NEW_PLAYER_NAME = "playerMessage";
    private static final String NEW_PLAYER_EMAIL = "playerEmail";
    private static final String NEW_PLAYER_ONCOURT_POSITION = "playerOnCourtPosition";
    private static final String NEW_PLAYER_BACK_NUMBER = "playerBackNumber";

    MyTeamContract.Presenter mPresenter;
    private RecyclerView mTeamRecyclerView;
    private ImageView mAddTeamButton;
    private FloatingActionButton mFloatAddTeam;

    private NewTeamPresenter mNewTeamPresenter;

    private MyTeamAdapter mTeamAdapter;
    private EditTeamPresenter mEditTeamPresenter;

    @Override
    public void setPresenter(MyTeamContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    public static MyTeamFragment newInstance() {
        return new MyTeamFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTeamAdapter = new MyTeamAdapter(mPresenter, getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myteam, container, false);
//        mAddTeamButton = root.findViewById(R.id.imageViewAddTeam);
        mFloatAddTeam = root.findViewById(R.id.floatingActionButtonAddTeam);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mAddTeamButton.setOnClickListener(newTeamOnClickListener);
        mFloatAddTeam.setOnClickListener(newTeamOnClickListener);

    }

    private void setupRecyclerView(View view) {

        mTeamRecyclerView = view.findViewById(R.id.recyclerViewTeam);
        LinearLayoutManager teamLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mTeamRecyclerView.setLayoutManager(teamLayoutManager);
        mTeamAdapter.setTeams(mPresenter.getTeams());
        mTeamRecyclerView.setAdapter(mTeamAdapter);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider_vertical);
        mTeamRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mTeamRecyclerView);
        ScrollingPagerIndicator recyclerIndicator = view.findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(mTeamRecyclerView);
    }

    private View.OnClickListener newTeamOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openNewTeamFragment();
        }
    };

    @Override
    public void openNewTeamFragmentUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        NewTeamFragment fragment = NewTeamFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "NewTeam").addToBackStack("NewTeam");
        fragmentTransaction.commit();
        mNewTeamPresenter = new NewTeamPresenter(fragment, mPresenter.getTeams());
    }

    @Override
    public void openBoxScoreUi(Game game) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BoxSoreFragment fragment = BoxSoreFragment.newInstance();
        BoxScorePresenter boxScorePresenter;
        boxScorePresenter = new BoxScorePresenter(fragment, game, false);
//        Log.d(TAG, "openBoxScoreUi player size: " + mPresenter.getGame().getmPlayerStats().size());
        fragmentTransaction.replace(R.id.container, fragment, "Surface").addToBackStack("BoxScore");
        fragmentTransaction.commit();
    }

    @Override
    public void showEditTeamUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        EditTeamFragment fragment = EditTeamFragment.newInstance();
        mEditTeamPresenter = new EditTeamPresenter(fragment, mTeamAdapter.getSelectedTeam(), mPresenter.getTeams());
        fragment.setPresenter(mEditTeamPresenter);
        fragmentTransaction.replace(R.id.container, fragment, "NewTeam").addToBackStack("EditTeam");
        fragmentTransaction.commit();

        Log.d(TAG, "selectedteam: " + mTeamAdapter.getSelectedTeam());
    }

    @Override
    public void showNewPlayerUi() {
        NewPlayerDialog newPlayerDialog = new NewPlayerDialog();
        NewPlayerDialogPresenter newPlayerDialogPresenter = new NewPlayerDialogPresenter(newPlayerDialog);
        newPlayerDialog.setPresenter(newPlayerDialogPresenter);
        newPlayerDialog.setTargetFragment(this, NEW_DIALOG_REQUEST_CODE);
        newPlayerDialog.show(getFragmentManager(), "createPlayer");
    }

    @Override
    public void showEditPlayerUi(Player player) {
        EditPlayerDialog editPlayerDialog = new EditPlayerDialog();
        EditPlayerDialogPresenter newPlayerDialogPresenter = new EditPlayerDialogPresenter(editPlayerDialog, player);
        editPlayerDialog.setPresenter(newPlayerDialogPresenter);
        editPlayerDialog.setTargetFragment(this, EDIT_DIALOG_REQUEST_CODE);
        editPlayerDialog.show(getFragmentManager(), "createPlayer");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == NEW_DIALOG_REQUEST_CODE) {
            String name = data.getStringExtra(NEW_PLAYER_NAME);
            String email = data.getStringExtra(NEW_PLAYER_EMAIL);
            String onCourtPosition = data.getStringExtra(NEW_PLAYER_ONCOURT_POSITION);
            String backNumber = data.getStringExtra(NEW_PLAYER_BACK_NUMBER);

            Log.d(TAG, "onActivityResult player: " + name + "\n" + email + "\n" + onCourtPosition + "\n" + backNumber);
            if (name == null) {
                Log.d(TAG, "it is null: ");
            } else {
                mPresenter.setNewPlayer(name, email, onCourtPosition, Integer.parseInt(backNumber), mTeamAdapter.getSelectedTeam());
                Log.d(TAG, "getNewPlayer: " + mPresenter.getNewPlayer());
//                mPresenter.getTeamPlayer().add(mPresenter.getNewPlayer());
                mPresenter.updateData();
            }
        } else if (requestCode == EDIT_DIALOG_REQUEST_CODE) {
            mPresenter.updateData();
        }
    }

    public static Intent newIntent(String name, String email, String onCourtPosition, int backNumber) {
        Intent intent = new Intent();
        return intent;
    }

    @Override
    public void updateDataUi() {
        mTeamAdapter.updateData(mPresenter.getTeams());
    }

    @Override
    public String getTeamName() {
        return null;
    }

    @Override
    public void onPause() {

        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void showToastMessageUi(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}























