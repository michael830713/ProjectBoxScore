package com.mike.projectboxscore.MyTeam;

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
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamPlayerAdapter extends RecyclerView.Adapter<TeamPlayerAdapter.PlayerViewHolder> {

    private Team mTeam;
    private MyTeamContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<Player> mPlayers;

    public TeamPlayerAdapter(MyTeamContract.Presenter presenter, Team team) {
        mPresenter = presenter;
        mTeam = team;
        mPlayers = mTeam.getmPlayers();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_players, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {
        playerViewHolder.playerName.setText(mPlayers.get(i).getName());
        playerViewHolder.onCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());
        playerViewHolder.backNumber.setText(String.valueOf(mPlayers.get(i).getBackNumber()));
        Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(50, 50).centerCrop().into(playerViewHolder.playerAvatar);
        //        playerViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.showEditPlayer(mPlayers.get(i));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView playerName;
        private TextView onCourtPosition;
        private TextView backNumber;
        private ImageView playerAvatarFrame;
        private ImageView playerAvatar;
        private ImageView editButton;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.textView_player_name);
            onCourtPosition = itemView.findViewById(R.id.textView_OnCourtPosition);
            backNumber = itemView.findViewById(R.id.textView_number);
            playerAvatarFrame = itemView.findViewById(R.id.imageViewPlayerAvatar);
            playerAvatar = itemView.findViewById(R.id.pic);
//            editButton = itemView.findViewById(R.id.imageViewEdit);

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
