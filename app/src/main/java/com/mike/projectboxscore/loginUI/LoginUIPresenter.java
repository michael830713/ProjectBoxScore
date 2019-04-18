package com.mike.projectboxscore.loginUI;

import android.support.v4.app.FragmentTransaction;

import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleFragment;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginUIPresenter implements LoginUIViewContract.Presenter {

    LoginUIViewContract.View mView;

    public LoginUIPresenter(LoginUIViewContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);

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
    public void demoNewTeam() {
        mView.demoNewTeamUi();
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
    public void calculateAndUpdateSurface() {

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
