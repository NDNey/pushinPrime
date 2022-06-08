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
import netscape.javascript.JSObject;
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
        getCommands();
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

//    public void help(){
//        System.out.println("To move type 'go' and the direction you want move (go right)");
//        System.out.println("To pick up an item type 'grab' and the item (grab snacks)");
//        System.out.println("To quit game type 'quit'");
//
//    }

    public void getCommands() {
        Scanner game = new Scanner(System.in);
        System.out.println("Enter username");
        String userName = game.nextLine();
        System.out.println("Welcome " + userName + " to your first day as a Prime Driver");
        System.out.println("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
        System.out.println("choose your route.");
        System.out.println("A. sunnyside park");
        System.out.println("B. Ballard ");
        System.out.println("C. waterlow row");
        String route = game.nextLine().toLowerCase();
        System.out.println(route);
        if (route.equals("b")){
            System.out.println("Looks like we are going to Ballard today"); //need to connect to object from this point on.
        }
        while (gameOver != true) {
            if (game.nextLine().toLowerCase() == "quit"){
                return;
            }
            if (game.nextLine().toLowerCase() == "help"){
                System.out.println("To move type 'go' and the direction you want move (go right)");
                System.out.println("To pick up an item type 'grab' and the item (grab snacks)");
                System.out.println("To quit game type 'quit'");
            }
            // Create a Scanner object

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