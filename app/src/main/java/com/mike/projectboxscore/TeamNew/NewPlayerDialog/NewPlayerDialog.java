package com.mike.projectboxscore.TeamNew.NewPlayerDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
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
import com.mike.projectboxscore.TeamEdit.EditTeamFragment;
import com.mike.projectboxscore.ExifUtil;
import com.mike.projectboxscore.TeamNew.NewTeamFragment;
import com.mike.projectboxscore.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialog extends DialogFragment implements NewPlayerDialogContract.View {

    private static final String TAG = "NewPlayerDialog";
    private static final int PICK_IMAGE_REQUEST = 5;
    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_GALLERY = 40;

    private EditText mPlayerName;
    private EditText mBackNumber;
    private ImageView mConfirmButton;
    private ImageView mDismissButton;
    private ImageView mPlayerAvatarFrame;
    private ImageView mPlayerAvatar;
    private Spinner mPosition;

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
        mBackNumber = view.findViewById(R.id.editText_back_number);
        mPosition = view.findViewById(R.id.spinner);
        mConfirmButton = view.findViewById(R.id.imageViewConfirm);
        mDismissButton = view.findViewById(R.id.imageViewDismiss);
        mPlayerAvatarFrame = view.findViewById(R.id.imageViewAvatarFrame);
        mPlayerAvatar = view.findViewById(R.id.pic);

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
                    String position = mPosition.getSelectedItem().toString();
                    int backNumber = Constants.OPPONENT_BACK_NUMBER;
                    if (!mBackNumber.getText().toString().equals("")) {
                        backNumber = Integer.parseInt(mBackNumber.getText().toString());
                    }

                    setupNewPlayer(playerName, position, backNumber);

                    break;
                case R.id.imageViewDismiss:

                    sendResult(null, null, Constants.OPPONENT_BACK_NUMBER, null);
                    dismiss();

                    break;
                case R.id.imageViewAvatarFrame:
                    mPresenter.checkPermissionAndOpenGallery(getActivity());
                    break;
            }
        }
    };

    private void setupNewPlayer(String playerName, String position, int backNumber) {
        if (!playerName.isEmpty() && backNumber != -1 && position != null) {
            if (mImageUri != null) {
                int finalBackNumber = backNumber;


                mPresenter.uploadFile(getImageUri(getActivity(), mImageBitmap),
                        mPresenter.getFileExtention(mImageUri), imageLink -> {

                            sendResult(playerName, position, finalBackNumber, imageLink);
                            Log.d(TAG, "loadGameCallBack: " + imageLink);

                        });
            } else {
                Log.d(TAG, "no image upload: ");
                sendResult(playerName, position, backNumber, null);
            }
            dismiss();
        } else {
            Toast.makeText(getActivity(), getString(R.string.enter_player_toast), Toast.LENGTH_SHORT).show();
        }
    }

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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void requestGalleryPermission(String[] permissions) {
        ActivityCompat.requestPermissions(getActivity(), permissions, MY_PERMISSIONS_REQUEST_READ_WRITE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            setImageToAvatar(data);
        }
    }

    private void setImageToAvatar(Intent data) {
        mImageUri = data.getData();
        mImageBitmap = ExifUtil.normalizeImageForUri(getActivity(), mImageUri);
        mPlayerAvatar.setImageBitmap(mImageBitmap);
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, Constants.IMAGE_URL_QUALITY, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
