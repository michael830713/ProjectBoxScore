package com.mike.projectboxscore.boxScore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.projectboxscore.R;

public class BoxScoreAdapter extends RecyclerView.Adapter<BoxScoreAdapter.PlayerViewHolder> {

    private static final String TAG = "BoxScoreAdapter";

    private BoxScoreViewContract.Presenter mPresenter;



    public BoxScoreAdapter(BoxScoreViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_boxscore, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView points;
        TextView rebounds;
        TextView assist;
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
            rebounds = itemView.findViewById(R.id.textView_player_name);
            assist = itemView.findViewById(R.id.textViewAst);
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
