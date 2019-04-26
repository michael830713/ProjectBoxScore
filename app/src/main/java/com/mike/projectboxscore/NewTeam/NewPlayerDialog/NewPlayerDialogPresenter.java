package com.mike.projectboxscore.NewTeam.NewPlayerDialog;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialogPresenter implements NewPlayerDialogContract.Presenter {
    private static final String TAG = "NewPlayerDialogPresenter";
    NewPlayerDialogContract.View mView;
    private Player mPlayer;
    private PlayerStats mTobeReplacedPlayer;
    private Context mContext;
    private StorageTask mUploadTask;

    private StorageReference mStorageReference;

    public NewPlayerDialogPresenter(NewPlayerDialogContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mStorageReference= FirebaseStorage.getInstance().getReference("uploads");
//        mPlayer = player;
    }

    @Override
    public void start() {

    }

    @Override
    public void setNewPlayerInfo(String mName, String mEmail, int backNumber, String onCourtPosition) {
        mPlayer.setName(mName);
        mPlayer.setmEmail(mEmail);
        mPlayer.setBackNumber(backNumber);
        mPlayer.setOnCourtPosition(onCourtPosition);
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
    public void setToBeReplacedPlayer(PlayerStats playerToEnterGame) {
        mTobeReplacedPlayer = playerToEnterGame;
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
    public void showPlayer() {
    }

    public void setBenchPlayer(ArrayList<PlayerStats> player) {
    }

    @Override
    public void uploadFile(Uri imageUri, String fileExtention) {
        if (imageUri != null) {
            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                    + "." + fileExtention);

            mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(mContext, "Upload successful", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d(TAG, "upload URL: " + uri);
                                }
                            });

//                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
//                                    taskSnapshot.getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(mContext, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
