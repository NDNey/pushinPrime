package com.servermonks.pushinprime.client;

import com.servermonks.pushinprime.app.PushinPrimeApp;

public class Main {

    public static void main(String[] args) {

        PushinPrimeApp pushinPrimeApp = new PushinPrimeApp();
        try {
            pushinPrimeApp.execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}