package com.mike.projectboxscore.createteam;

import android.net.Uri;

import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.datas.Team;
import com.mike.projectboxscore.createteam.createplayer.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface NewTeamContract {

    interface View extends BaseView<NewTeamContract.Presenter> {


        void showToastMessageUi(String message);

        String getTeamName();


        void showNewPlayerUi();

        void openMyTeamFragmentUi();

        void updateDataUi();

    }

    interface Presenter extends BasePresenter {

        void setNewPlayer(String name, String email, String onCourtPosition, int backNumber,String imageUrl);

        Player getNewPlayer();

        ArrayList<Player> getTeamPlayer();

        void updateData();

        void addTeamToFirebase(Team team);

        void openMyTeamFragment();

        void createNewTeam(String imageLink);

        void showNewPlayerDialog();

        ArrayList<Team> getTeams();

        void showToast(String message);

        void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback);
    }
}
