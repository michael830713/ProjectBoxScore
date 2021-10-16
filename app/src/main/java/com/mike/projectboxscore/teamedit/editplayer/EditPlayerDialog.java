package com.mike.projectboxscore.teamedit.editplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.teamedit.EditTeamFragment;
import com.mike.projectboxscore.ExifUtil;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPlayerDialog extends DialogFragment implements EditPlayerDialogContract.View {

    private static final String TAG = "EditPlayerDialog";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 30;

    private EditText mPlayerName;
    private EditText mBackNumber;
    private ImageView mConfirmButton;
    private ImageView mDismissButton;
    private ImageView mAvatarFrame;
    private ImageView mAvatar;
    private Spinner mPosition;
    private Uri mImageUri;
    private Bitmap mImageBitmap;
    private static final int PICK_IMAGE_REQUEST = 5;
    private EditPlayerDialogContract.Presenter mPresenter;

    @Override
    public void setPresenter(EditPlayerDialogContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Dialog onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_player, container, false);
        mPlayerName = view.findViewById(R.id.editTextPlayerName);
        mBackNumber = view.findViewById(R.id.editText_back_number);
        mPosition = view.findViewById(R.id.spinner);
        mConfirmButton = view.findViewById(R.id.imageViewConfirm);
        mDismissButton = view.findViewById(R.id.imageViewDismiss);
        mAvatarFrame = view.findViewById(R.id.imageViewAvatarFrame);
        mAvatar = view.findViewById(R.id.pic);

        setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPlayerName.setText(mPresenter.getPlayer().getName());
        mBackNumber.setText(String.valueOf(mPresenter.getPlayer().getBackNumber()));
        mPresenter.setPositionSpinner();
        mPosition.setSelection(mPresenter.getSpinnerPosition());
        Picasso.get().load(mPresenter.getPlayer().getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(Constants.PLAYER_AVATAR_DIMEN, Constants.PLAYER_AVATAR_DIMEN).centerCrop().into(mAvatar);

        mConfirmButton.setOnClickListener(onClickListener);
        mDismissButton.setOnClickListener(onClickListener);
        mAvatarFrame.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageViewConfirm:
                    String playerName = mPlayerName.getText().toString();
                    int backNumber = Constants.OPPONENT_BACK_NUMBER;
                    String position = mPosition.getSelectedItem().toString();

                    if (!mBackNumber.getText().toString().equals("")) {
                        backNumber = Integer.parseInt(mBackNumber.getText().toString());
                    }
                    if (playerName != null && backNumber != Constants.OPPONENT_BACK_NUMBER && position != null) {
                        if (mImageUri != null) {
                            int finalBackNumber = backNumber;

                            mPresenter.uploadFile(getImageUri(getActivity(), mImageBitmap),
                                    mPresenter.getFileExtention(mImageUri), imageLink -> {

                                        //after upload succeed
                                        mPresenter.updatePlayerInfo(playerName, finalBackNumber, position, imageLink);
                                        sendResult(playerName, position, finalBackNumber, imageLink);
                                    });

                        } else {

                            //no need to upload image
                            mPresenter.updatePlayerInfo(playerName, backNumber, position, null);
                            sendResult(playerName, position, backNumber, null);
                        }
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.enter_player_toast), Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.imageViewDismiss:

                    sendResult(null, null, Constants.OPPONENT_BACK_NUMBER, null);
                    dismiss();
                    break;
                case R.id.imageViewAvatarFrame:
                    mPresenter.openGallery();
                    break;
            }
        }
    };

    private void sendResult(String name, String onCourtPosition, int backNumber, String imageUrl) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = EditTeamFragment.newIntent(name, onCourtPosition, backNumber, imageUrl);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    @Override
    public void setPositionSpinnerUi() {
        final String[] lunch = {Constants.GUARD, Constants.FORWARD, Constants.CENTER};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        mPosition.setAdapter(lunchList);
    }

    @Override
    public void openGalleryUi() {
        checkGalleryPermission();
    }

    private void checkGalleryPermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!hasPermissions(getActivity(), permissions)) {
            ActivityCompat.requestPermissions(getActivity(), permissions, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();
            mImageBitmap = ExifUtil.normalizeImageForUri(getActivity(), mImageUri);
            mAvatar.setImageBitmap(mImageBitmap);
        }

    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, Constants.IMAGE_URL_QUALITY, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
