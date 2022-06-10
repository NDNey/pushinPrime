package com.servermonks.pushinprime.client;

import com.servermonks.pushinprime.app.PushinPrimeApp;

import java.io.IOException;

public class Main {

    public static void main(String[] args)  {
        PushinPrimeApp pushinPrimeApp = new PushinPrimeApp();
        try {
            pushinPrimeApp.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}