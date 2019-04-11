package com.mike.projectboxscore.newGame;

import android.support.annotation.NonNull;
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

    public TeamAdapter(NewGameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_team, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder teamViewHolder, int i) {
        teamViewHolder.mTeamName.setText(mTeams.get(i).getmName());
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public void setmTeams(ArrayList<Team> mTeams) {
        this.mTeams = mTeams;
        notifyDataSetChanged();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        private TextView mTeamName;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            mTeamName = itemView.findViewById(R.id.textViewTeamName);
        }
    }
}
