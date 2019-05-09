package com.mike.projectboxscore.TeamMain;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.hoang8f.android.segmented.SegmentedGroup;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.TeamViewHolder> {
    private static final String TAG = "MyTeamAdapter";
    private Context mContext;
    private MyTeamContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<Team> mTeams;

    public MyTeamAdapter(MyTeamContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }


    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new TeamViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_myteam, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final TeamViewHolder teamViewHolder, final int i) {

        teamViewHolder.teamName.setText(mTeams.get(i).getName());
        Picasso.get().load(mTeams.get(i).getmTeamLogoUrl()).placeholder(R.drawable.team_logo_placeholder).into(teamViewHolder.teamLogo);
        teamViewHolder.roster.setOnClickListener(v -> {

            TeamPlayerAdapter teamPlayerAdapter = new TeamPlayerAdapter(mPresenter, mTeams.get(i));
            teamViewHolder.playerOrGameRecyclerView.setAdapter(teamPlayerAdapter);
            teamViewHolder.playerOrGameRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        });

        teamViewHolder.games.setOnClickListener(v -> {
            GameAdapter gameAdapter = new GameAdapter(mPresenter, mPresenter.getGames());
            mPresenter.loadGameData(i, new GamesDataCallback() {
                @Override
                public void loadGameCallBack(ArrayList<Game> games) {
                    gameAdapter.updateData(games, true);
                }
            });

            teamViewHolder.playerOrGameRecyclerView.setAdapter(gameAdapter);
            teamViewHolder.playerOrGameRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        });
        teamViewHolder.buttonEditTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = i;
                mPresenter.showEditTeam();
            }
        });
        teamViewHolder.roster.performClick();

    }

    @Override
    public int getItemCount() {
        if (mTeams != null) {
            return mTeams.size();
        } else {
            return 1;
        }

    }

    @Override
    public void onViewRecycled(@NonNull TeamViewHolder holder) {
        super.onViewRecycled(holder);
        Log.d(TAG, "onViewRecycled: ");
        while (holder.playerOrGameRecyclerView.getItemDecorationCount() > 0) {
            holder.playerOrGameRecyclerView.removeItemDecorationAt(0);
        }

    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        SegmentedGroup segmented2;
        RecyclerView playerOrGameRecyclerView;
        Button roster;
        Button games;
        TextView teamName;
        ImageView buttonEditTeam;
        ImageView teamLogo;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            segmented2 = (SegmentedGroup) itemView.findViewById(R.id.segmented2);
            segmented2.setTintColor(Color.parseColor("#F39A2C"));
            playerOrGameRecyclerView = itemView.findViewById(R.id.recyclerViewPlayerOrGameList);
            roster = itemView.findViewById(R.id.button21);
            games = itemView.findViewById(R.id.button22);
            teamName = itemView.findViewById(R.id.textViewTeamName);
            teamLogo = itemView.findViewById(R.id.imageViewLogo);
            buttonEditTeam = itemView.findViewById(R.id.imageViewEdit);
            Drawable dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.divider_grey);
            playerOrGameRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        }
    }

    public Team getSelectedTeam() {
        return mTeams.get(row_index);
    }


    public void setTeams(ArrayList<Team> teams) {
        mTeams = teams;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Team> teamPlayers) {
        mTeams = teamPlayers;
        notifyDataSetChanged();
    }
}