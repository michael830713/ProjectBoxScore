package com.mike.projectboxscore.datas;

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

    public Game() {
    }

    public Game(String opponent, String tournament, String homeTeam) {
        id = uniqueId.getAndIncrement();
        this.mOpponent = opponent;
        this.mTournament = tournament;
        this.mMyTeamName = homeTeam;
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
        timeStamp = s.format(new Date());

    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setmPlayerStats(ArrayList<PlayerStats> playerStats) {
        this.mPlayerStats = playerStats;
    }

    public void addmPlayerStats(ArrayList<PlayerStats> playerStats) {
        mPlayerStats.addAll(playerStats);
    }

    public void addmPlayerStat(PlayerStats playerStats) {
        mPlayerStats.add(playerStats);
    }

    public void addOpponentToPlayerStats() {
        PlayerStats opponent = new PlayerStats(getmOpponent(), -1, "O", true);
        mPlayerStats.add(opponent);
    }

    public String getmMyTeamName() {
        return mMyTeamName;
    }

    public void setmMyTeamName(String myTeamName) {
        this.mMyTeamName = myTeamName;
    }

    public int getmMyScore() {
        return mMyScore;
    }

    public void setmMyScore(int myScore) {
        this.mMyScore = myScore;
    }

    public int getmOpponentScore() {
        return mOpponentScore;
    }

    public void setmOpponentScore(int opponentScore) {
        this.mOpponentScore = opponentScore;
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

    public void setmOpponent(String opponent) {
        this.mOpponent = opponent;
    }

    public String getmTournament() {
        return mTournament;
    }

    public void setmTournament(String tournament) {
        this.mTournament = tournament;
    }

}
