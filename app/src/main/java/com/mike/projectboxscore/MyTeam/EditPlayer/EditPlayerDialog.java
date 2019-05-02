package com.mike.projectboxscore.MyTeam.EditPlayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.EditTeam.EditTeamFragment;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialogContract;
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPlayerDialog extends DialogFragment implements EditPlayerDialogContract.View {

    private static final String TAG = "EditPlayerDialog";

    private EditText mPlayerName;
    private EditText mEmail;
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
        Log.d(TAG, "Dialog onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_player, container, false);
        mPlayerName = view.findViewById(R.id.editTextPlayerName);
        mEmail = view.findViewById(R.id.editText_email);
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
        mEmail.setText(mPresenter.getPlayer().getmEmail());
        mBackNumber.setText(String.valueOf(mPresenter.getPlayer().getBackNumber()));
        mPresenter.setPositionSpinner();
        mPosition.setSelection(mPresenter.getSpinnerPosition());

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
                    String email = mEmail.getText().toString();
                    int backNumber = -1;
                    String position = mPosition.getSelectedItem().toString();

                    if (!mBackNumber.getText().toString().equals("")) {
                        backNumber = Integer.parseInt(mBackNumber.getText().toString());
                    }
                    if (playerName != null && email != null && backNumber != -1 && position != null) {
                        if (mImageUri != null) {
                            int finalBackNumber = backNumber;
                            mPresenter.uploadFile(mImageUri, mPresenter.getFileExtention(mImageUri), new PlayerAvatarUploadCallback() {
                                @Override
                                public void loadGameCallBack(String imageLink) {
                                    mPresenter.updatePlayerInfo(playerName, email, finalBackNumber, position, imageLink);
                                    sendResult(playerName, email, position, finalBackNumber, imageLink);
                                    Log.d(TAG, "loadGameCallBack: " + mPresenter.getPlayer().getImageUrl());
                                }
                            });
                        } else {

                            mPresenter.updatePlayerInfo(playerName, email, backNumber, position, null);
                            sendResult(playerName, email, position, backNumber, null);
                        }
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please enter player info!", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.imageViewDismiss:

                    sendResult(null, null, null, -1, null);
                    dismiss();
                    break;
                case R.id.imageViewAvatarFrame:

                    mPresenter.openGallery();

                    break;
            }
        }
    };

    private void sendResult(String name, String email, String onCourtPosition, int backNumber, String imageUrl) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = EditTeamFragment.newIntent(name, email, onCourtPosition, backNumber, imageUrl);
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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Log.d(TAG, "onActivityResult: " + data.getData());
            Picasso.get().load(mImageUri).placeholder(R.drawable.man).resize(50, 50).centerCrop().into(mAvatar);
//            mPlayerAvatar.setColorFilter(null);
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
