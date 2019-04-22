package com.mike.projectboxscore.MyTeam.EditPlayer;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialogContract;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPlayerDialogPresenter implements EditPlayerDialogContract.Presenter {
    private static final String TAG = "NewPlayerDialogPresenter";
    EditPlayerDialogContract.View mView;
    private Player mPlayer;
    private PlayerStats mTobeReplacedPlayer;

    public EditPlayerDialogPresenter(EditPlayerDialogContract.View view, Player player) {
        mView = checkNotNull(view, "view cannot be null!");
        mPlayer = player;
//        mPlayer = player;
    }

    @Override
    public void start() {

    }

    @Override
    public void setNewPlayerInfo(String mName, String mEmail, int backNumber, String onCourtPosition) {
        mPlayer.setmName(mName);
        mPlayer.setmEmail(mEmail);
        mPlayer.setBackNumber(backNumber);
        mPlayer.setOnCourtPosition(onCourtPosition);
    }

    @Override
    public Player getPlayer() {
        return mPlayer;
    }

    @Override
    public void deletePlayer() {

        mPlayer = null;
        System.gc();
    }

    @Override
    public void changePlayer(int rowIndex) {
    }

    @Override
    public void setPositionSpinner() {
        mView.setPositionSpinnerUi();
    }

    @Override
    public int getSpinnerPosition() {
        if (mPlayer.getOnCourtPosition().equals("G")) {
            return 0;
        } else if (mPlayer.getOnCourtPosition().equals("F")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void updatePlayerInfo(String playerName, String email, int backNumber, String position) {
        mPlayer.setmName(playerName);
        mPlayer.setmEmail(email);
        mPlayer.setBackNumber(backNumber);
        mPlayer.setOnCourtPosition(position);
    }

    @Override
    public void setToBeReplacedPlayer(PlayerStats playerToEnterGame) {
        mTobeReplacedPlayer = playerToEnterGame;
    }

    @Override
    public void showPlayer() {
    }

    public void setBenchPlayer(ArrayList<PlayerStats> player) {
    }
}
