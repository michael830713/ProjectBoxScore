package com.mike.projectboxscore.mainConsole;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class MainLogAdapter extends RecyclerView.Adapter<MainLogAdapter.PlayerViewHolder> {

    private static final String TAG = "MainLogAdapter";

    private MainConsoleViewContract.Presenter mPresenter;
    private ArrayList<PlayerStats> mPlayers = new ArrayList<>();
    private ArrayList<String> mActions = new ArrayList<>();

    public MainLogAdapter(MainConsoleViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_logs, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, final int i) {
        playerViewHolder.mPlayerName.setText(mPlayers.get(i).getName());
        playerViewHolder.mAction.setText(mActions.get(i));
        if (mPlayers.get(i).getBackNumber() != Constants.OPPONENT_BACK_NUMBER) {
            playerViewHolder.mBackNumber.setText(mPlayers.get(i).getBackNumber() + "  ");
        } else {
            playerViewHolder.mBackNumber.setText("");
        }
        Log.d(TAG, "mActions: " + mActions.get(i));
        if (mActions.get(i).contains(Constants.MADE)) {
            Log.d(TAG, "it works: ");
            playerViewHolder.constraintLayout.setBackgroundResource(R.drawable.log_border_black_with__rounded_corner);
        } else if (mActions.get(i).contains(Constants.MISS)) {
            playerViewHolder.constraintLayout.setBackgroundResource(R.drawable.log_border_red);
        } else if (mActions.get(i).contains(Constants.FOUL)) {
            playerViewHolder.constraintLayout.setBackgroundResource(R.drawable.log_border_black_with__rounded_corner);
        } else {
            playerViewHolder.constraintLayout.setBackgroundResource(R.drawable.log_border_light_green);
        }
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        TextView mAction;
        TextView mPlayerName;
        TextView mBackNumber;
        ConstraintLayout constraintLayout;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            mAction = itemView.findViewById(R.id.textView_action);
            mPlayerName = itemView.findViewById(R.id.textView_player_name);
            mBackNumber = itemView.findViewById(R.id.textView_back_number);
            constraintLayout = itemView.findViewById(R.id.constraint_layout_logs);
        }
    }

    public void setLog(PlayerStats playerStats, String action) {
        mPlayers.add(Constants.ADAPTER_TOP_POSITION, playerStats);
        mActions.add(Constants.ADAPTER_TOP_POSITION, action);
        notifyItemInserted(Constants.ADAPTER_TOP_POSITION);
    }

    public ArrayList<String> getmActions() {
        return mActions;
    }

    public ArrayList<PlayerStats> getmPlayers() {
        return mPlayers;
    }

    public void setmPlayers(ArrayList<PlayerStats> mPlayers) {
        this.mPlayers = mPlayers;

    }

    public void setmActionsRemoved(ArrayList<String> mActions, int i) {
        this.mActions = mActions;
        notifyItemRemoved(i);
        notifyItemRangeChanged(Constants.ADAPTER_TOP_POSITION, getItemCount());
    }

}
