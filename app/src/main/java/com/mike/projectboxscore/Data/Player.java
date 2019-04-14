package com.mike.projectboxscore.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String mName;
    private String mEmail;
    private Team mMyTeam;
    private int backNumber;
    private String onCourtPosition;
    private HashMap<String,PlayerStats> mGameStats;

    public HashMap<String, PlayerStats> getmGameStats() {
        return mGameStats;
    }

    public void setmGameStats(HashMap<String, PlayerStats> mGameStats) {
        this.mGameStats = mGameStats;
    }

    public String getmName() {
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
