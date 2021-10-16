package com.mike.projectboxscore.teams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.callback.GamesDataCallback;
import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.PlayerViewHolder> {

    private MyTeamContract.Presenter mPresenter;
    int mRowIndex = 0;
    private ArrayList<Game> mGames;

    public GameAdapter(MyTeamContract.Presenter presenter, ArrayList<Game> game) {
        mPresenter = presenter;
        mGames = game;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_games, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {
        Game game = mGames.get(i);
        playerViewHolder.myName.setText(game.getmMyTeamName());
        playerViewHolder.opponentName.setText(game.getmOpponent());
        playerViewHolder.myScore.setText(String.valueOf(game.getmMyScore()));
        playerViewHolder.opponentScore.setText(String.valueOf(game.getmOpponentScore()));
        playerViewHolder.mConstraintLayout.setOnClickListener(v -> mPresenter.openBoxScore(game));
        playerViewHolder.mConstraintLayout.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(R.string.delete_game_dialog_title)
                    .setMessage(R.string.delete_game_dialog_message)
                    .setCancelable(true)
                    .setPositiveButton(Constants.YES, (dialog, id) -> {
                        mPresenter.deleteGame(game, games -> {
                            mGames = games;
                            notifyDataSetChanged();
                        });
                        dialog.dismiss();
                    })
                    .setNegativeButton(Constants.NO, (dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView myName;
        private TextView opponentName;
        private TextView myScore;
        private TextView opponentScore;
        private ConstraintLayout mConstraintLayout;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            myName = itemView.findViewById(R.id.textView_My_name);
            opponentName = itemView.findViewById(R.id.textView_Opponent_name);
            myScore = itemView.findViewById(R.id.textView_My_Score);
            opponentScore = itemView.findViewById(R.id.textView_Opponent_Score);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout_games);

        }
    }

    public void updateData(ArrayList<Game> games, boolean useless) {
        mGames = games;
        notifyDataSetChanged();
    }
}
