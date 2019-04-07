package com.mike.projectboxscore.mainConsole;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.Data.PlayerOnCourtStats;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class OnCourtPlayerAdapter extends RecyclerView.Adapter<OnCourtPlayerAdapter.PlayerViewHolder> {

    private MainConsoleViewContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<PlayerOnCourtStats> mPlayers;

    public OnCourtPlayerAdapter(MainConsoleViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_players, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {

        playerViewHolder.mPlayerName.setText(mPlayers.get(i).getName());
        playerViewHolder.mBackNumber.setText("#" + mPlayers.get(i).getBackNumber());
        playerViewHolder.mOnCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());


        playerViewHolder.mConstraintLayout.setOnClickListener(v -> {
            row_index = i;
            notifyDataSetChanged();
        });
        if (row_index == i) {
            highlightSelectedPlayer(playerViewHolder.mConstraintLayout);
        } else {
            notHighlightSelectedPlayer(playerViewHolder.mConstraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        ImageView mPlayerAvatar;
        TextView mPlayerName;
        TextView mOnCourtPosition;
        TextView mBackNumber;
        ConstraintLayout mConstraintLayout;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlayerAvatar = itemView.findViewById(R.id.imageViewPlayerAvatar);
            mPlayerName = itemView.findViewById(R.id.textView_player_name);
            mOnCourtPosition = itemView.findViewById(R.id.textView_OnCourtPosition);
            mBackNumber = itemView.findViewById(R.id.textView_number);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout_onCourt_players);

        }
    }

    public void highlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.parseColor("#689bed"));
    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.parseColor("#202020"));
    }

    public int getRow_index() {
        return row_index;
    }

    public void setPlayers(ArrayList<PlayerOnCourtStats> players) {
        mPlayers = players;
    }
}
