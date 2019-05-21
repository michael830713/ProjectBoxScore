package com.mike.projectboxscore.main;

import android.content.Context;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.Team;
import com.mike.projectboxscore.FirebaseDataSource;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainPagePresenter implements MainPageContract.Presenter {

    private static final String TAG = "MainPagePresenter";
    private final Context mContext;

    MainPageContract.View mView;
    ArrayList<Team> mTeams = new ArrayList<>();

    public MainPagePresenter(MainPageContract.View view, Context context) {
        mView = checkNotNull(view, Constants.CHECK_VIEW_NOT_NULL);
        mView.setPresenter(this);
        mContext = context;

    }

    @Override
    public void demoNewGameView() {
        mView.demoNewGameViewUi();
    }

    @Override
    public void demoMyTeamView() {

        mView.demoMyTeamViewUi();
    }

    @Override
    public void demoLoginView() {
        mView.demoLoginViewUi();
    }

    @Override
    public void checkFirebaseData() {
        FirebaseDataSource.checkFirebaseData(mContext, teams -> mTeams = teams);
    }

    @Override
    public void setTeamCollection() {

        FirebaseDataSource.updateTeamInfo(new Team(Constants.INIT_TEAM_PATH));
    }

    @Override
    public ArrayList<Team> getTeams() {
        return mTeams;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {

    }
}
