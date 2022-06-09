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
import com.servermonks.pushinprime.Board;
//import com.PushinPrimeApp.Player;


public class PushinPrimeApp {

    private final Prompter PROMPTER = new Prompter(new Scanner(System.in));
    private final Board board = Board.getInstance();
    private boolean gameOver;
    private Player player1;


    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player name
     */
    public void execute() throws IOException {
        welcome();
        howToPlay();
        PROMPTER.prompt(GREEN + "Press [enter] to start..." + RESET + "");
        //board.createBoard();
        //prompt for commands
        promptForUsername();
        //validateInput();
        //announceWinner();
        playAgain();

    }

    private void welcome() throws IOException {
        Console.clear();
        String banner = Files.readString(Path.of("resources/welcome_banner.txt"));
        PROMPTER.info(banner);
    }

    private void howToPlay() {
        PROMPTER.info(YELLOW + "How to play:" + RESET + "\n" + CYAN +
                "   *  Driver moves to loading dock.\n" +
                "   *  Four packages are assigned for delivery, with their routes\n" +
                "   *  driver is expected to delivered all packages to keep customer satisfaction up.\n" +
                "   *  If no obstacle,or you overcome, package is delivered successfully." + RESET + "\n");

        System.out.println("================\\\n" +
                " |----------||@  \\\\   ___\n" +
                " |____|_____|||_/_\\\\_|___|_\n" +
                "<|  ___\\    ||     | ____  |\n" +
                "<| /    |___||_____|/    | |\n" +
                "||/  O  |__________/  O  |_||\n" +
                "   \\___/            \\___/\n");
    }

    // Prompts for usernames and creates new Player objects
    private void promptForUsername() {
        this.player1 = new Player(PROMPTER.prompt("Enter your name: "));
    }

//
//    private void announceWinner() {
//    }
//
//    private void validateInput() {
//        Console.blankLines(1);
//
//    }


    //    public void getCommands() {
//        while (gameOver != true) { // while(!gameOver)
//            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
//            System.out.println("Enter username");
//            String userName = myObj.nextLine();
//            //need to use .split("",2) to split input into two.
//            //take seperate actions depending on input(grab,use,go)
//
//        }
//    }
    public void playAgain() throws IOException {
        Console.blankLines(1);
        String playAgain= PROMPTER.prompt("Would you like to play again? " +
                        GREEN + "[N]ew Game" + RESET + "/" + YELLOW +
                        "[R]ematch" + RESET + "/" + RED + "[E]xit " + RESET,
                "(?i)E|N|R", RED + "Please enter 'E', 'R', or 'N'" + RESET);

        if ("N".equalsIgnoreCase(playAgain)) {
            gameOver = false;
            execute();
        } else if ("R".equalsIgnoreCase(playAgain)) {
            gameOver = false;
            Console.clear();

        } else {
            gameOver();
        }
    }

    private void gameOver() throws IOException {
        try {
            Console.clear();
            Console.blankLines(1);
            String banner = Files.readString(Path.of("resources/thankyou.txt"));
            PROMPTER.info(banner);
            Console.blankLines(1);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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