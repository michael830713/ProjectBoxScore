package com.mike.projectboxscore.MyTeam;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.projectboxscore.Data.Game;
import com.mike.projectboxscore.Data.Player;
import com.mike.projectboxscore.Data.Team;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.PlayerViewHolder> {

    private Team mTeam;
    private MyTeamContract.Presenter mPresenter;
    int row_index = 0;
    private ArrayList<Player> mPlayers;
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
        playerViewHolder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openBoxScore(game);
            }
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
