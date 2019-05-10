package com.mike.projectboxscore.createteam.createplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.FirebaseDataSource;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.callback.PlayerAvatarUploadCallback;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialogPresenter implements NewPlayerDialogContract.Presenter {
    private static final String TAG = "NewPlayerDialogPresenter";
    NewPlayerDialogContract.View mView;
    private Context mContext;

    public NewPlayerDialogPresenter(NewPlayerDialogContract.View view) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
    }

    @Override
    public void start() {

    }

    @Override
    public void setPositionSpinner() {
        mView.setPositionSpinnerUi();
    }

    @Override
    public void openGallery() {
        mView.openGalleryUi();
    }

    @Override
    public String getFileExtention(Uri imageUri) {

        ContentResolver cR = mContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(imageUri));
    }

    @Override
    public void setContext(FragmentActivity activity) {
        mContext = activity;
    }

    @Override
    public void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback) {
        if (imageUri != null) {

            FirebaseDataSource.uploadTeamLogoFile(mContext, imageUri, fileExtention, callback);

        } else {
            Toast.makeText(mContext, mContext.getString(R.string.file_empty_toast), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void checkPermissionAndOpenGallery(Context activity) {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(activity, permissions)) {
            mView.requestGalleryPermission(permissions);
        } else {
            mView.openGalleryUi();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
