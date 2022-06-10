package com.servermonks.pushinprime.app;

public class Player {
    private String name;
    private int wins = 0;
    private int losses = 0;

    public Player(String name) {
        this.name = name;
    }

    public void setLoss() {
        this.losses++;
    }

    public int getLosses() {
        return this.losses;
    }

    public void setWin() {
        this.wins++;
    }

    public int getWins() {
        return this.wins;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName() + " (" + getWins() + "|" + getLosses() + ")";
    }

}
