package com.mike.projectboxscore.Data;

public class PlayerOnCourtStats {
    private String Name;
    private int backNumber;
    private int points;
    private int rebounds;
    private int assists;
    private int offensiveRebounds;
    private int defensiveRebounds;
    private int twoPointShotTaken;
    private int twoPointShotMade;
    private int threePointShotTaken;
    private int threePointShotMade;
    private int turnOvers;
    private int fouls;

    public PlayerOnCourtStats(String name, int backNumber) {
        Name = name;
        this.backNumber = backNumber;
    }

    public String getName() {
        return Name;
    }

    public int getBackNumber() {
        return backNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRebounds() {
        return rebounds;
    }

    public void setRebounds(int rebounds) {
        this.rebounds = rebounds;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getOffensiveRebounds() {
        return offensiveRebounds;
    }

    public void setOffensiveRebounds(int offensiveRebounds) {
        this.offensiveRebounds = offensiveRebounds;
    }

    public int getDefensiveRebounds() {
        return defensiveRebounds;
    }

    public void setDefensiveRebounds(int defensiveRebounds) {
        this.defensiveRebounds = defensiveRebounds;
    }

    public int getTwoPointShotTaken() {
        return twoPointShotTaken;
    }

    public void setTwoPointShotTaken(int twoPointShotTaken) {
        this.twoPointShotTaken = twoPointShotTaken;
    }

    public int getTwoPointShotMade() {
        return twoPointShotMade;
    }

    public void setTwoPointShotMade(int twoPointShotMade) {
        this.twoPointShotMade = twoPointShotMade;
    }

    public int getThreePointShotTaken() {
        return threePointShotTaken;
    }

    public void setThreePointShotTaken(int threePointShotTaken) {
        this.threePointShotTaken = threePointShotTaken;
    }

    public int getThreePointShotMade() {
        return threePointShotMade;
    }

    public void setThreePointShotMade(int threePointShotMade) {
        this.threePointShotMade = threePointShotMade;
    }

    public int getTurnOvers() {
        return turnOvers;
    }

    public void setTurnOvers(int turnOvers) {
        this.turnOvers = turnOvers;
    }

    public int getFouls() {
        return fouls;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }
}
