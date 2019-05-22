package com.mike.projectboxscore.datas;

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

    public void setmTeamLogoUrl(String teamLogoUrl) {
        this.mTeamLogoUrl = teamLogoUrl;
    }

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public ArrayList<Game> getmGames() {
        return mGames;
    }

    public void setmGames(ArrayList<Game> games) {
        this.mGames = games;
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

    public void setmPlayers(ArrayList<Player> players) {
        this.mPlayers = players;
    }

    public void addmPlayers(Player playerAdds) {
        mPlayers.add(playerAdds);
    }

    public int getmWins() {
        return mWins;
    }

    public void setmWins(int wins) {
        this.mWins = wins;
    }

    public int getmLosses() {
        return mLosses;
    }

    public void setmLosses(int losses) {
        this.mLosses = losses;
    }
}
