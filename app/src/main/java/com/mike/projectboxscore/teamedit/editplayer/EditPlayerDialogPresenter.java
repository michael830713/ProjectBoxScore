package com.mike.projectboxscore.teamedit.editplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.FirebaseDataSource;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.createteam.createplayer.PlayerAvatarUploadCallback;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPlayerDialogPresenter implements EditPlayerDialogContract.Presenter {
    private static final String TAG = "EditPlayerDialogPresenter";
    EditPlayerDialogContract.View mView;
    private Player mPlayer;
    private Context mContext;

    public EditPlayerDialogPresenter(EditPlayerDialogContract.View view, Player player, Context context) {
        mView = checkNotNull(view, "view cannot be null!");
        mPlayer = player;
        mContext = context;

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

            FirebaseDataSource.uploadTeamLogoFile(mContext, imageUri, fileExtention, callback);

        } else {
            Toast.makeText(mContext, mContext.getString(R.string.file_empty_toast), Toast.LENGTH_SHORT).show();
        }
    }


}
