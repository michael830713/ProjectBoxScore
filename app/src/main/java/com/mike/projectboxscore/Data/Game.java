package com.mike.projectboxscore.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private static AtomicInteger uniqueId = new AtomicInteger();
    private int id;
    private String mOpponent;
    private String mTournament;
    private Team mHomeTeam;
    private Date mDate;
    private int mMyScore = 0;
    private int mOpponentScore = 0;
    private ArrayList<PlayerStats> mPlayerStats = new ArrayList<>();

    public Game(String mOpponent, String mTournament, Team mHomeTeam) {
        id = uniqueId.getAndIncrement();
        this.mOpponent = mOpponent;
        this.mTournament = mTournament;
        this.mHomeTeam = mHomeTeam;
        for (Player player : mHomeTeam.getmPlayers()) {
            HashMap<Integer, PlayerStats> gameStats = player.getmGameStats();
            gameStats.put(id, new PlayerStats(player.getName(), player.getBackNumber(), player.getOnCourtPosition(), player.isOnCourt));
            mPlayerStats.add(player.getmGameStats().get(id));
        }
        mPlayerStats.add(new PlayerStats(mOpponent, -1, "O", true));
    }

    public void setmPlayerStats(ArrayList<PlayerStats> mPlayerStats) {
        this.mPlayerStats = mPlayerStats;
    }

    public int getmMyScore() {
        return mMyScore;
    }

    public void setmMyScore(int mMyScore) {
        this.mMyScore = mMyScore;
    }

    public int getmOpponentScore() {
        return mOpponentScore;
    }

    public void setmOpponentScore(int mOpponentScore) {
        this.mOpponentScore = mOpponentScore;
    }

    public ArrayList<PlayerStats> getmPlayerStats() {
        return mPlayerStats;
    }

    public int getId() {
        return id;
    }

    public String getmOpponent() {
        return mOpponent;
    }

    public void setmOpponent(String mOpponent) {
        this.mOpponent = mOpponent;
    }

    public String getmTournament() {
        return mTournament;
    }

    public void setmTournament(String mTournament) {
        this.mTournament = mTournament;
    }

    public Team getmHomeTeam() {
        return mHomeTeam;
    }

    public void setmHomeTeam(Team mHomeTeam) {
        this.mHomeTeam = mHomeTeam;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

//    public void setmPlayerStats() {
//        for (Player player : mHomeTeam.getmPlayers()) {
//            mPlayerStats.add(player.getmGameStats().get(id));
//        }
//    }

//    public void setmPlayerStats(ArrayList<PlayerStats> mPlayerStats) {
//        this.mPlayerStats = mPlayerStats;
//    }
}
