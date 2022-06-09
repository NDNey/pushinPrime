package com.servermonks.pushinprime.app;
import com.servermonks.pushinprime.Board;

public class PushinPrimeApp {

    private Board board;
    private boolean gameOver;

    public PushinPrimeApp() {
    }

    public void execute() {
        board = new Board();
    }
}