package com.mike.projectboxscore.console;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.PlayerStats;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OnCourtPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainConsoleViewContract.Presenter mPresenter;
    int row_index = 0;
    private static final int playerViewHolder = 0;
    private static final int opponentViewHolder = 1;
    private ArrayList<PlayerStats> mPlayers;
    private Context mContext;

    public OnCourtPlayerAdapter(MainConsoleViewContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case playerViewHolder:
                return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.viewholder_players, viewGroup, false));
            case opponentViewHolder:
                return new OpponentViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.viewholder_opponent, viewGroup, false));
            default:
                return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.viewholder_players, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position != 5) {
            return playerViewHolder;
        } else {
            return opponentViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        switch (viewHolder.getItemViewType()) {
            case playerViewHolder:
                PlayerViewHolder playerViewHolder1 = (PlayerViewHolder) viewHolder;
                playerViewHolder1.mPlayerName.setText(mPlayers.get(i).getName());
                playerViewHolder1.mBackNumber.setText("" + mPlayers.get(i).getBackNumber());
                playerViewHolder1.mOnCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());

                Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(Constants.PLAYER_AVATAR_DIMEN, Constants.PLAYER_AVATAR_DIMEN).centerCrop().into(playerViewHolder1.mPlayerAvatar);

                playerViewHolder1.mConstraintLayout.setOnClickListener(v -> {
                    row_index = i;
                    notifyDataSetChanged();
                });
                if (row_index == i) {
                    highlightSelectedPlayer(playerViewHolder1.mConstraintLayout, playerViewHolder1.mPlayerAvatarFrame);
                } else {
                    notHighlightSelectedPlayer(playerViewHolder1.mConstraintLayout, playerViewHolder1.mPlayerAvatarFrame);
                }
                break;
            case opponentViewHolder:
                OpponentViewHolder opponentViewHolder = (OpponentViewHolder) viewHolder;
                opponentViewHolder.opponentName.setText(mPlayers.get(i).getName());
                opponentViewHolder.mConstraintLayout.setOnClickListener(v -> {
                    row_index = i;
                    notifyDataSetChanged();
                });
                if (row_index == i) {
                    highlightSelectedPlayer(opponentViewHolder.mConstraintLayout, null);
                    opponentViewHolder.opponentName.setTextColor(ContextCompat.getColor(mContext, R.color.selected_white));
                } else {
                    notHighlightSelectedPlayer(opponentViewHolder.mConstraintLayout, null);
                    opponentViewHolder.opponentName.setTextColor(ContextCompat.getColor(mContext, R.color.btnColor));

                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        ImageView mPlayerAvatarFrame;
        ImageView mPlayerAvatar;
        TextView mPlayerName;
        TextView mOnCourtPosition;
        TextView mBackNumber;
        ConstraintLayout mConstraintLayout;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlayerAvatarFrame = itemView.findViewById(R.id.imageViewPlayerAvatar);
            mPlayerAvatar = itemView.findViewById(R.id.pic);
            mPlayerName = itemView.findViewById(R.id.textView_player_name);
            mOnCourtPosition = itemView.findViewById(R.id.textView_OnCourtPosition);
            mBackNumber = itemView.findViewById(R.id.textView_number);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout_onCourt_players);

        }
    }

    public class OpponentViewHolder extends RecyclerView.ViewHolder {
        TextView opponentName;
        ConstraintLayout mConstraintLayout;

        public OpponentViewHolder(@NonNull View itemView) {
            super(itemView);
            opponentName = itemView.findViewById(R.id.textView_opponent_name);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout_onCourt_players);
        }
    }

    public void highlightSelectedPlayer(ConstraintLayout constraintLayout, ImageView frame) {
        constraintLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.test_selected_orange));
        if (frame != null) {
            frame.setColorFilter(ContextCompat.getColor(mContext, R.color.test_selected_orange));
        }
    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout, ImageView frame) {
        constraintLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.log_background_grey));
        if (frame != null) {
            frame.setColorFilter(ContextCompat.getColor(mContext, R.color.log_background_grey));
        }
    }

    public PlayerStats getCurrentPlayer() {
        return mPlayers.get(row_index);
    }

    public void setPlayers(ArrayList<PlayerStats> players) {
        mPlayers = sortPlayersByPosition(players);
        notifyDataSetChanged();
    }

    private ArrayList<PlayerStats> sortPlayersByPosition(ArrayList<PlayerStats> players) {
        ArrayList<PlayerStats> newPlayers = new ArrayList<>();
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals(Constants.GUARD)) {
                newPlayers.add(playerStats);
            }
        }
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals(Constants.FORWARD)) {
                newPlayers.add(playerStats);
            }
        }
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals(Constants.CENTER)) {
                newPlayers.add(playerStats);
            }
        }
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals(Constants.OPPONENT)) {
                newPlayers.add(playerStats);
            }
        }
        return newPlayers;
    }

}
