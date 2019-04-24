package com.mike.projectboxscore.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    boolean isOnCourt;
    private String name;
    private String mEmail;
    private Team mMyTeam;
    private int backNumber = -1;
    private String onCourtPosition;
    ArrayList<HashMap> gameStatHistory;
    private HashMap<String, PlayerStats> mGameStats = new HashMap<>();

    public Player(String mName, String mEmail, int backNumber, String onCourtPosition) {
        this.name = mName;
        this.mEmail = mEmail;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
    }

    public Player(String mName, int backNumber, String onCourtPosition) {
        this.name = mName;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
    }

    public Player() {
    }

    public boolean isOnCourt() {
        return isOnCourt;
    }

    public void setOnCourt(boolean onCourt) {
        isOnCourt = onCourt;
    }

    public HashMap<String, PlayerStats> getmGameStats() {
        return mGameStats;
    }

    public void setmGameStats(HashMap<String, PlayerStats> mGameStats) {
        this.mGameStats = mGameStats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Team getmMyTeam() {
        return mMyTeam;
    }

    public void setmMyTeam(Team mMyTeam) {
        this.mMyTeam = mMyTeam;
    }

    public int getBackNumber() {
        return backNumber;
    }

    public void setBackNumber(int backNumber) {
        this.backNumber = backNumber;
    }

    public String getOnCourtPosition() {
        return onCourtPosition;
    }

    public void setOnCourtPosition(String onCourtPosition) {
        this.onCourtPosition = onCourtPosition;
    }

}
