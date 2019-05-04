package com.mike.projectboxscore.mainConsole.substituteDialog;

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

public class SubstituteAdapter extends RecyclerView.Adapter<SubstituteAdapter.PlayerViewHolder> {

    private static final String TAG = "SubstituteAdapter";

    private SubContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<PlayerStats> mPlayers;
    private Context mContext;

    public SubstituteAdapter(SubContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    public SubstituteAdapter() {

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
        playerViewHolder.mBackNumber.setText("" + mPlayers.get(i).getBackNumber());
        playerViewHolder.mOnCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());
        Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(50, 50).centerCrop().into(playerViewHolder.mPlayerAvatar);

        playerViewHolder.mConstraintLayout.setOnClickListener(v -> {
            row_index = i;
            mPresenter.changePlayer(row_index);
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
        if (mPlayers != null) {
            return mPlayers.size();
        } else {
            return 1;
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
//        frame.setColorFilter(ContextCompat.getColor(mContext, R.color.selected_blue));
        constraintLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.test_selected_orange));
        frame.setColorFilter(ContextCompat.getColor(mContext, R.color.test_selected_orange));

    }

    public void notHighlightSelectedPlayer(ConstraintLayout constraintLayout, ImageView frame) {
        constraintLayout.setBackgroundColor(Color.parseColor("#202020"));
        frame.setColorFilter(ContextCompat.getColor(mContext, R.color.log_background_grey));

    }

    public int getRow_index() {
        return row_index;
    }

    public void setPlayers(ArrayList<PlayerStats> players) {
        mPlayers = players;
        notifyDataSetChanged();

    }

    public void updateData() {
        notifyDataSetChanged();
    }
}
