package com.mike.projectboxscore.Data;

import java.util.ArrayList;

public class Team {
    private String mName;
    private ArrayList<PlayerStats> mPlayers;
    private int mWins;
    private int mLosses;

    public Team(String mName) {
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<PlayerStats> getmPlayers() {
        return mPlayers;
    }

    public void setmPlayers(ArrayList<PlayerStats> mPlayers) {
        this.mPlayers = mPlayers;
    }

    public void addmPlayers(PlayerStats player) {
        mPlayers.add(player);
    }

    public int getmWins() {
        return mWins;
    }

    public void setmWins(int mWins) {
        this.mWins = mWins;
    }

    public int getmLosses() {
        return mLosses;
    }

    public void setmLosses(int mLosses) {
        this.mLosses = mLosses;
    }
}
