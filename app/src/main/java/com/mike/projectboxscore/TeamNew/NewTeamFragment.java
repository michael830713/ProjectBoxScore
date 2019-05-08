package com.mike.projectboxscore.TeamNew;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.ExifUtil;
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.NewPlayerDialog;
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.NewPlayerDialogPresenter;
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewTeamFragment extends Fragment implements NewTeamContract.View {

    private static final String TAG = "NewTeamFragment";

    private static final String NEW_PLAYER_NAME = "playerMessage";
    private static final String NEW_PLAYER_EMAIL = "playerEmail";
    private static final String NEW_PLAYER_ONCOURT_POSITION = "playerOnCourtPosition";
    private static final String NEW_PLAYER_BACK_NUMBER = "playerBackNumber";
    private static final String NEW_PLAYER_IMAGE_URI = "playerImageUri";

    private static final int TARGET_FRAGMENT_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 5;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 40;

    NewTeamContract.Presenter mPresenter;
    private RecyclerView mPlayerRecyclerView;
    private EditText mTeamName;
    private ImageView mTeamLogo;
    private ImageView mButtonAddPlayer;
    private ImageView mButtonFinishCreateTeam;
    private ImageView mButtonDeleteTeam;

    private Uri mImageUri;
    private Bitmap mImageBitmap;

    private NewPlayerAdapter mPlayerAdapter;

    @Override
    public void setPresenter(NewTeamContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    public static NewTeamFragment newInstance() {
        return new NewTeamFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerAdapter = new NewPlayerAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_team, container, false);

        mPlayerRecyclerView = root.findViewById(R.id.recyclerViewNewPlayerList);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mPlayerRecyclerView.setLayoutManager(playerLayoutManager);
        mPlayerRecyclerView.setAdapter(mPlayerAdapter);

        mTeamName = root.findViewById(R.id.editTextNewTeamName);
        mTeamLogo = root.findViewById(R.id.imageViewLogo);
        mButtonAddPlayer = root.findViewById(R.id.imageViewAddButton);
        mButtonFinishCreateTeam = root.findViewById(R.id.imageViewNext);
        mButtonDeleteTeam = root.findViewById(R.id.imageViewDelete);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonAddPlayer.setOnClickListener(newTeamOnClickListener);
        mButtonFinishCreateTeam.setOnClickListener(newTeamOnClickListener);
        mTeamLogo.setOnClickListener(newTeamOnClickListener);
        mButtonDeleteTeam.setVisibility(View.INVISIBLE);

    }

    private View.OnClickListener newTeamOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageViewAddButton:
                    Log.d(TAG, "onClick: ");
                    mPresenter.showNewPlayerDialog();

                    break;
                case R.id.imageViewNext:
                    if (mTeamName.getText().toString().isEmpty()) {
                        showToastMessageUi("Please enter team name!");
                    } else {
                        if (mImageUri != null) {
                            ProgressDialog pd = new ProgressDialog(getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                            pd.setMessage("uploading image...");
                            pd.show();
                            mPresenter.uploadFile(mImageUri, getFileExtention(mImageUri), new PlayerAvatarUploadCallback() {
                                @Override
                                public void loadGameCallBack(String imageLink) {
                                    pd.dismiss();
                                    mPresenter.createNewTeam(imageLink);
                                    mPresenter.openMyTeamFragment();
                                }
                            });
                        } else {
                            mPresenter.createNewTeam(null);
                            mPresenter.openMyTeamFragment();
                        }

                    }
                    break;

                case R.id.imageViewLogo:
                    checkGalleryPermissionAndOpenGallery();
                    break;

            }
        }
    };

    @Override
    public void showNewPlayerUi() {
        NewPlayerDialog newPlayerDialog = new NewPlayerDialog();
        NewPlayerDialogPresenter newPlayerDialogPresenter = new NewPlayerDialogPresenter(newPlayerDialog);
        newPlayerDialog.setPresenter(newPlayerDialogPresenter);
        newPlayerDialog.setTargetFragment(this, TARGET_FRAGMENT_REQUEST_CODE);
        newPlayerDialog.show(getFragmentManager(), "createPlayer");

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

    @Override
    public void openMyTeamFragmentUi() {

        getFragmentManager().popBackStack("MyTeam", 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == TARGET_FRAGMENT_REQUEST_CODE) {
            String name = data.getStringExtra(NEW_PLAYER_NAME);
            String email = data.getStringExtra(NEW_PLAYER_EMAIL);
            String onCourtPosition = data.getStringExtra(NEW_PLAYER_ONCOURT_POSITION);
            String backNumber = data.getStringExtra(NEW_PLAYER_BACK_NUMBER);
            String imageUrl = data.getStringExtra(NEW_PLAYER_IMAGE_URI);

            Log.d(TAG, "onActivityResult greeting: " + name + "\n" + email + "\n" + onCourtPosition + "\n" + backNumber);
            if (name == null) {
                Log.d(TAG, "it is null: ");
            } else {
                mPresenter.setNewPlayer(name, email, onCourtPosition, Integer.parseInt(backNumber), imageUrl);
                mPresenter.getTeamPlayer().add(mPresenter.getNewPlayer());
                mPresenter.updateData();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();
            mImageBitmap = ExifUtil.normalizeImageForUri(getActivity(), mImageUri);
            Log.d(TAG, "onActivityResult: " + mImageBitmap);
            mTeamLogo.setImageBitmap(mImageBitmap);
        }
    }

    public static Intent newIntent(String name, String onCourtPosition, int backNumber, String imageUrl) {
        Intent intent = new Intent();
        intent.putExtra(NEW_PLAYER_NAME, name);
        intent.putExtra(NEW_PLAYER_ONCOURT_POSITION, onCourtPosition);
        intent.putExtra(NEW_PLAYER_BACK_NUMBER, String.valueOf(backNumber));
        intent.putExtra(NEW_PLAYER_IMAGE_URI, imageUrl);

        return intent;
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
    public void updateDataUi() {
        mPlayerAdapter.updateData(mPresenter.getTeamPlayer());
    }

    @Override
    public String getTeamName() {
        return mTeamName.getText().toString();
    }


    public String getFileExtention(Uri mImageUri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(mImageUri));
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























