package com.mike.projectboxscore.teamedit;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.teamedit.editplayer.EditPlayerDialog;
import com.mike.projectboxscore.teamedit.editplayer.EditPlayerDialogPresenter;
import com.mike.projectboxscore.ExifUtil;
import com.mike.projectboxscore.createteam.createplayer.NewPlayerDialog;
import com.mike.projectboxscore.createteam.createplayer.NewPlayerDialogPresenter;
import com.mike.projectboxscore.callback.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditTeamFragment extends Fragment implements EditTeamContract.View {

    private static final String TAG = "EditTeamFragment";

    private static final int NEW_DIALOG_REQUEST_CODE = 1;
    private static final String NEW_PLAYER_NAME = "playerMessage";
    private static final String NEW_PLAYER_ONCOURT_POSITION = "playerOnCourtPosition";
    private static final String NEW_PLAYER_BACK_NUMBER = "playerBackNumber";
    private static final String NEW_PLAYER_AVATAR = "playerAvatar";
    private static final int EDIT_DIALOG_REQUEST_CODE = 2;

    private static final int PICK_IMAGE_REQUEST = 5;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 40;
    EditTeamContract.Presenter mPresenter;
    private RecyclerView mPlayerRecyclerView;
    private TextView mTitle;
    private EditText mTeamName;
    private ImageView mTeamLogo;
    private ImageView mButtonAddPlayer;
    private ImageView mButtonFinishCreateTeam;
    private ImageView mButtonDeleteTeam;

    private Uri mImageUri;
    private Bitmap mImageBitmap;

    private EditPlayerAdapter mPlayerAdapter;

    @Override
    public void setPresenter(EditTeamContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    public static EditTeamFragment newInstance() {
        return new EditTeamFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerAdapter = new EditPlayerAdapter(mPresenter, mPresenter.getTeamPlayer());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_team, container, false);
        mTitle = root.findViewById(R.id.textViewTitle);

        mPlayerRecyclerView = root.findViewById(R.id.recyclerViewNewPlayerList);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(playerLayoutManager);
        mPlayerRecyclerView.setAdapter(mPlayerAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_grey);
        mPlayerRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        mTeamName = root.findViewById(R.id.editTextNewTeamName);
        mTeamLogo = root.findViewById(R.id.imageViewLogo);
        Picasso.get().load(mPresenter.getTeam().getmTeamLogoUrl()).placeholder(R.drawable.team_logo_placeholder).into(mTeamLogo);
        mButtonAddPlayer = root.findViewById(R.id.imageViewAddButton);
        mButtonFinishCreateTeam = root.findViewById(R.id.imageViewNext);
        mButtonDeleteTeam = root.findViewById(R.id.imageViewDelete);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTeamName.setText(mPresenter.getTeam().getName());
        mButtonAddPlayer.setOnClickListener(newTeamOnClickListener);
        mButtonFinishCreateTeam.setOnClickListener(newTeamOnClickListener);
        mButtonDeleteTeam.setOnClickListener(newTeamOnClickListener);
        mTeamLogo.setOnClickListener(newTeamOnClickListener);
        mTitle.setText(getString(R.string.edit_team_title));

    }

    private View.OnClickListener newTeamOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String teamName = mTeamName.getText().toString();

            switch (v.getId()) {
                case R.id.imageViewAddButton:
                    mPresenter.showNewPlayerDialog();
                    break;

                case R.id.imageViewNext:
                    if (teamName.trim().isEmpty()) {
                        showToastMessageUi(getString(R.string.enter_team_name_toast));
                    } else {
                        if (mImageUri != null) {
                            mPresenter.uploadFile(mImageUri, getFileExtention(mImageUri), new PlayerAvatarUploadCallback() {
                                @Override
                                public void loadGameCallBack(String imageLink) {
                                    mPresenter.updateTeamInfo(teamName, imageLink);
                                    mPresenter.openMyTeamFragment();
                                }
                            });
                        } else {
                            mPresenter.updateTeamInfo(teamName, null);
                            mPresenter.openMyTeamFragment();
                        }

                    }
                    break;
                case R.id.imageViewDelete:
                    mPresenter.showConfirmDeleteDialog(false);
                    break;

                case R.id.imageViewLogo:

                    checkGalleryPermissionAndOpenGallery();

                    break;

            }
        }
    };

    @Override
    public void showEditPlayerUi(Player player) {
        EditPlayerDialog editPlayerDialog = new EditPlayerDialog();
        EditPlayerDialogPresenter newPlayerDialogPresenter = new EditPlayerDialogPresenter(editPlayerDialog, player, getActivity());
        editPlayerDialog.setPresenter(newPlayerDialogPresenter);
        editPlayerDialog.setTargetFragment(this, EDIT_DIALOG_REQUEST_CODE);
        editPlayerDialog.show(getFragmentManager(), Constants.FRAGMENT_EDIT_PLAYER);
    }

    @Override
    public void showNewPlayerUi() {
        NewPlayerDialog newPlayerDialog = new NewPlayerDialog();
        NewPlayerDialogPresenter newPlayerDialogPresenter = new NewPlayerDialogPresenter(newPlayerDialog);
        newPlayerDialog.setPresenter(newPlayerDialogPresenter);
        newPlayerDialog.setTargetFragment(this, NEW_DIALOG_REQUEST_CODE);
        newPlayerDialog.show(getFragmentManager(), Constants.FRAGMENT_CREATE_PLAYER);

    }

    private void checkGalleryPermissionAndOpenGallery() {
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

    public String getFileExtention(Uri imageUri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    @Override
    public void showConfirmDeleteDialogUi(boolean isPlayer) {
        if (isPlayer) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            builder.setMessage(R.string.confirm_dialog_message)
                    .setCancelable(true)
                    .setPositiveButton(Constants.YES, (dialog, id) -> {

                        mPresenter.getTeamPlayer().remove(mPlayerAdapter.getmRowIndex());
                        mPresenter.updateFirebaseData();
                        mPlayerAdapter.updateData();

                        dialog.dismiss();
                    })
                    .setNegativeButton(Constants.NO, (dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            builder.setMessage(R.string.confirm_delete_team_dialog_message)
                    .setCancelable(true)
                    .setPositiveButton(Constants.YES, (dialog, id) -> {
                        mPresenter.getTeams().remove(mPresenter.getTeam());
                        mPresenter.deleteTeamFromFirebase();
                        Log.d(TAG, "remove team name: " + mPresenter.getTeam().getName());
                        mPresenter.openMyTeamFragment();
                        dialog.dismiss();
                    })
                    .setNegativeButton(Constants.NO, (dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
//        builder.setMessage(R.string.confirm_dialog_message)
//                .setCancelable(true)
//                .setPositiveButton(Constants.YES, (dialog, id) -> {
//                    if (isPlayer) {
//                        mPresenter.getTeamPlayer().remove(mPlayerAdapter.getRow_index());
//                        mPresenter.updateFirebaseData();
//                        mPlayerAdapter.updateData();
//                    } else {
//                        mPresenter.getTeams().remove(mPresenter.getTeam());
//                        mPresenter.deleteTeamFromFirebase();
//                        Log.d(TAG, "remove team name: " + mPresenter.getTeam().getName());
//                        mPresenter.openMyTeamFragment();
//                    }
//                    dialog.dismiss();
//                })
//                .setNegativeButton(Constants.NO, (dialog, id) -> {
//                    //player missed shot
//                    dialog.dismiss();
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
    }

    @Override
    public void openMyTeamFragmentUi() {

        getFragmentManager().popBackStack("MyTeam", 0);
    }

    public static Intent newIntent(String name, String onCourtPosition, int backNumber, String imageUrl) {
        Intent intent = new Intent();
        intent.putExtra(NEW_PLAYER_NAME, name);
        intent.putExtra(NEW_PLAYER_ONCOURT_POSITION, onCourtPosition);
        intent.putExtra(NEW_PLAYER_BACK_NUMBER, String.valueOf(backNumber));
        Log.d(TAG, "new intent image: " + imageUrl);
        intent.putExtra(NEW_PLAYER_AVATAR, imageUrl);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == NEW_DIALOG_REQUEST_CODE) {
            String name = data.getStringExtra(NEW_PLAYER_NAME);
            String onCourtPosition = data.getStringExtra(NEW_PLAYER_ONCOURT_POSITION);
            String backNumber = data.getStringExtra(NEW_PLAYER_BACK_NUMBER);
            String imageUrl = data.getStringExtra(NEW_PLAYER_AVATAR);

            Log.d(TAG, "onActivityResult greeting: " + name + "\n" + "\n" + onCourtPosition + "\n" + backNumber + "\n" + imageUrl);
            if (name == null) {
                Log.d(TAG, "it is null: ");
            } else {
                mPresenter.setNewPlayer(name, onCourtPosition, Integer.parseInt(backNumber), imageUrl);
                mPresenter.getTeamPlayer().add(mPresenter.getNewPlayer());
                mPresenter.updateFirebaseData();
                mPresenter.updateData();
            }
        } else if (requestCode == EDIT_DIALOG_REQUEST_CODE) {
            Log.d(TAG, "EDIT_DIALOG_REQUEST_CODE: " + data.getStringExtra(NEW_PLAYER_AVATAR));
            mPresenter.updateFirebaseData();
            mPresenter.updateData();
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();
            mImageBitmap = ExifUtil.normalizeImageForUri(getActivity(), mImageUri);
            Log.d(TAG, "onActivityResult: " + mImageBitmap);
            mTeamLogo.setImageBitmap(mImageBitmap);
        }
    }

    @Override
    public void updateDataUi() {
        mPlayerAdapter.updateData();
    }

    @Override
    public String getTeamName() {
        return mTeamName.getText().toString();
    }

    @Override
    public void onPause() {

        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void showToastMessageUi(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}























