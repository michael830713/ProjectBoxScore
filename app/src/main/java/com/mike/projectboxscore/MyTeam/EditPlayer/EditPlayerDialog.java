package com.mike.projectboxscore.MyTeam.EditPlayer;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.EditTeam.EditTeamFragment;
import com.mike.projectboxscore.ExifUtil;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialogContract;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPlayerDialog extends DialogFragment implements EditPlayerDialogContract.View {

    private static final String TAG = "EditPlayerDialog";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 30;

    private EditText mPlayerName;
    //    private EditText mEmail;
    private EditText mBackNumber;
    private ImageView mConfirmButton;
    private ImageView mDismissButton;
    private ImageView mAvatarFrame;
    private ImageView mAvatar;
    private Spinner mPosition;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 5;
    private EditPlayerDialogContract.Presenter mPresenter;

    @Override
    public void setPresenter(EditPlayerDialogContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        checkGalleryPermission();
        Log.d(TAG, "Dialog onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_player, container, false);
        mPlayerName = view.findViewById(R.id.editTextPlayerName);
//        mEmail = view.findViewById(R.id.editText_email);
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
//        mEmail.setText(mPresenter.getPlayer().getmEmail());
        mBackNumber.setText(String.valueOf(mPresenter.getPlayer().getBackNumber()));
        mPresenter.setPositionSpinner();
        mPosition.setSelection(mPresenter.getSpinnerPosition());
        Picasso.get().load(mPresenter.getPlayer().getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(50, 50).centerCrop().into(mAvatar);

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
//                    String email = mEmail.getText().toString();
                    int backNumber = -1;
                    String position = mPosition.getSelectedItem().toString();

                    if (!mBackNumber.getText().toString().equals("")) {
                        backNumber = Integer.parseInt(mBackNumber.getText().toString());
                    }
                    if (playerName != null && backNumber != -1 && position != null) {
                        if (mImageUri != null) {
                            int finalBackNumber = backNumber;
                            ProgressDialog pd = new ProgressDialog(getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                            pd.setMessage("uploading image...");
                            pd.show();
                            mPresenter.uploadFile(mImageUri, mPresenter.getFileExtention(mImageUri), new PlayerAvatarUploadCallback() {
                                @Override
                                public void loadGameCallBack(String imageLink) {
                                    pd.dismiss();
                                    mPresenter.updatePlayerInfo(playerName, finalBackNumber, position, imageLink);
                                    sendResult(playerName, position, finalBackNumber, imageLink);
                                    Log.d(TAG, "loadGameCallBack: " + mPresenter.getPlayer().getImageUrl());
                                }
                            });
                        } else {

                            mPresenter.updatePlayerInfo(playerName, backNumber, position, null);
                            sendResult(playerName, position, backNumber, null);
                        }
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please enter player info!", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.imageViewDismiss:

                    sendResult(null, null, -1, null);
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
//        dismiss();
    }

    @Override
    public void setPositionSpinnerUi() {
        final String[] lunch = {"G", "F", "C"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        mPosition.setAdapter(lunchList);
    }

    @Override
    public void openGalleryUi() {
        checkGalleryPermission();
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            checkGalleryPermission();
//            // Permission is not granted
//        } else {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent, PICK_IMAGE_REQUEST);
//        }

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
            // Permission has already been granted
        }
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
            Bitmap bitmap = ExifUtil.normalizeImageForUri(getActivity(), mImageUri);
            Log.d(TAG, "onActivityResult: " + bitmap);
            mAvatar.setImageBitmap(bitmap);
//            Bitmap b = BitmapFactory.decodeFile(mImageUri.toString());
//            Bitmap orientedBitmap = ExifUtil.rotateBitmap(mImageUri.toString(), b);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Log.d(TAG, "onActivityResult: " + data.getData());
//            mAvatar.setImageBitmap(orientedBitmap);

//            Picasso.get().load(mImageUri).placeholder(R.drawable.man).resize(50, 50).centerCrop().into(mAvatar);
        }

    }

    @Override
    public void showPlayerUi(ArrayList<PlayerStats> playerOnBench) {
    }

    @Override
    public void changePlayerUi(PlayerStats playerToEnterGame) {
        dismiss();
    }
}
