package com.mike.projectboxscore.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    boolean isOnCourt;
    private String mName;
    private String mEmail;
    private Team mMyTeam;
    private int backNumber;
    private String onCourtPosition;
    ArrayList<HashMap> gameStatHistory;
    private HashMap<Integer, PlayerStats> mGameStats=new HashMap<>();

    public Player(String mName, String mEmail, int backNumber, String onCourtPosition) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
    }

    public Player(String mName, int backNumber, String onCourtPosition) {
        this.mName = mName;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
    }

    public boolean isOnCourt() {
        return isOnCourt;
    }

    public void setOnCourt(boolean onCourt) {
        isOnCourt = onCourt;
    }

    public HashMap<Integer, PlayerStats> getmGameStats() {
        return mGameStats;
    }

    public void setmGameStats(HashMap<Integer, PlayerStats> mGameStats) {
        this.mGameStats = mGameStats;
    }

    public String getName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
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
