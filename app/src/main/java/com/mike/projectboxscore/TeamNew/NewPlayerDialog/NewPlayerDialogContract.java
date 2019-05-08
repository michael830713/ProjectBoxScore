package com.mike.projectboxscore.TeamNew.NewPlayerDialog;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface NewPlayerDialogContract {
    interface View extends BaseView<Presenter> {

        void setPositionSpinnerUi();

        void openGalleryUi();
    }

    interface Presenter extends BasePresenter {

        void setPositionSpinner();


        void openGallery();

        String getFileExtention(Uri mImageUri);

        void setContext(FragmentActivity activity);

        void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback);
    }
}
