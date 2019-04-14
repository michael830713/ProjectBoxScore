package com.mike.projectboxscore.Data;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private String mOpponent;
    private String mTournament;
    private Team mHomeTeam;
    private Date mDate;
    private ArrayList<PlayerStats> mPlayerStats = new ArrayList<>();

    public void setmPlayerStats() {
        for (int i = 0; i < mHomeTeam.getmPlayers().size(); i++) {
//            mPlayerStats = mHomeTeam.getmPlayers().get(i).
        }
    }
}
