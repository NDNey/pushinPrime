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

import com.servermonks.pushinprime.Board;

public class PushinPrimeApp {

    private Board board;
    private boolean gameOver;
    private Player player;
    private String username;
    private String password = "password";
    private String endGame = "quit";


    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player name
     */
    public void execute() throws IOException, InterruptedException {
        welcome();
        howToPlay();
        PROMPTER.prompt(GREEN + "Press [enter] to start..." + RESET + "");
        //board.createBoard();
        //prompt for commands
        promptForUsername();
        //validateInput();
        //announceWinner();
        playAgain();
        quitGame();

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
    private void promptForUsername() throws InterruptedException {
        //this.player = new Player(PROMPTER.prompt("Enter your name: "));


        Scanner s = new Scanner(System.in);
        System.out.print("Enter username:");//username:user
        username = s.nextLine();
        System.out.print("Enter password:");//password:user
        password = s.nextLine();
        if(password.equals("password"))
        {
            System.out.println("Authenticating....please wait");
            Thread.sleep(3000);
            System.out.println("Authentication Successful");
        }
        else
        {
            Thread.sleep(3000);
            System.out.println("Authentication Failed");
        }
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
    public void playAgain() throws IOException, InterruptedException {
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
            execute();

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
    private void quitGame() throws IOException, InterruptedException {
        if(endGame.equalsIgnoreCase("quit")) {
            gameOver();
        }
        else {
            PROMPTER.prompt("Keep playing?");
            playAgain();
        }
    }
}


    public PushinPrimeApp() {
    }

    public void execute() {
        board = new Board();
    }
}
