package com.mike.projectboxscore.boxScore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class BoxScoreAdapter extends RecyclerView.Adapter<BoxScoreAdapter.PlayerViewHolder> {

    private static final String TAG = "BoxScoreAdapter";

    private BoxScoreViewContract.Presenter mPresenter;
    private ArrayList<PlayerStats> mPlayerStats;

    public BoxScoreAdapter(BoxScoreViewContract.Presenter presenter, ArrayList<PlayerStats> playerStats) {
        mPresenter = presenter;
        mPlayerStats = playerStats;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_boxscore, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {
        Log.d(TAG, "player size: " + mPlayerStats.size());
        Log.d(TAG, "points: " + mPlayerStats.get(i).getPoints());
        Log.d(TAG, "rebounds: " + mPlayerStats.get(i).getRebounds());
        playerViewHolder.name.setText(mPlayerStats.get(i).getName());
        playerViewHolder.points.setText(String.valueOf(mPlayerStats.get(i).getPoints()));
        playerViewHolder.rebounds.setText(String.valueOf(mPlayerStats.get(i).getRebounds()));
        playerViewHolder.assist.setText(String.valueOf(mPlayerStats.get(i).getAssists()));
        playerViewHolder.steal.setText(String.valueOf(mPlayerStats.get(i).getSteals()));
        playerViewHolder.block.setText(String.valueOf(mPlayerStats.get(i).getBlocks()));

        playerViewHolder.fieldGoalMade.setText(String.valueOf(mPlayerStats.get(i).getShotMade()));
        playerViewHolder.fieldGoalAttempt.setText(String.valueOf(mPlayerStats.get(i).getShotTaken()));

        playerViewHolder.threePointsMade.setText(String.valueOf(mPlayerStats.get(i).getThreePointShotMade()));
        playerViewHolder.threePointsAttempt.setText(String.valueOf(mPlayerStats.get(i).getThreePointShotTaken()));

        playerViewHolder.freeThrowMade.setText(String.valueOf(mPlayerStats.get(i).getFreeThrowMade()));
        playerViewHolder.freeThrowAttempt.setText(String.valueOf(mPlayerStats.get(i).getFreeThrowTaken()));

        playerViewHolder.offensiveRebound.setText(String.valueOf(mPlayerStats.get(i).getOffensiveRebounds()));
        playerViewHolder.defensiveRebound.setText(String.valueOf(mPlayerStats.get(i).getDefensiveRebounds()));
        playerViewHolder.turnOver.setText(String.valueOf(mPlayerStats.get(i).getTurnOvers()));
        playerViewHolder.personalFoul.setText(String.valueOf(mPlayerStats.get(i).getFouls()));

        Log.d(TAG, mPlayerStats.get(0).getName() + "assist: " + mPlayerStats.get(0).getAssists());

    }

    @Override
    public int getItemCount() {
//        if (mPlayerStats != null) {
            return mPlayerStats.size();
//        } else {
//            return 0;
//        }

    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView points;
        TextView rebounds;
        TextView assist;
        TextView steal;
        TextView block;
        TextView fieldGoalMade;
        TextView fieldGoalAttempt;
        TextView fieldGoalPercentage;
        TextView threePointsMade;
        TextView threePointsAttempt;
        TextView threePointsPercentage;
        TextView freeThrowMade;
        TextView freeThrowAttempt;
        TextView freeThrowPercentage;
        TextView offensiveRebound;
        TextView defensiveRebound;
        TextView turnOver;
        TextView personalFoul;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_player);
            points = itemView.findViewById(R.id.text_view_pts);
            rebounds = itemView.findViewById(R.id.textViewReb);
            assist = itemView.findViewById(R.id.textViewAst);
            steal = itemView.findViewById(R.id.textViewSteal);
            block = itemView.findViewById(R.id.textViewBlock);
            fieldGoalMade = itemView.findViewById(R.id.textViewFieldGoalMade);
            fieldGoalAttempt = itemView.findViewById(R.id.textViewFieldGoalAttempt);
            fieldGoalPercentage = itemView.findViewById(R.id.textViewFieldGoalPercentage);
            threePointsMade = itemView.findViewById(R.id.textViewThreePointsMade);
            threePointsAttempt = itemView.findViewById(R.id.textViewThreePointsAttempt);
            threePointsPercentage = itemView.findViewById(R.id.textViewThreePointsPercentage);
            freeThrowMade = itemView.findViewById(R.id.textViewFreeThrowMade);
            freeThrowAttempt = itemView.findViewById(R.id.textViewFreeThrowAttempt);
            freeThrowPercentage = itemView.findViewById(R.id.textViewFreeThrowPercentage);
            offensiveRebound = itemView.findViewById(R.id.textViewOffensiveRebound);
            defensiveRebound = itemView.findViewById(R.id.textViewDefensiveRebound);
            turnOver = itemView.findViewById(R.id.textViewTurnOver);
            personalFoul = itemView.findViewById(R.id.textViewPersonalFoul);

        }
    }

}
