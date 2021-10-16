package com.mike.projectboxscore.teamedit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.datas.Player;
import com.mike.projectboxscore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EditPlayerAdapter extends RecyclerView.Adapter<EditPlayerAdapter.PlayerViewHolder> {
    private static final String TAG = "EditPlayerAdapter";
    private EditTeamContract.Presenter mPresenter;
    int mRowIndex = 0;
    private ArrayList<Player> mPlayers;

    public EditPlayerAdapter(EditTeamContract.Presenter presenter, ArrayList<Player> players) {
        mPresenter = presenter;
        mPlayers = players;
        Log.d(TAG, "players: " + players);
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
            Picasso.get().load(mPlayers.get(i).getImageUrl()).placeholder(R.drawable.man_with_orange_tint).resize(Constants.PLAYER_AVATAR_DIMEN, Constants.PLAYER_AVATAR_DIMEN).centerCrop().into(playerViewHolder.playerAvatar);

            Log.d(TAG, "player Url: " + mPlayers.get(i).getName() + "  " + mPlayers.get(i).getImageUrl());

            playerViewHolder.editPlayerButton.setOnClickListener(v -> mPresenter.showEditPlayerDialog(mPlayers.get(i)));
            playerViewHolder.deletePlayerButton.setOnClickListener(v -> {
                mRowIndex = i;
                boolean isPlayer = true;
                mPresenter.showConfirmDeleteDialog(isPlayer);
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
        private ImageView playerAvatar;
        private ImageView editPlayerButton;
        private ImageView deletePlayerButton;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.textView_player_name);
            onCourtPosition = itemView.findViewById(R.id.textView_OnCourtPosition);
            backNumber = itemView.findViewById(R.id.textView_number);
            playerAvatar = itemView.findViewById(R.id.pic);
            editPlayerButton = itemView.findViewById(R.id.imageViewEdit);
            deletePlayerButton = itemView.findViewById(R.id.imageViewDelete);

        }
    }

    public int getmRowIndex() {
        return mRowIndex;
    }

    public void updateData() {
        notifyDataSetChanged();
    }
}
