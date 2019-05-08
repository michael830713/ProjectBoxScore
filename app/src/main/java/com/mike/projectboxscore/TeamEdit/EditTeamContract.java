package com.mike.projectboxscore.TeamEdit;

import android.net.Uri;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.TeamNew.NewPlayerDialog.PlayerAvatarUploadCallback;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

public interface EditTeamContract {

    interface View extends BaseView<EditTeamContract.Presenter> {

        void showToastMessageUi(String message);

        String getTeamName();

        void showNewPlayerUi();

        void openMyTeamFragmentUi();

        void updateDataUi();

        void showEditPlayerUi(Player player);

        void showConfirmDeleteDialogUi(boolean isPlayer);
    }

    interface Presenter extends BasePresenter {

        void setNewPlayer(String name, String onCourtPosition, int backNumber, String imageUrl);

        Player getNewPlayer();

        ArrayList<Player> getTeamPlayer();

        void updateData();

        void openMyTeamFragment();

        void showNewPlayerDialog();

        ArrayList<Team> getTeams();

        void showToast(String message);

        void showEditPlayerDialog(Player player);

        Team getTeam();

        void showConfirmDeleteDialog(boolean isPlayer);

        void updateFirebaseData();

        void uploadFile(Uri imageUri, String fileExtention, PlayerAvatarUploadCallback callback);

        void deleteTeamFromFirebase();

        void updateTeamInfo(String teamName, String imageLink);
    }
}














