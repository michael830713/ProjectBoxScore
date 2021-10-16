package com.mike.projectboxscore.newgame;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.projectboxscore.datas.Team;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private NewGameContract.Presenter mPresenter;
    private ArrayList<Team> mTeams;
    private int mRowIndex = -1;

    public TeamAdapter(NewGameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_team_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder teamViewHolder, int i) {
        teamViewHolder.teamName.setText(mTeams.get(i).getName());
        teamViewHolder.constraintLayout.setOnClickListener(v -> {
            mRowIndex = i;
            mPresenter.showPlayersOnTeam(mRowIndex);
            notifyDataSetChanged();
        });
        if (mRowIndex == i) {
            highlightSelectedPlayer(teamViewHolder.constraintLayout);
        } else {
            notHighlightSelectedPlayer(teamViewHolder.constraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {

        private TextView teamName;
        private ConstraintLayout constraintLayout;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.textViewTeamName);
            constraintLayout = itemView.findViewById(R.id.newTeamsConstraintLayout);
        }
    }

    public void setmTeams(ArrayList<Team> teams) {
        this.mTeams = teams;
        notifyDataSetChanged();
    }

    public void highlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundResource(R.drawable.log_background_orange);

    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundResource(R.drawable.log_background_grey);
    }

}
