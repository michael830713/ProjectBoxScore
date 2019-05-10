package com.mike.projectboxscore.teamedit.editplayer;

import android.net.Uri;

import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.createteam.createplayer.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

public interface EditPlayerDialogContract {
    interface View extends BaseView<Presenter> {

        void setPositionSpinnerUi();

        void openGalleryUi();
    }

    interface Presenter extends BasePresenter {

        void setPositionSpinner();

        Player getPlayer();

        int getSpinnerPosition();

        String getFileExtention(Uri mImageUri);

        void updatePlayerInfo(String playerName, int backNumber, String position,String imageUrl);

        void openGallery();

        void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback);
    }
}
