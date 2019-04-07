package com.mike.projectboxscore.mainConsole;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsolePresenter implements MainConsoleViewContract.Presenter {

    MainConsoleViewContract.View mView;

    public MainConsolePresenter(MainConsoleViewContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);

    }

    @Override
    public void selectPlayer(int position) {
        mView.showSelectedPlayer();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void doNormalMode(int screenWidth, int screenHeight) {

    }

    @Override
    public void doFullscreenMode(int screenWidth, int screenHeight) {

    }

    @Override
    public void setScreenInfo(int screenWidth, int screenHeight) {

    }

    @Override
    public void setVideoInfo(int videoWidth, int videoHeight) {

    }

    @Override
    public void calculateAndUpdateScore(int addScore) {
        mView.updateScoreUi(addScore);
    }

    @Override
    public void requestLandscape() {

    }

    @Override
    public void requestPortrait() {

    }

    @Override
    public void start() {

    }
}
