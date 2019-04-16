package com.mike.projectboxscore.boxScore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.OnCourtPlayerAdapter;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubContract;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class BoxSoreFragment extends Fragment implements BoxScoreViewContract.View {

    private static final String TAG = "BoxSoreFragment";

    BoxScoreViewContract.Presenter mPresenter;
    SubContract.Presenter mSubPresenter;
    private OnCourtPlayerAdapter mOnCourtPlayerAdapter;
    private BoxScoreAdapter mBoxScoreAdapter;
    RecyclerView mBoxRecyclerView;
    RecyclerView mLogRecyclerView;
    TextView mTextViewAwayScore;
    TextView mTextViewHomeScore;
    private JoystickView mJoystickView;
    private int mAwayScore = 0;
    private int mHomeScore = 0;
    private ImageView m2Pts;
    private ImageView m3Pts;
    private ImageView mFreeThrows;
    private ImageView mDreb;
    private ImageView mOreb;
    private ImageView mSteal;
    private ImageView mAssist;
    private ImageView mTurnOver;
    private ImageView mSubstitute;
    private ImageView mFoul;
    private ImageView mBlock;
    private ImageView mSettings;
    private ImageView mBackButton;

    private static final String TWO_POINTS_MADE = "2 Pts Made";
    private static final String THREE_POINTS_MADE = "3 Pts Made";
    private static final String TWO_POINTS_MISS = "2 Pts Miss";
    private static final String THREE_POINTS_MISS = "3 Pts Miss";
    private static final String FREE_THROW_MADE = "Free Throw Made";
    private static final String FREE_THROW_MISS = "Free Throw Miss";
    private static final String OFFENSIVE_REBOUND = "O Rebound";
    private static final String DEFENSIVE_REBOUND = "D Rebound";
    private static final String ASSIST = "Assist";
    private static final String TURN_OVER = "Turn Over";
    private static final String FOUL = "Foul";
    private static final String STEAL = "Steal";
    private static final String BLOCK = "Block";

    public BoxSoreFragment() {
        // Requires empty public constructor
    }

    public static BoxSoreFragment newInstance() {
        return new BoxSoreFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBoxScoreAdapter = new BoxScoreAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_box_score, container, false);

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

    }

    private int updateAddAwayScore(int addPoints) {
        mAwayScore = mAwayScore + addPoints;
        return mAwayScore;
    }

    private int updateAddHomeScore(int addPoints) {
        mHomeScore = mHomeScore + addPoints;
        return mHomeScore;
    }

    private int updateMinusAwayScore(int addPoints) {
        mAwayScore = mAwayScore - addPoints;
        return mAwayScore;
    }

    private int updateMinusHomeScore(int addPoints) {
        mHomeScore = mHomeScore - addPoints;
        return mHomeScore;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
//
//        mPresenter.setOnCourtPlayers();
//
//        mOnCourtPlayerAdapter.setPlayers(mPresenter.getPlayers());
//        Log.d(TAG, "onResume: " + mPresenter.getPlayers());
        super.onResume();
    }

    @Override
    public void setPresenter(BoxScoreViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }

    @Override
    public void initView() {

    }
}
