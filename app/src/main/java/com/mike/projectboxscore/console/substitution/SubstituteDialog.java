package com.mike.projectboxscore.console.substitution;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mike.projectboxscore.datas.PlayerStats;
import com.mike.projectboxscore.R;

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
        mAdapter = new SubstituteAdapter(mPresenter,getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_substitite, container, false);
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
