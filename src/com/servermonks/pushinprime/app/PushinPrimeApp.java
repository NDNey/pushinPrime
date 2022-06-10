package com.servermonks.pushinprime.app;


import com.apps.util.Console;
import com.apps.util.Prompter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.servermonks.pushinprime.Colors.*;


public class PushinPrimeApp {

    private final Prompter PROMPTER = new Prompter(new Scanner(System.in));
//    private final Board board = Board.getInstance();

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

        try {
            jsonPractice();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        getCommands();

        //showSplashScreen();
        //createBoard();
        //prompts for user name

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
        if (route.equals("b")) {
            System.out.println("Looks like we are going to Ballard today"); //need to connect to object from this point on.
        }
        while (gameOver != true) {
            if (game.nextLine().toLowerCase() == "quit") {
                return;
            }
            if (game.nextLine().toLowerCase() == "help") {
                System.out.println("To move type 'go' and the direction you want move (go right)");
                System.out.println("To pick up an item type 'grab' and the item (grab snacks)");
                System.out.println("To quit game type 'quit'");
            }
            // Create a Scanner object

            //need to use .split("",2) to split input into two.
            //take seperate actions depending on input(grab,use,go)
        }
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
        String playAgain = PROMPTER.prompt("Would you like to play again? " +
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

