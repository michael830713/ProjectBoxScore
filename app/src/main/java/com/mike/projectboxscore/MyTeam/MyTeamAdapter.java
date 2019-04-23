package com.mike.projectboxscore.MyTeam;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.MainActivity;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

import info.hoang8f.android.segmented.SegmentedGroup;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.PlayerViewHolder> {

    private Context mContext;
    private MyTeamContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<Team> mTeams;

    public MyTeamAdapter(MyTeamContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    public MyTeamAdapter() {

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_myteam, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {

        playerViewHolder.teamName.setText(mTeams.get(i).getmName());

        playerViewHolder.roster.setOnClickListener(v -> {

            TeamPlayerAdapter teamPlayerAdapter = new TeamPlayerAdapter(mPresenter, mTeams.get(i));
            playerViewHolder.recyclerView.setAdapter(teamPlayerAdapter);
            playerViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            Drawable dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.divider_grey);

            playerViewHolder.recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        });
        playerViewHolder.games.setOnClickListener(v -> {
            mPresenter.showToast("game coming soon!");
            GameAdapter gameAdapter = new GameAdapter(mPresenter, mTeams.get(i).getmGames());
            playerViewHolder.recyclerView.setAdapter(gameAdapter);
            playerViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            Drawable dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.divider_grey);

            playerViewHolder.recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
//            TeamPlayerAdapter teamPlayerAdapter = new TeamPlayerAdapter(mPresenter,mTeams.get(i));
//            playerViewHolder.recyclerView.setAdapter(teamPlayerAdapter);
//            playerViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        });
//        playerViewHolder.buttonAddPlayer.setOnClickListener(v -> mPresenter.showNewPlayer());
        playerViewHolder.buttonEditTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = i;
                mPresenter.showEditTeam();
            }
        });
        playerViewHolder.roster.performClick();

    }

    @Override
    public int getItemCount() {
        if (mTeams != null) {
            return mTeams.size();
        } else {
            return 1;
        }

    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        SegmentedGroup segmented2;
        RecyclerView recyclerView;
        Button roster;
        Button games;
        TextView teamName;
        ImageView buttonAddPlayer;
        ImageView buttonEditTeam;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            segmented2 = (SegmentedGroup) itemView.findViewById(R.id.segmented2);
            segmented2.setTintColor(Color.parseColor("#F39A2C"));
            recyclerView = itemView.findViewById(R.id.recyclerViewPlayerOrGameList);
            roster = itemView.findViewById(R.id.button21);
            games = itemView.findViewById(R.id.button22);
            teamName = itemView.findViewById(R.id.textViewTeamName);
//            buttonAddPlayer = itemView.findViewById(R.id.imageViewAddButton);
            buttonEditTeam = itemView.findViewById(R.id.imageViewEdit);

        }
    }

    public void highlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.parseColor("#689bed"));
    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.parseColor("#202020"));
    }

    public Team getSelectedTeam() {
        return mTeams.get(row_index);
    }

    public int getRow_index() {
        return row_index;
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
