package com.mike.projectboxscore.MyTeam;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;
import com.mike.projectboxscore.NewTeam.NewTeamPresenter;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MyTeamFragment extends Fragment implements MyTeamContract.View {

    private static final String TAG = "MyTeamFragment";

    private static final int TARGET_FRAGMENT_REQUEST_CODE = 1;
    private static final String NEW_PLAYER_NAME = "playerMessage";
    private static final String NEW_PLAYER_EMAIL = "playerEmail";
    private static final String NEW_PLAYER_ONCOURT_POSITION = "playerOnCourtPosition";
    private static final String NEW_PLAYER_BACK_NUMBER = "playerBackNumber";

    MyTeamContract.Presenter mPresenter;
    private RecyclerView mTeamRecyclerView;
    private ImageView mAddTeamButton;

    private NewTeamPresenter mNewTeamPresenter;

    private MyTeamAdapter mTeamAdapter;

    @Override
    public void setPresenter(MyTeamContract.Presenter surfaceViewPresenter) {
        mPresenter = checkNotNull(surfaceViewPresenter);
    }

    public static MyTeamFragment newInstance() {
        return new MyTeamFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTeamAdapter = new MyTeamAdapter(mPresenter, getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myteam, container, false);
        mAddTeamButton = root.findViewById(R.id.imageViewAddTeam);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddTeamButton.setOnClickListener(newTeamOnClickListener);

    }

    private void setupRecyclerView(View view) {

        mTeamRecyclerView = view.findViewById(R.id.recyclerViewTeam);
        LinearLayoutManager teamLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mTeamRecyclerView.setLayoutManager(teamLayoutManager);
        mTeamAdapter.setTeams(mPresenter.getTeams());
        mTeamRecyclerView.setAdapter(mTeamAdapter);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider_vertical);
        mTeamRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mTeamRecyclerView);
        ScrollingPagerIndicator recyclerIndicator = view.findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(mTeamRecyclerView);
    }

    private View.OnClickListener newTeamOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openNewTeamFragment();
        }
    };

    @Override
    public void openNewTeamFragmentUi() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        NewTeamFragment fragment = NewTeamFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "NewTeam").addToBackStack("NewTeam");
        fragmentTransaction.commit();
        mNewTeamPresenter = new NewTeamPresenter(fragment,mPresenter.getTeams());
    }

    @Override
    public void showNewPlayerUi() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public static Intent newIntent(String name, String email, String onCourtPosition, int backNumber) {
        Intent intent = new Intent();
        return intent;
    }

    @Override
    public void updateDataUi() {
        mTeamAdapter.updateData(mPresenter.getTeams());
    }

    @Override
    public String getTeamName() {
        return null;
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























