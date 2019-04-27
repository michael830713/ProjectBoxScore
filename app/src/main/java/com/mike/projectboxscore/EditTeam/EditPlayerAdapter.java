package com.mike.projectboxscore.EditTeam;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

public class EditPlayerAdapter extends RecyclerView.Adapter<EditPlayerAdapter.PlayerViewHolder> {
    private static final String TAG = "EditPlayerAdapter";
    private EditTeamContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<Player> mPlayers;

    public EditPlayerAdapter(EditTeamContract.Presenter presenter, ArrayList<Player> players) {
        mPresenter = presenter;
        mPlayers = players;
        Log.d(TAG, "players: " + players);
    }

    public EditPlayerAdapter() {

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_players_with_edit, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {
        if (mPlayers != null) {
            playerViewHolder.playerName.setText(mPlayers.get(i).getName());
            playerViewHolder.backNumber.setText("#" + mPlayers.get(i).getBackNumber());
            playerViewHolder.onCourtPosition.setText(mPlayers.get(i).getOnCourtPosition());
            Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man).resize(50, 50).centerCrop().into(playerViewHolder.playerAvatar);
            playerViewHolder.editPlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.showEditPlayerDialog(mPlayers.get(i));
                }
            });
            playerViewHolder.deletePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index = i;
                    boolean isPlayer = true;
                    mPresenter.showConfirmDeleteDialog(isPlayer);
                }
            });

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
        private ImageView playerAvatarFrame;
        private ImageView playerAvatar;
        private ImageView editPlayerButton;
        private ImageView deletePlayerButton;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.textView_player_name);
            onCourtPosition = itemView.findViewById(R.id.textView_OnCourtPosition);
            backNumber = itemView.findViewById(R.id.textView_number);
            playerAvatarFrame = itemView.findViewById(R.id.imageViewPlayerAvatar);
            playerAvatar = itemView.findViewById(R.id.pic);
            editPlayerButton = itemView.findViewById(R.id.imageViewEdit);
            deletePlayerButton = itemView.findViewById(R.id.imageViewDelete);

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

    public void updateData() {
        notifyDataSetChanged();
    }
}
