package com.mike.projectboxscore.NewTeam.NewPlayerDialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.EditTeam.EditTeamFragment;
import com.mike.projectboxscore.ExifUtil;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialog extends DialogFragment implements NewPlayerDialogContract.View {

    private static final String TAG = "NewPlayerDialog";
    private static final int PICK_IMAGE_REQUEST = 5;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 40;

    private EditText mPlayerName;
    //    private EditText mEmail;
    private EditText mBackNumber;
    private ImageView mConfirmButton;
    private ImageView mDismissButton;
    private ImageView mPlayerAvatarFrame;
    private ImageView mPlayerAvatar;
    private Spinner mPosition;

    private StorageReference mStorageRef;

    private Uri mImageUri;
    private Bitmap mImageBitmap;

    private NewPlayerDialogContract.Presenter mPresenter;

    @Override
    public void setPresenter(NewPlayerDialogContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.setContext(getActivity());
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
        mPlayerAvatarFrame = view.findViewById(R.id.imageViewAvatarFrame);
        mPlayerAvatar = view.findViewById(R.id.pic);
//        mPlayerAvatarFrame.setOutlineProvider(new ProfileAvatarOutlineProvider());

        setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.setPositionSpinner();
        mConfirmButton.setOnClickListener(onClickListener);
        mDismissButton.setOnClickListener(onClickListener);
        mPlayerAvatarFrame.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageViewConfirm:
                    String playerName = mPlayerName.getText().toString().trim();
                    Log.d(TAG, "playerName: " + playerName);
//                    String email = mEmail.getText().toString();
                    int backNumber = -1;
                    String position = mPosition.getSelectedItem().toString();

                    if (!mBackNumber.getText().toString().equals("")) {
                        backNumber = Integer.parseInt(mBackNumber.getText().toString());
                    }
                    if (!playerName.isEmpty() && backNumber != -1 && position != null) {

                        if (mImageUri != null) {
                            int finalBackNumber = backNumber;
                            ProgressDialog pd = new ProgressDialog(getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                            pd.setMessage("uploading image...");
                            pd.show();
                            mPresenter.uploadFile(getImageUri(getActivity(), mImageBitmap), mPresenter.getFileExtention(mImageUri), new PlayerAvatarUploadCallback() {
                                @Override
                                public void loadGameCallBack(String imageLink) {
                                    pd.dismiss();
                                    sendResult(playerName, position, finalBackNumber, imageLink);
                                    Log.d(TAG, "loadGameCallBack: " + imageLink);
                                }
                            });
                        } else {
                            Log.d(TAG, "no image upload: ");
                            sendResult(playerName, position, backNumber, null);
                        }
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please enter player info!", Toast.LENGTH_SHORT).show();
                    }
//                    dismiss();
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
        if (getTargetFragment() instanceof NewTeamFragment) {
            Intent intent = NewTeamFragment.newIntent(name, onCourtPosition, backNumber, imageUrl);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

        } else {
            Intent intent = EditTeamFragment.newIntent(name, onCourtPosition, backNumber, imageUrl);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }

        Log.d(TAG, "sendResult URL: " + imageUrl);
//        dismiss();
    }

    //    private void sendResult(String name, String email, String onCourtPosition, int backNumber,String imageUrl) {
//        if (getTargetFragment() == null) {
//            return;
//        }
//        Intent intent = NewTeamFragment.newIntent(name, email, onCourtPosition, backNumber);
//        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
//        dismiss();
//    }
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
            Log.d(TAG, "onActivityResult: " + mImageBitmap);
            mPlayerAvatar.setImageBitmap(mImageBitmap);
        }

    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void showPlayerUi(ArrayList<PlayerStats> playerOnBench) {
    }

    @Override
    public void changePlayerUi(PlayerStats playerToEnterGame) {
        dismiss();
    }
}
