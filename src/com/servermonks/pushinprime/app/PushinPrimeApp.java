package com.servermonks.pushinprime.app;

import java.nio.file.Paths;
import java.util.*;
import java.util.Scanner;
import static com.servermonks.pushinprime.Colors.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONString;
import org.json.JSONObject;
import com.apps.util.Console;
import com.apps.util.Prompter;
//import com.PushinPrimeApp.Player;



public class PushinPrimeApp {

    private final Prompter PROMPTER = new Prompter(new Scanner(System.in)) ;
//    private final Board board = Board.getInstance();
    private boolean gameOver;




    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player names
     */
    public void execute()  {
//        welcome();
        //howToPlay();
        PROMPTER.prompt(GREEN + "Press [enter] to start..." + RESET + "");
        try {
            jsonPractice();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        //showSplashScreen();
        //createBoard();
        //prompts for user name
        //prompt for commands
        //promptForUsername();
        //run();
    }

    private void welcome() throws IOException {
        Console.clear();
        String banner = Files.readString(Path.of("resources/data"));
        PROMPTER.info(banner);
    }

    public void getCommands() {
        while (gameOver != true) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter username");
            String userName = myObj.nextLine();
            //need to use .split("",2) to split input into two.
            //take seperate actions depending on input(grab,use,go)


        }
    }

    public void jsonPractice() throws JSONException, IOException {

        String content = new String(Files.readAllBytes(Path.of("resources/data")));
        JSONObject json = new JSONObject(content);


        System.out.println(json);
        System.out.println(json.get("Pantry"));
//        JSONObject json = new JSONObject();
//        JSONObject json2 = new JSONObject();
//        JSONArray array = new JSONArray();

//        json.put("name", "David");
//        json.put("object", json2 );
//        json.put("number", 5);
//        array.put("item");
//        array.put(4);
//        array.put("item2");
//        json.put("array", array);
//        System.out.println(json);
//
//
//        System.out.println(json.get("name"));





    }
}


