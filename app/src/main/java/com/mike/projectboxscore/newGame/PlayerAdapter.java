package com.mike.projectboxscore.newGame;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private static final String TAG = "PlayerAdapter";

    private NewGameContract.Presenter mPresenter;
    int row_index = 0;
    private Context mContext;
    private ArrayList<Player> mPlayers;

    public PlayerAdapter(NewGameContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    public PlayerAdapter() {

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_players, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {
        Player player = mPlayers.get(i);
        playerViewHolder.mPlayerName.setText(player.getName());
        playerViewHolder.mBackNumber.setText("" + player.getBackNumber());
        playerViewHolder.mOnCourtPosition.setText(player.getOnCourtPosition());
        Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(50, 50).centerCrop().into(playerViewHolder.mPlayerAvatar);
        Log.d(TAG, "player image: "+mPlayers.get(i).getImageUrl());
        playerViewHolder.mConstraintLayout.setOnClickListener(v -> {

            int onCourtPlayers = 0;
            for (int j = 0; j < mPlayers.size(); j++) {
                if (mPlayers.get(j).isOnCourt()) {
                    onCourtPlayers += 1;
                }
            }
            if (onCourtPlayers >= 5) {
                if (player.isOnCourt()) {
                    player.setOnCourt(!player.isOnCourt());
                } else {
                    mPresenter.showToast("Reached 5 players!!");
                }
            } else {
                player.setOnCourt(!player.isOnCourt());
            }
            notifyDataSetChanged();

        });
        if (player.isOnCourt()) {
            highlightSelectedPlayer(playerViewHolder.mConstraintLayout, playerViewHolder.mPlayerAvatarFrame);
//            playerViewHolder.mPlayerName.setTextColor(ContextCompat.getColor(mContext, R.color.btnColor));
//            playerViewHolder.mBackNumber.setTextColor(ContextCompat.getColor(mContext, R.color.btnColor));
//            notHighlightSelectedPlayer(playerViewHolder.mConstraintLayout);
        } else {
            notHighlightSelectedPlayer(playerViewHolder.mConstraintLayout, playerViewHolder.mPlayerAvatarFrame);
        }

    }

    @Override
    public int getItemCount() {
        if (mPlayers != null) {
            return mPlayers.size();
        } else {
            return 0;
        }

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
//        constraintLayout.setBackgroundColor(Color.parseColor("#689bed"));
        constraintLayout.setBackgroundResource(R.drawable.log_background_blue);
//        ContextCompat.getColor(mContext, R.color.green_500);
//        ImageViewCompat.setImageTintList(frame, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.selected_blue)));
        frame.setColorFilter(ContextCompat.getColor(mContext, R.color.selected_blue));

    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout, ImageView frame) {
        constraintLayout.setBackgroundResource(R.drawable.log_background_transparent);
        frame.setColorFilter(ContextCompat.getColor(mContext, R.color.deep_black));

//        frame.setColorFilter(R.color.deep_black, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public int getRow_index() {
        return row_index;
    }

    public void setPlayers(ArrayList<Player> players) {
        mPlayers = players;
        notifyDataSetChanged();
    }
}
















