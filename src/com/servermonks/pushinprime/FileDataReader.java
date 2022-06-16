package com.servermonks.pushinprime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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




}