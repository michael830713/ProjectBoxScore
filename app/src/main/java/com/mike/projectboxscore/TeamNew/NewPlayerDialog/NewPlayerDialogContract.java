package com.mike.projectboxscore.TeamNew.NewPlayerDialog;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

public interface NewPlayerDialogContract {
    interface View extends BaseView<Presenter> {

        void setPositionSpinnerUi();

        void openGalleryUi();

        void requestGalleryPermission(String[] permissions);
    }

    interface Presenter extends BasePresenter {

        void setPositionSpinner();

        void openGallery();

        String getFileExtention(Uri mImageUri);

        void setContext(FragmentActivity activity);

        void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback);

        void checkPermissionAndOpenGallery(Context activity);
    }
}
