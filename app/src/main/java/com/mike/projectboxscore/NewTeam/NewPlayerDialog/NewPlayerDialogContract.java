package com.mike.projectboxscore.NewTeam.NewPlayerDialog;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface NewPlayerDialogContract {
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

        void openGallery();

        String getFileExtention(Uri mImageUri);

        void setContext(FragmentActivity activity);

        void uploadFile(Uri imageUri,String fileExtention,PlayerAvatarUploadCallback callback);
    }
}
