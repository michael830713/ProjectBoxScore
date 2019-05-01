package com.mike.projectboxscore.mainConsole;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OnCourtPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainConsoleViewContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<PlayerStats> mPlayers;
    private Context mContext;

    public OnCourtPlayerAdapter(MainConsoleViewContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    public OnCourtPlayerAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.viewholder_players, viewGroup, false));
            case 1:
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
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                PlayerViewHolder playerViewHolder1 = (PlayerViewHolder) viewHolder;
                playerViewHolder1.mPlayerName.setText(mPlayers.get(i).getName());
//                if (mPlayers.get(i).getBackNumber() == -1) {
//                    playerViewHolder1.mPlayerName.setText(mPlayers.get(i).getName().toUpperCase());
//                    playerViewHolder1.mPlayerName.setTextColor(Color.parseColor("#e00007"));
//                } else {
//                    playerViewHolder1.mPlayerName.setTextColor(Color.parseColor("#ffffff"));
//                }
                playerViewHolder1.mBackNumber.setText("" + mPlayers.get(i).getBackNumber());
                playerViewHolder1.mOnCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());

                Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(50, 50).centerCrop().into(playerViewHolder1.mPlayerAvatar);

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
            case 1:
                OpponentViewHolder opponentViewHolder = (OpponentViewHolder) viewHolder;
                opponentViewHolder.opponentName.setText(mPlayers.get(i).getName());
                opponentViewHolder.mConstraintLayout.setOnClickListener(v -> {
                    row_index = i;
                    notifyDataSetChanged();
                });
                if (row_index == i) {
                    highlightSelectedPlayer(opponentViewHolder.mConstraintLayout, null);
                } else {
                    notHighlightSelectedPlayer(opponentViewHolder.mConstraintLayout, null);
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
        constraintLayout.setBackgroundColor(Color.parseColor("#689bed"));
        if (frame != null) {
            frame.setColorFilter(ContextCompat.getColor(mContext, R.color.selected_blue));

        }

    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout, ImageView frame) {
        constraintLayout.setBackgroundColor(Color.parseColor("#202020"));
        if (frame != null) {
            frame.setColorFilter(ContextCompat.getColor(mContext, R.color.log_background_grey));
        }
    }

    public int getRow_index() {
        return row_index;
    }

    public PlayerStats getCurrentPlayer() {
        return mPlayers.get(row_index);
    }

    public void setPlayers(ArrayList<PlayerStats> players) {
//        mPlayers = players;
        mPlayers = sortPlayers(players);
        notifyDataSetChanged();
    }

    private ArrayList<PlayerStats> sortPlayers(ArrayList<PlayerStats> players) {
        ArrayList<PlayerStats> newPlayers = new ArrayList<>();
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals("G")) {
                newPlayers.add(playerStats);
            }
        }
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals("F")) {
                newPlayers.add(playerStats);
            }
        }
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals("C")) {
                newPlayers.add(playerStats);
            }
        }
        for (PlayerStats playerStats : players) {
            if (playerStats.getOnCourtPosition().equals("O")) {
                newPlayers.add(playerStats);
            }
        }
        return newPlayers;
    }

}
