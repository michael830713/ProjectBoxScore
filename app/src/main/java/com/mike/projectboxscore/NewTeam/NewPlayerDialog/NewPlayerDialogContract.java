package com.mike.projectboxscore.NewTeam.NewPlayerDialog;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface NewPlayerDialogContract {
    interface View extends BaseView<Presenter> {
        void showPlayerUi(ArrayList<PlayerStats> playerOnBench);

        void changePlayerUi(PlayerStats playerToEnterGame);

        void setPositionSpinnerUi();
    }

    interface Presenter extends BasePresenter {
        void showPlayer();

        void changePlayer(int rowIndex);

        void setPositionSpinner();

        void setToBeReplacedPlayer(PlayerStats playerToEnterGame);
    }
}
