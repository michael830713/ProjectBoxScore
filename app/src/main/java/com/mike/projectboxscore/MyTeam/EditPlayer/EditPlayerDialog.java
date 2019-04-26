package com.mike.projectboxscore.MyTeam.EditPlayer;

import android.app.Activity;
import android.content.Intent;
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
import com.mike.projectboxscore.NewTeam.NewPlayerDialog.NewPlayerDialogContract;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPlayerDialog extends DialogFragment implements EditPlayerDialogContract.View {

    private static final String TAG = "NewPlayerDialog";

    private EditText mPlayerName;
    private EditText mEmail;
    private EditText mBackNumber;
    private ImageView mConfirmButton;
    private ImageView mDismissButton;
    private Spinner mPosition;

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
                        mPresenter.updatePlayerInfo(playerName, email, backNumber, position);
                        sendResult(null, null, null, 0, null);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please enter player info!", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.imageViewDismiss:

                    sendResult(null, null, null, -1, null);
                    dismiss();
                    break;
            }
        }
    };

    private void sendResult(String name, String email, String onCourtPosition, int backNumber, String imageUrl) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = NewTeamFragment.newIntent(name, email, onCourtPosition, backNumber, imageUrl);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
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
    public void showPlayerUi(ArrayList<PlayerStats> playerOnBench) {
    }

    @Override
    public void changePlayerUi(PlayerStats playerToEnterGame) {
        dismiss();
    }
}
