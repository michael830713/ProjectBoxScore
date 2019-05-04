package com.mike.projectboxscore.Data;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Player> mPlayers = new ArrayList<>();
    private ArrayList<Game> mGames = new ArrayList<>();
    private int mWins;
    private int mLosses;
    private String mTeamLogoUrl;

    public String getmTeamLogoUrl() {
        return mTeamLogoUrl;
    }

    public void setmTeamLogoUrl(String mTeamLogoUrl) {
        this.mTeamLogoUrl = mTeamLogoUrl;
    }

    public Team() {
    }

    public Team(String mName) {
        this.name = mName;
    }

    public ArrayList<Game> getmGames() {
        return mGames;
    }

    public void setmGames(ArrayList<Game> mGames) {
        this.mGames = mGames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
