package com.mike.projectboxscore.datas;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    boolean isOnCourt;
    private String name;
    private String mEmail;
    private Team mMyTeam;
    private int backNumber = -1;
    private String onCourtPosition;
    private String imageUrl;
    ArrayList<HashMap> gameStatHistory;
    private HashMap<String, PlayerStats> mGameStats = new HashMap<>();

    public Player(String name, String email, int backNumber, String onCourtPosition) {
        this.name = name;
        this.mEmail = email;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
    }

    public Player(String name, String email, int backNumber, String onCourtPosition, String imageUrl) {
        this.name = name;
        this.mEmail = email;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
        this.imageUrl = imageUrl;
    }

    public Player(String name, int backNumber, String onCourtPosition, String imageUrl) {
        this.name = name;

        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
        this.imageUrl = imageUrl;
    }

    public Player(String name, int backNumber, String onCourtPosition) {
        this.name = name;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setmGameStats(HashMap<String, PlayerStats> gameStats) {
        this.mGameStats = gameStats;
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

    public void setmEmail(String email) {
        this.mEmail = email;
    }

    public Team getmMyTeam() {
        return mMyTeam;
    }

    public void setmMyTeam(Team myTeam) {
        this.mMyTeam = myTeam;
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
