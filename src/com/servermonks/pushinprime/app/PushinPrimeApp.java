package com.servermonks.pushinprime.app;

import java.util.*;
import java.util.Scanner;
import static com.servermonks.pushinprime.Colors.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.PushinPrimeApp.Player;


public class PushinPrimeApp {

    private Prompter prompter;
    private final Board board = Board.getInstance();
    private boolean gameOver;


    public PushinPrimeApp(Prompter prompter) {
        this.prompter = prompter;
    }

    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player names
     */
    public void execute() throws IOException {
        welcome();
        //howToPlay();
        prompter.prompt(GREEN + "Press [enter] to start..." + RESET + "");
        //showSplashScreen();
        //createBoard();
        //prompts for user name
        //prompt for commands
        //promptForUsername();
        //run();
    }

    private void welcome() throws IOException {
        Console.clear();
        String banner = Files.readString(Path.of("resources/welcome_banner.txt"));
        prompter.info(banner);
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
}


//
//rooms = {
//        'Hall': {
//        'south': 'Kitchen',
//        'east': 'Dining Room',
//        'item': 'key',
//        'description': 'This is a really beautiful hall you will see the kitchen to the south and the Dining Room to the east'
//        },
//        'Kitchen': {
//        'north': 'Hall',
//        'item': 'monster',
//        'description': 'Be careful there is a Monster somewhere in the kitchen'
//        },
//        'Dining Room': {
//        'west': 'Hall',
//        'south': 'Garden',
//        'item': 'potion',
//        'north': 'Pantry',
//        'description': 'There is really powerful potion in this room. From here you can go to the hall, the garden or the pantry'
//        },
//        'Garden': {
//        'north': 'Dining Room',
//        'east': 'Maze',
//        'description': 'This beautiful garden has a maze. Be careful you do not want to get trap in there'
//        },
//        'Pantry': {
//        'south': 'Dining Room',
//        'item': 'cookie',
//        'description': 'There is not much in this room but you can get some cookies if you look around'
//        }
//        }