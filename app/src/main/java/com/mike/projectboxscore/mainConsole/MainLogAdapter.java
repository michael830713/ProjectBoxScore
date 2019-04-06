package com.mike.projectboxscore.mainConsole;

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

import com.mike.projectboxscore.Data.PlayerOnCourtStats;
import com.mike.projectboxscore.R;

import java.util.ArrayList;

public class MainLogAdapter extends RecyclerView.Adapter<MainLogAdapter.PlayerViewHolder> {

    private static final String TAG = "MainLogAdapter";

    private MainConsoleViewContract.Presenter mPresenter;
    private ArrayList<PlayerOnCourtStats> mPlayerOnCourtStatList = new ArrayList<PlayerOnCourtStats>();
    private ArrayList<String> mActions = new ArrayList<String>();

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
        playerViewHolder.mPlayerName.setText(mPlayerOnCourtStatList.get(0).getName());
        playerViewHolder.mPlayerPoints.setText(Integer.toString(mPlayerOnCourtStatList.get(0).getPoints()));
        playerViewHolder.mAction.setText(mActions.get(0));
        Log.d(TAG, "mPlayerList: " + mPlayerOnCourtStatList);
    }

    @Override
    public int getItemCount() {
        return mPlayerOnCourtStatList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView mAction;
        TextView mPlayerPoints;
        TextView mPlayerName;
        TextView mFieldGoals;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            mAction = itemView.findViewById(R.id.textView_action);
            mPlayerPoints = itemView.findViewById(R.id.textView_player_points);
            mPlayerName = itemView.findViewById(R.id.textView_player_name);
            mFieldGoals = itemView.findViewById(R.id.textView_field_goal);

        }
    }

    public void setLog(PlayerOnCourtStats playerOnCourtStats, String action) {
        mPlayerOnCourtStatList.add(0, playerOnCourtStats);
        mActions.add(0, action);
        notifyItemInserted(0);
    }

}
