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

public class OnCourtPlayerAdapter extends RecyclerView.Adapter<OnCourtPlayerAdapter.PlayerViewHolder> {

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
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_players, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {

        playerViewHolder.mPlayerName.setText(mPlayers.get(i).getName());
        if (mPlayers.get(i).getBackNumber() == -1) {
            playerViewHolder.mPlayerName.setText(mPlayers.get(i).getName().toUpperCase());
            playerViewHolder.mPlayerName.setTextColor(Color.parseColor("#e00007"));
        } else {
            playerViewHolder.mPlayerName.setTextColor(Color.parseColor("#ffffff"));
        }
        playerViewHolder.mBackNumber.setText("#" + mPlayers.get(i).getBackNumber());
        playerViewHolder.mOnCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());

        Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(50, 50).centerCrop().into(playerViewHolder.mPlayerAvatar);

        playerViewHolder.mConstraintLayout.setOnClickListener(v -> {
            row_index = i;
            notifyDataSetChanged();
        });
        if (row_index == i) {
            highlightSelectedPlayer(playerViewHolder.mConstraintLayout,playerViewHolder.mPlayerAvatarFrame);
        } else {
            notHighlightSelectedPlayer(playerViewHolder.mConstraintLayout,playerViewHolder.mPlayerAvatarFrame);
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

    public void highlightSelectedPlayer(ConstraintLayout constraintLayout, ImageView frame) {
        constraintLayout.setBackgroundColor(Color.parseColor("#689bed"));
        frame.setColorFilter(ContextCompat.getColor(mContext, R.color.selected_blue));

    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout, ImageView frame) {
        constraintLayout.setBackgroundColor(Color.parseColor("#202020"));
        frame.setColorFilter(ContextCompat.getColor(mContext, R.color.log_background_grey));

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
