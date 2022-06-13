package com.servermonks.pushinprime.app;


import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int wins = 0;
    private int losses = 0;
    private List<String> inventory = new ArrayList<String>();


    public Player(String name) {
        this.name = name;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName() + " your inventory has:" + getInventory();
    }

}
