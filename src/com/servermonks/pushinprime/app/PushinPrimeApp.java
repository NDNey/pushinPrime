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
//import com.PushinPrimeApp.Player;


public class PushinPrimeApp {

    private final Prompter PROMPTER = new Prompter(new Scanner(System.in)) ;
//    private final Board board = Board.getInstance();
    private boolean gameOver;




    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player names
     */
    public void execute() throws IOException {
        welcome();
        //howToPlay();
        PROMPTER.prompt(GREEN + "Press [enter] to start..." + RESET + "");
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
        PROMPTER.info(banner);
    }
    private void howToPlay() {
        PROMPTER.info(RED_UNDERLINED + "$How to play:" + RESET + "\n" +
                "   *  The game is played on a 3x3 grid.\n" +
                "   *  Players take turns putting their marks (X or O) in empty squares using [1-9].\n" +
                "   *  The first player to get 3 of her marks in a row (up, down, across, " +
                "or diagonally) is the winner.\n" +
                "   *  If all 9 squares are full, the game is over.\n");
    }

    public void getCommands() {
        while (gameOver != true) { // while(!gameOver)
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