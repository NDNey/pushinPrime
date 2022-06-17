package com.servermonks.pushinprime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class FileDataReader {
    private JSONObject data = getJson("resources/data");
    private JSONObject NPCs = getJson("resources/NPCs") ;

    public JSONObject getData() {
        return data;
    }


    public JSONObject getNPCs() {
        return NPCs;
    }

    private JSONObject getJson(String filePath) {
        String content = null;
        JSONObject json = null;

        try {
            content = new String(Files.readAllBytes(Path.of(filePath)));
            json = new JSONObject(content);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public String getDirections(String currentLocation) {
        String directions = null;
        try {
            directions = data.getJSONObject(currentLocation).getJSONObject("directions").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return directions;
    }
    public String getAdversary(String currentLocation) {
        String adversary = null;
        try {
            adversary = data.getJSONObject(currentLocation).get("adversary").toString();
        } catch (JSONException e) {
            adversary = "non";
            return adversary;
        }
        return adversary;
    }
    public JSONArray getItems(String currentLocation) {
        JSONArray item = null;
        try {
            item =  data.getJSONObject(currentLocation).getJSONArray("item");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    public JSONArray getPackages(String currentLocation) {
        JSONArray packages = null;
        try {
            packages =  data.getJSONObject(currentLocation).getJSONArray("packages");
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return packages;
    }
    public String[] getKeys( ) {
        String[] keys = null;
        try {
            for (Iterator it = data.keys(); it.hasNext(); ) {
                int place = it.next();


            }
            keys =  packagesJson.join("-").split("-");

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return keys;
    }



    public String goToLocation(String currentLocation, String direction) {
        String location = null;
        try {
            location = (String) data.getJSONObject(currentLocation).getJSONObject("directions").get(direction);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return location;
    }

    public String getNpc(String currentLocation ) {
        String npc = null;
        try {
            npc = data.getJSONObject(currentLocation).get("customer").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return npc;
    }

    public String getNpcDialog(String npc,int index ) {
        String dialog = null;
        try {
            dialog = (String) NPCs.getJSONObject("customers").getJSONObject(npc).getJSONArray("dialog").get(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dialog;
    }







}