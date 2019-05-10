package com.mike.projectboxscore.console.substitution;

import com.mike.projectboxscore.datas.PlayerStats;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface SubContract {
    interface View extends BaseView<Presenter> {
        void showPlayerUi(ArrayList<PlayerStats> playerOnBench);

        void changePlayerUi(PlayerStats playerToEnterGame);
    }

    interface Presenter extends BasePresenter {
        void showPlayer();

        void changePlayer(int rowIndex);

        void setToBeReplacedPlayer(PlayerStats playerToEnterGame);
    }
}
