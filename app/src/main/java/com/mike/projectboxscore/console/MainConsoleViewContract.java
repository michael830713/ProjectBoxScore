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

package com.mike.projectboxscore.console;

import com.mike.projectboxscore.datas.Action;
import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.datas.PlayerStats;
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

        void showMadeOrMissDialogUi(int addPoints);

        void showSubstituteDialogUi();

        void updateLogUi(int addScore, boolean isShotMade);

        void updateLogUi(Action action);

        void removeLogUi(int i);

        void showConfirmExitDialogUi();

        void openBoxScoreUi();

        void openExitBoxScoreUi();

        void updateScoreboardUi(int addScore);

        void updateScoreboardReturnUi(int addScore);

        void returnLastStepUi(int i);

        int getAwayScore();

        int getHomeScore();

        void showTutorialUi();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void updatePlayerScores(int addPoints);

        void updatePlayerMisses(int addPoints);

        void updateLog(int addPoint, boolean isShotMade);

        void updateLog(Action action);

        void removeLog(int i);

        void updateScoreboard(int addPoint);

        void updateScoreboardReturn(int addPoint);

        void showMadeOrMissDialog(int addPoints);

        void showSubstituteDialog();

        void showConfirmExitDialog();

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

        void returnLastStep(int i);

        void addNewGame();

        void updateFirebaseData();

        void setRebound();

        void setShotPersentage();

        void showTutorialDialog();

        Action setNewAction(int actionCode, String action);
    }
}
