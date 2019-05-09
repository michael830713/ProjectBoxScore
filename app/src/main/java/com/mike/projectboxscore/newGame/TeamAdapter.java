package com.mike.projectboxscore.newGame;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private NewGameContract.Presenter mPresenter;
    private ArrayList<Team> mTeams;
    private int row_index = -1;

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
            row_index = i;
            mPresenter.showPlayersOnTeam(row_index);
            notifyDataSetChanged();
        });
        if (row_index == i) {
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

    public void setmTeams(ArrayList<Team> mTeams) {
        this.mTeams = mTeams;
        notifyDataSetChanged();
    }

    public void highlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundResource(R.drawable.log_background_orange);

    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundResource(R.drawable.log_background_grey);
    }

}
