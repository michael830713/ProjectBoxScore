package com.mike.projectboxscore.Data;

public class Action {
    private int actionCode;
    private String action;

    public Action(int actionCode, String action) {
        this.actionCode = actionCode;
        this.action = action;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
