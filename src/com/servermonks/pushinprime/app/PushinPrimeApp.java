package com.servermonks.pushinprime.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.servermonks.pushinprime.Board;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.servermonks.pushinprime.Colors.*;

//import com.PushinPrimeApp.Player;


public class PushinPrimeApp {

    private final Prompter PROMPTER = new Prompter(new Scanner(System.in));
    private JSONObject data;
    private String currentLocation = "warehouse";
//    private final Board board = Board.getInstance();


    private boolean gameOver;
    private Player player;
    private String username;
    private String password = "password";


    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player name
     */
    public void execute() throws IOException, InterruptedException, JSONException {
        data = getJson();
        welcome();
        howToPlay();
        PROMPTER.prompt(GREEN + "Press [enter] to start..." + RESET + "");
        promptForUsername();
        getCommands();
        playAgain();
        Board board = new Board();
    }

    private void welcome() {
        Console.clear();
        String banner = null;
        try {
            banner = Files.readString(Path.of("resources/welcome_banner.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PROMPTER.info(banner);
    }


    public void help() {
        System.out.println("To move type 'go' and the direction you want move (go right)");
        System.out.println("To pick up an item type 'grab' and the item (grab snacks)");
        System.out.println("To quit game type 'quit'");

    }

    public void showStatus() {
        System.out.println("You are in the " + currentLocation + " from here you can go");
        try {
            System.out.println(data.getJSONObject(currentLocation).getJSONObject("directions"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



// Prompts for usernames and password for authentication
    private void promptForUsername() throws InterruptedException {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter username:");
        username = s.nextLine();
        System.out.print("Enter password:");//password:password
        password = s.nextLine();
        int totalAttempts = 2;

        while (totalAttempts != 0) {
            if (password.equals("password")) {
                System.out.println("Authenticating....please wait");
                Thread.sleep(3000);
                System.out.println("Authentication Successful !");
                System.out.println();
                break;
            } else if (password != "password") {
                PROMPTER.prompt("Invalid password,try again:");
//            totalAttempts--;
                //System.out.println("You have " + totalAttempts + " attempt left");
            }

            if (totalAttempts == 0) {
                System.out.println("Password limit reached..Goodbye!");
                System.exit(0);
            }
        }
    }

    public void getCommands() throws JSONException, IOException, InterruptedException {

        Scanner game = new Scanner(System.in);
        System.out.println("Welcome " + username + " to your first day as a Prime Driver");
        System.out.println("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");

        showStatus();
        String route = game.nextLine().toLowerCase();
        System.out.println(route);



        while (gameOver != true) {
            if (route.equals("help")) {
                help();
            } else if (route.contains("go")) {
                System.out.println("I work");
                System.out.println(data.getJSONObject(currentLocation).getJSONObject("directions"));
                System.out.println(data.getJSONObject(currentLocation).getJSONObject("directions").get(route.substring(3)));
                currentLocation = (String) data.getJSONObject(currentLocation).getJSONObject("directions").get(route.substring(3));
                showStatus();
            } else if (route.equals("quit game")) {
                playAgain();
            } else {
                System.exit(0);
            }


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


    private JSONObject getJson()  {
        String content = null;
        JSONObject json = null;

        try {
            content = new String(Files.readAllBytes(Path.of("resources/data")));
            json = new JSONObject(content);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return json;

    }


    public void playAgain() throws IOException, InterruptedException, JSONException {
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
            execute();

        } else {
            gameOver();
        }
    }

    private void gameOver() {
        try {
            Console.clear();
            Console.blankLines(1);
            String banner = Files.readString(Path.of("resources/thankyou.txt"));
            PROMPTER.info(banner);
            Console.blankLines(1);
            Thread.sleep(3000);
            System.exit(0);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
}

