package com.mike.projectboxscore.NewTeam.NewPlayerDialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mike.projectboxscore.MainActivity;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class NewPlayerDialog extends DialogFragment implements NewPlayerDialogContract.View {

    private static final String TAG = "NewPlayerDialog";

    private EditText mPlayerName;
    private EditText mEmail;
    private EditText mBackNumber;
    private ImageView mConfirmButton;
    private ImageView mDismissButton;
    private Spinner mPosition;

    private NewPlayerDialogContract.Presenter mPresenter;

    @Override
    public void setPresenter(NewPlayerDialogContract.Presenter surfaceViewPresenter) {
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

        mPresenter.setPositionSpinner();
        mConfirmButton.setOnClickListener(v -> {
            String playerName = mPlayerName.getText().toString();
            String email = mEmail.getText().toString();
            int backNumber = -1;
            if (!mBackNumber.getText().toString().equals("")) {
                backNumber = Integer.parseInt(mBackNumber.getText().toString());
            }
            String position = mPosition.getSelectedItem().toString();
            if (playerName != null && email != null && backNumber != -1 && position != null) {
                mPresenter.setNewPlayerInfo(mPlayerName.getText().toString(),
                        mEmail.getText().toString(),
                        Integer.parseInt(mBackNumber.getText().toString()),
                        mPosition.getSelectedItem().toString());
                dismiss();
            } else {
                Toast.makeText(getActivity(), "Please enter player info!", Toast.LENGTH_SHORT).show();
            }

        });
        mDismissButton.setOnClickListener(v ->
                {
                    mPresenter.deletePlayer();
                    dismiss();
                }


        );


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
