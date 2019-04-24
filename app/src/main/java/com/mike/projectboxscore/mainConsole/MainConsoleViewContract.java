/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mike.projectboxscore.mainConsole;

import android.view.SurfaceHolder;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

/**
 * Created by Wayne Chen on 2018/4/16.
 * <p>
 * This specifies the contract between the view and the presenter.
 */
public interface MainConsoleViewContract {

    interface View extends BaseView<Presenter> {

        void initView();

        void showSelectedPlayer();

        void showFullscreenModeUi(int videoWidth, int videoHeight);

        void showNormalModeUi(int videoWidth, int videoHeight);

        void addSurfaceHolderCallback(SurfaceHolder.Callback callback);

        void showMadeOrMissDialogUi(int addPoints);

        void showSubstituteDialogUi();

        void updateLogUi(int addScore, boolean isShotMade);

        void updateLogUi(String action);

        void removeLogUi();

        void showConfirmExitDialogUi();

        void openBoxScoreUi();

        void openExitBoxScoreUi();

        void updateScoreboardUi(int addScore);

        void updateScoreboardReturnUi(int addScore);

        void returnLastStepUi();

        int getAwayScore();

        int getHomeScore();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

//        void setMediaControllerPresenter(MediaControllerContract.Presenter presenter);

        void doNormalMode(int screenWidth, int screenHeight);

        void doFullscreenMode(int screenWidth, int screenHeight);

        void setScreenInfo(int screenWidth, int screenHeight);

        void setVideoInfo(int videoWidth, int videoHeight);

        void updatePlayerScores(int addPoints);

        void updatePlayerMisses(int addPoints);

        void updateLog(int addPoint, boolean isShotMade);

        void updateLog(String action);

        void removeLog();

        void updateScoreboard(int addPoint);

        void updateScoreboardReturn(int addPoint);

        void showMadeOrMissDialog( int addPoints);

        void showSubstituteDialog();

        void showConfirmExitDialog();

        void requestPortrait();

        void setupNewPlayer(String name, int backNumber, String onCourtPosition);

        void setupNewPlayer(String name, int backNumber, String onCourtPosition, boolean isOnCourt);

        void setPlayerOnCourt(int backNumber);

        void setPlayerOffCourt(int backNumber);

        void setOnCourtPlayers();

        void setOnBenchPlayers();


        ArrayList<PlayerStats> getPlayers();

        ArrayList<PlayerStats> getOnBenchPlayers();

        void setSelectedPlayer(PlayerStats playerStats);

        PlayerStats getSelectedPlayer();

        void playerMadeShot();

        void playerShoot();

        void playerMade3PtShot();

        void player3ptShoot();

        void playerMadeFreeThrow();

        void playerFreeThrowShoot();

        void playerMadeShotReturn();

        void playerShootReturn();

        void playerMade3PtShotReturn();

        void player3ptShootReturn();

        Game getGame();

        void playerMadeFreeThrowReturn();

        void playerFreeThrowReturn();

        void playerOffensiveRebounded(int amount);

        void playerDefensiveRebounded(int amount);

        void playerAssisted(int amount);

        void playerTurnedOver(int amount);

        void playerFouled(int amount);

        void playerstealed(int amount);

        void playerBlocked(int amount);

        void openBoxScore();

        void openExitBoxScore();

        void returnLastStep();

        void addNewGame();

        void updateFirebaseData();
    }
}
