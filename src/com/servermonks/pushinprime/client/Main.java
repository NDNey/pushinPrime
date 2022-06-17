package com.servermonks.pushinprime.client;

import com.servermonks.pushinprime.FileDataReader;
import com.servermonks.pushinprime.app.PushinPrimeApp;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

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