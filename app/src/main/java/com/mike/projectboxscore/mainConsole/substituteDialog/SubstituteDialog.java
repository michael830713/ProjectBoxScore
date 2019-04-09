package com.mike.projectboxscore.mainConsole.substituteDialog;

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

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.OnCourtPlayerAdapter;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class SubstituteDialog extends DialogFragment implements SubContract.View {

    private static final String TAG = "SubstituteDialog";

    private SubContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private SubstituteAdapter mAdapter;

    @Override
    public void setPresenter(SubContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SubstituteAdapter(mPresenter);
        Log.d(TAG, "Dialog onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.substitute_dialog, container, false);
        mRecyclerView = view.findViewById(R.id.subPlayers);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(playerLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.showPlayer();

    }

    @Override
    public void showPlayerUi(ArrayList<PlayerStats> playerOnBench) {
        mAdapter.setPlayers(playerOnBench);
    }

    @Override
    public void changePlayerUi(PlayerStats playerToEnterGame) {
        dismiss();
    }
}
