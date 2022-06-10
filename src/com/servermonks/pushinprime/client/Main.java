package com.servermonks.pushinprime.client;

import com.servermonks.pushinprime.app.PushinPrimeApp;
import org.json.JSONException;

import java.io.IOException;

public class Main {



    public static void main(String[] args)  {

        PushinPrimeApp pushinPrimeApp = new PushinPrimeApp();
        try {
            pushinPrimeApp.execute();
        } catch (IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }
}