package com.mike.projectboxscore.TeamEdit.EditPlayer;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.PlayerAvatarUploadCallback;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPlayerDialogPresenter implements EditPlayerDialogContract.Presenter {
    private static final String TAG = "EditPlayerDialogPresenter";
    EditPlayerDialogContract.View mView;
    private Player mPlayer;
    private Context mContext;

    private StorageReference mStorageReference;

    public EditPlayerDialogPresenter(EditPlayerDialogContract.View view, Player player, Context context) {
        mView = checkNotNull(view, "view cannot be null!");
        mPlayer = player;
        mContext = context;
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");

    }

    @Override
    public void start() {

    }


    @Override
    public Player getPlayer() {
        return mPlayer;
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
    public String getFileExtention(Uri mImageUri) {
        ContentResolver cR = mContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(mImageUri));
    }

    @Override
    public void updatePlayerInfo(String playerName, int backNumber, String position, String imageUrl) {
        mPlayer.setName(playerName);
        mPlayer.setBackNumber(backNumber);
        mPlayer.setOnCourtPosition(position);
        if (imageUrl != null) {
            mPlayer.setImageUrl(imageUrl);
        }
    }

    @Override
    public void openGallery() {
        mView.openGalleryUi();
    }

    @Override
    public void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback) {
        if (imageUri != null) {
            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                    + "." + fileExtention);

            StorageTask mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(mContext, "Upload successful", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d(TAG, "upload URL: " + uri);
                                    callback.loadGameCallBack(uri.toString());
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        } else {
            Toast.makeText(mContext, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


}
