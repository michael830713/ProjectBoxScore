package com.mike.projectboxscore.EditTeam;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.MyTeam.EditPlayer.EditPlayerDialog;
import com.mike.projectboxscore.MyTeam.EditPlayer.EditPlayerDialogPresenter;
import com.mike.projectboxscore.NewTeam.NewPlayerAdapter;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialog;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialogPresenter;
import com.mike.projectboxscore.NewTeam.NewTeamContract;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditTeamFragment extends Fragment implements EditTeamContract.View {

    private static final String TAG = "EditTeamFragment";

    private static final int TARGET_FRAGMENT_REQUEST_CODE = 1;
    private static final String NEW_PLAYER_NAME = "playerMessage";
    private static final String NEW_PLAYER_EMAIL = "playerEmail";
    private static final String NEW_PLAYER_ONCOURT_POSITION = "playerOnCourtPosition";
    private static final String NEW_PLAYER_BACK_NUMBER = "playerBackNumber";
    private static final int EDIT_DIALOG_REQUEST_CODE = 2;

    EditTeamContract.Presenter mPresenter;
    private RecyclerView mPlayerRecyclerView;
    private TextView mTitle;
    private EditText mTeamName;
    private ImageView mTeamLogo;
    private ImageView mButtonAddPlayer;
    private ImageView mButtonFinishCreateTeam;

    private EditPlayerAdapter mPlayerAdapter;

    @Override
    public void setPresenter(EditTeamContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    public static EditTeamFragment newInstance() {
        return new EditTeamFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerAdapter = new EditPlayerAdapter(mPresenter, mPresenter.getTeamPlayer());
        Log.d(TAG, "getTeamPlayer: " + mPresenter.getTeamPlayer());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_team, container, false);
        mTitle = root.findViewById(R.id.textViewTitle);

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
        mTeamName.setText(mPresenter.getTeam().getmName());
        mButtonAddPlayer.setOnClickListener(newTeamOnClickListener);
        mButtonFinishCreateTeam.setOnClickListener(newTeamOnClickListener);
        mTitle.setText("Edit Team ");

    }

    private View.OnClickListener newTeamOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageViewAddButton:
                    mPresenter.showNewPlayerDialog();
                    break;

                case R.id.imageViewNext:
                    if (mTeamName.getText().toString().isEmpty()) {
                        showToastMessageUi("Please enter team name!");
                    } else {
//                        mPresenter.createNewTeam();
                        mPresenter.openMyTeamFragment();
                    }

                    break;

            }
        }
    };

    @Override
    public void showEditPlayerUi(Player player) {
        EditPlayerDialog editPlayerDialog = new EditPlayerDialog();
        EditPlayerDialogPresenter newPlayerDialogPresenter = new EditPlayerDialogPresenter(editPlayerDialog, player);
        editPlayerDialog.setPresenter(newPlayerDialogPresenter);
        editPlayerDialog.setTargetFragment(this, EDIT_DIALOG_REQUEST_CODE);
        editPlayerDialog.show(getFragmentManager(), "createPlayer");
    }

    @Override
    public void showNewPlayerUi() {
        NewPlayerDialog newPlayerDialog = new NewPlayerDialog();
        NewPlayerDialogPresenter newPlayerDialogPresenter = new NewPlayerDialogPresenter(newPlayerDialog);
        newPlayerDialog.setPresenter(newPlayerDialogPresenter);
        newPlayerDialog.setTargetFragment(this, TARGET_FRAGMENT_REQUEST_CODE);
        newPlayerDialog.show(getFragmentManager(), "createPlayer");

    }

    @Override
    public void openMyTeamFragmentUi() {

        getFragmentManager().popBackStack("MyTeam", 0);
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
        } else if (requestCode == EDIT_DIALOG_REQUEST_CODE) {
            mPresenter.updateData();
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
        mPlayerAdapter.updateData();
    }

    @Override
    public String getTeamName() {
        return mTeamName.getText().toString();
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return null;
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






















