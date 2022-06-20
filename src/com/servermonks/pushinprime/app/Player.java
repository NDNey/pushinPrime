package com.servermonks.pushinprime.app;


import com.servermonks.pushinprime.Board;
import com.servermonks.pushinprime.FileDataReader;
import com.servermonks.pushinprime.Prompter;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Board board = Board.getInstance();
    private Prompter PROMPTER = new Prompter(board);
    private String name;
    private int health = 100;
    private int customerSatisfaction ;

    private List<String> inventory = new ArrayList<String>();

    public int getCustomerSatisfaction() {
        return customerSatisfaction;
    }

    public void setCustomerSatisfaction(int customerSatisfaction) {
        this.customerSatisfaction = customerSatisfaction;
    }

    public Player(String name) {
        this.name = name;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return this.name;
    }

    public void look(FileDataReader data, String currentLocation) {
        PROMPTER.info(" ");
        PROMPTER.info("Here you can see: ");
        PROMPTER.info(data.getItems(currentLocation).toString());

    }

    public void talk(FileDataReader data, String currentLocation) {
        PROMPTER.info(" ");
        int random = (int) (Math.random() * 3);
        String npc = data.getNpc(currentLocation);
        String dialog = data.getNpcDialog(npc, random);
        PROMPTER.info(npc + " says " + dialog);
    }

    public void heal() {
        if(inventory.contains("medicine") && getHealth() <= 95){
            setHealth(getHealth() + 5);
            PROMPTER.info("your health is recovering...");
        }else{
            PROMPTER.info("you will need some medicine to hill yourself");
        }
    }

    @Override
    public String toString() {
        return getName() + " your inventory has:" + getInventory();
    }

}
