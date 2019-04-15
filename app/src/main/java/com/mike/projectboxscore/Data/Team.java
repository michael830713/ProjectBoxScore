package com.mike.projectboxscore.Data;

import java.util.ArrayList;

public class Team {
    private String mName;
    private ArrayList<Player> mPlayers = new ArrayList<>();
    private ArrayList<Game> mGames;
    private int mWins;
    private int mLosses;

    public Team(String mName) {
        this.mName = mName;
    }

    public ArrayList<Game> getmGames() {
        return mGames;
    }

    public void setmGames(ArrayList<Game> mGames) {
        this.mGames = mGames;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Player> getmPlayers() {
        return mPlayers;
    }

    public void setmPlayers(ArrayList<Player> mPlayers) {
        this.mPlayers = mPlayers;
    }

    public void addmPlayers(Player mPlayeradds) {
        mPlayers.add(mPlayeradds);
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
