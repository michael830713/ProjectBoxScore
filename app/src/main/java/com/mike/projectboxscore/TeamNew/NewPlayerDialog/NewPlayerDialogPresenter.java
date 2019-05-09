package com.mike.projectboxscore.TeamNew.NewPlayerDialog;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.FirebaseDataSource;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialogPresenter implements NewPlayerDialogContract.Presenter {
    private static final String TAG = "NewPlayerDialogPresenter";
    NewPlayerDialogContract.View mView;
    private Context mContext;
    private StorageTask mUploadTask;

    private StorageReference mStorageReference;

    public NewPlayerDialogPresenter(NewPlayerDialogContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
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
            Toast.makeText(mContext, "No file selected", Toast.LENGTH_SHORT).show();
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
