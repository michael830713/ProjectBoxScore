package com.mike.projectboxscore.NewTeam;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class NewPlayerAdapter extends RecyclerView.Adapter<NewPlayerAdapter.PlayerViewHolder> {

    private NewTeamContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<Player> mPlayers;

    public NewPlayerAdapter(NewTeamContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public NewPlayerAdapter() {

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_players, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {
        if (mPlayers != null) {
            playerViewHolder.playerName.setText(mPlayers.get(i).getName());
            playerViewHolder.backNumber.setText("#" + mPlayers.get(i).getBackNumber());
            playerViewHolder.onCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());

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
        private TextView playerName;
        private TextView onCourtPosition;
        private TextView backNumber;
        private ImageView playerAvatar;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.textView_player_name);
            onCourtPosition = itemView.findViewById(R.id.textView_OnCourtPosition);
            backNumber = itemView.findViewById(R.id.textView_number);
            playerAvatar = itemView.findViewById(R.id.imageViewPlayerAvatar);

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

    public void setPlayers(ArrayList<Player> players) {
        mPlayers = players;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Player> teamPlayers) {
        mPlayers = teamPlayers;
        notifyDataSetChanged();
    }
}