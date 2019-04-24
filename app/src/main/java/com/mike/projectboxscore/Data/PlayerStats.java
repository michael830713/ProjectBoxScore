package com.mike.projectboxscore.Data;

public class PlayerStats {
    private int gameId;
    private String name;
    private int backNumber;
    private int points = 0;
    private int rebounds = 0;
    private int assists = 0;
    private int offensiveRebounds = 0;
    private int defensiveRebounds = 0;
    private int shotTaken = 0;
    private int shotMade = 0;
    private int threePointShotTaken = 0;
    private int threePointShotMade = 0;
    private int freeThrowTaken = 0;
    private int freeThrowMade = 0;
    private int turnOvers = 0;
    private int fouls = 0;
    private int steals = 0;
    private int blocks = 0;
    private String onCourtPosition;
    private boolean isOnCourt;

    public boolean isOnCourt() {
        return isOnCourt;
    }

    public void setOnCourt(boolean onCourt) {
        isOnCourt = onCourt;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public PlayerStats() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBackNumber(int backNumber) {
        this.backNumber = backNumber;
    }

    public void setOnCourtPosition(String onCourtPosition) {
        this.onCourtPosition = onCourtPosition;
    }

    public int getFreeThrowTaken() {
        return freeThrowTaken;
    }

    public void setFreeThrowTaken(int freeThrowTaken) {
        this.freeThrowTaken = freeThrowTaken;
    }

    public int getFreeThrowMade() {
        return freeThrowMade;
    }

    public void setFreeThrowMade(int freeThrowMade) {
        this.freeThrowMade = freeThrowMade;
    }

    public String getOnCourtPosition() {
        return onCourtPosition;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getSteals() {
        return steals;
    }

    public void setSteals(int steals) {
        this.steals = steals;
    }

    public PlayerStats(String name, int backNumber, String onCourtPosition) {
        this.name = name;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
    }

    public PlayerStats(String name, int backNumber, String onCourtPosition, boolean isOnCourt) {
        this.name = name;
        this.backNumber = backNumber;
        this.onCourtPosition = onCourtPosition;
        this.isOnCourt = isOnCourt;
    }

    public String getName() {
        return name;
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

    public int getShotTaken() {
        return shotTaken;
    }

    public void setShotTaken(int shotTaken) {
        this.shotTaken = shotTaken;
    }

    public int getShotMade() {
        return shotMade;
    }

    public void setShotMade(int shotMade) {
        this.shotMade = shotMade;
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
