package com.mike.projectboxscore.MyTeam.EditPlayer;

import android.net.Uri;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface EditPlayerDialogContract {
    interface View extends BaseView<Presenter> {
        void showPlayerUi(ArrayList<PlayerStats> playerOnBench);

        void changePlayerUi(PlayerStats playerToEnterGame);

        void setPositionSpinnerUi();

        void openGalleryUi();
    }

    interface Presenter extends BasePresenter {
        void showPlayer();

        void changePlayer(int rowIndex);

        void setPositionSpinner();
        void deletePlayer();

        void setNewPlayerInfo(String mName, String mEmail, int backNumber, String onCourtPosition);

        void setToBeReplacedPlayer(PlayerStats playerToEnterGame);

        Player getPlayer();

        int getSpinnerPosition();

        String getFileExtention(Uri mImageUri);

        void updatePlayerInfo(String playerName, String email, int backNumber, String position,String imageUrl);

        void openGallery();

        void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback);
    }
}
