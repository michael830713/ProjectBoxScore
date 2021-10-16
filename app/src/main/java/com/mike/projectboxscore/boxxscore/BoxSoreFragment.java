package com.mike.projectboxscore.boxxscore;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.R;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class BoxSoreFragment extends Fragment implements BoxScoreViewContract.View {

    private static final String TAG = "BoxSoreFragment";

    BoxScoreViewContract.Presenter mPresenter;
    private BoxScoreAdapter mBoxScoreAdapter;
    RecyclerView mBoxRecyclerView;
    ImageView imageViewHome;
    TextView mTextViewAwayScore;
    TextView mTextViewHomeScore;
    TextView mTextViewAwayTeamName;
    TextView mTextViewHomeTeamName;

    public BoxSoreFragment() {
        // Requires empty public constructor
    }

    public static BoxSoreFragment newInstance() {
        return new BoxSoreFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBoxScoreAdapter = new BoxScoreAdapter(mPresenter, mPresenter.getPlayerStats());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_box_score, container, false);
        mTextViewAwayScore = root.findViewById(R.id.textViewAwayScore);
        mTextViewHomeScore = root.findViewById(R.id.textViewHomeScore);

        mTextViewAwayTeamName = root.findViewById(R.id.textViewAwayTeam);
        mTextViewHomeTeamName = root.findViewById(R.id.textViewHomeTeam);

        imageViewHome = root.findViewById(R.id.imageViewHome);

        //setup the box recyclerView
        mBoxRecyclerView = root.findViewById(R.id.recyclerViewBoxScore);
        LinearLayoutManager playerLayoutManager = new LinearLayoutManager(getContext());
        mBoxRecyclerView.setLayoutManager(playerLayoutManager);
        mBoxRecyclerView.setAdapter(mBoxScoreAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewAwayScore.setText(String.valueOf(mPresenter.getAwayScore()));
        mTextViewHomeScore.setText(String.valueOf(mPresenter.getHomeScore()));

        mTextViewAwayTeamName.setText(mPresenter.getGame().getmMyTeamName());
        mTextViewHomeTeamName.setText(mPresenter.getGame().getmOpponent());

        if (mPresenter.getExit()) {
            imageViewHome.setVisibility(View.VISIBLE);
            imageViewHome.setOnClickListener(awesomeOnClickListener);
        } else {
            imageViewHome.setVisibility(View.INVISIBLE);
        }

    }

    private View.OnClickListener awesomeOnClickListener = v -> {

        mPresenter.openHome();
    };


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void openHomeUi() {
        getFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(BoxScoreViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }

}
