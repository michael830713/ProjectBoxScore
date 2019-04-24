package com.mike.projectboxscore.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private static AtomicInteger uniqueId = new AtomicInteger();
    private int id;
    private String mOpponent;
    private String mTournament;
    private String mMyTeamName;
    String timeStamp;
    private int mMyScore = 0;
    private int mOpponentScore = 0;
    private ArrayList<PlayerStats> mPlayerStats = new ArrayList<>();

    public Game(String mOpponent, String mTournament, String mHomeTeam) {
        id = uniqueId.getAndIncrement();
        this.mOpponent = mOpponent;
        this.mTournament = mTournament;
        this.mMyTeamName = mHomeTeam;
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
        timeStamp = s.format(new Date());

    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setmPlayerStats(ArrayList<PlayerStats> mPlayerStats) {
        this.mPlayerStats = mPlayerStats;
    }

    public void addmPlayerStats(ArrayList<PlayerStats> playerStats) {
        mPlayerStats.addAll(playerStats);

    }

    public void addOpponentToPlayerStats() {
        PlayerStats opponent = new PlayerStats(getmOpponent(), -1, "O", true);
        mPlayerStats.add(opponent);
    }

    public String getmMyTeamName() {
        return mMyTeamName;
    }

    public void setmMyTeamName(String mMyTeamName) {
        this.mMyTeamName = mMyTeamName;
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

//    public void setmPlayerStats() {
//        for (Player player : mHomeTeam.getmPlayers()) {
//            mPlayerStats.add(player.getmGameStats().get(id));
//        }
//    }

//    public void setmPlayerStats(ArrayList<PlayerStats> mPlayerStats) {
//        this.mPlayerStats = mPlayerStats;
//    }
}
