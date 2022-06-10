package com.servermonks.pushinprime.app;

import com.apps.util.Console;
import com.servermonks.pushinprime.Board;
import com.servermonks.pushinprime.Prompter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.servermonks.pushinprime.Colors.*;

//import com.PushinPrimeApp.Player;

public class PushinPrimeApp {

    public static ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
    private Board board = Board.getInstance();
    private Prompter PROMPTER = new Prompter(board);

    private JSONObject data;
    private String currentLocation = "warehouse";


    private boolean gameOver;
    private String username;
    private String password = "password";


    public void PushinPrimeApp() {
        board = Board.getInstance();
    }

    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player name
     */


    public void execute() throws IOException, InterruptedException, JSONException {
        data = getJson();
        welcome();
        howToPlay();
//        PROMPTER.prompt(GREEN + "Press [enter] to start..." + RESET + "");
        promptForUsername();
        getCommands();
    }

    private void welcome() {
        Console.clear();
        String banner = null;
        try {
            banner = Files.readString(Path.of("resources/welcome_banner.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PROMPTER.asciiArt(banner);
    }


    public void help() {
        PROMPTER.info(" ");
        PROMPTER.info("* Seems that you need some Help!");
        PROMPTER.info("* To move type 'go' and the direction you want move (go north)");
        PROMPTER.info("* To pick up an item type 'get' and the item (get snacks)");
        PROMPTER.info("* To quit game type 'quit game'");
    }

    public void showStatus() {
        PROMPTER.info(" ");
        try {
            PROMPTER.info("You are in the " + currentLocation + " from here you can go");
            PROMPTER.info(data.getJSONObject(currentLocation).getJSONObject("directions").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void look() {
        PROMPTER.info(" ");
        try {
            PROMPTER.info("Here you can see: ");
            PROMPTER.info(data.getJSONObject(currentLocation).getJSONArray("item").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Prompts for usernames and password for authentication
    private void promptForUsername() throws InterruptedException {

        username = PROMPTER.prompt("Enter username: ");
        password = PROMPTER.prompt("Enter password: ");
        int totalAttempts = 2;

        while (totalAttempts != 0) {
            if (password.equals("password")) {
                PROMPTER.info("Authenticating....please wait");
                Thread.sleep(3000);
                PROMPTER.info("Authentication Successful !\n");
//                PROMPTER.info();
                PROMPTER.info("Welcome " + CYAN + username + RESET + " to your first day as a Prime Driver");
                PROMPTER.info("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
                break;

            } else if (!password.equals("password")) {
                String tryAgain = PROMPTER.prompt("Invalid password,try again:");
                if (tryAgain.equals("password")) {
                    PROMPTER.info("Authenticating....please wait");
                    Thread.sleep(3000);
                    PROMPTER.info("Authentication Successful !\n");
//                    PROMPTER.info();
                    PROMPTER.info("Welcome " + CYAN + username + RESET + " to your first day as a Prime Driver");
                    PROMPTER.info("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
                    break;
                } else {
                    totalAttempts--;
                    PROMPTER.info("You have " + totalAttempts + " attempts left");
                    password = tryAgain;
                }


            }

            if (totalAttempts == 0) {
                PROMPTER.info("Password limit reached..Goodbye!");
                System.exit(0);
            }

        }
    }

    public void getCommands() {
        showStatus();
        String route = PROMPTER.prompt().toLowerCase();

        if (route.equals("help")) {
            help();
        } else if (route.contains("go")) {
            try {
                currentLocation = (String) data.getJSONObject(currentLocation).getJSONObject("directions").get(route.substring(3));
            } catch (JSONException e) {
                help();
            }
        } else if (route.contains("look")) {
            look();

        } else if (route.equals("quit game")) {
            playAgain();
        } else {
            PROMPTER.info("Remember the available commands are: ");
            help();
        }

//        board.clear();
        getCommands();

    }

    private void howToPlay() {
        PROMPTER.info(YELLOW + "How to play:" + RESET + "\n" + CYAN +
                "   *  Driver moves to loading dock.\n" +
                "   *  Four packages are assigned for delivery, with their routes\n" +
                "   *  driver is expected to delivered all packages to keep customer satisfaction up.\n" +
                "   *  If no obstacle,or you overcome, package is delivered successfully." + RESET + "\n" +
                "   *  If you need help type 'help' \n" +
                "   *  The user password is " + RED + "password" + RESET + "\n");

        PROMPTER.asciiArt("================\\\n" +
                " |----------||@  \\\\   ___\n" +
                " |____|_____|||_/_\\\\_|___|_\n" +
                "<|  ___\\    ||     | ____  |\n" +
                "<| /    |___||_____|/    | |\n" +
                "||/  O  |__________/  O  |_||\n" +
                "   \\___/            \\___/\n");
    }


    private JSONObject getJson() {
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


    public void playAgain() {
//        Console.blankLines(1);
        String playAgain = PROMPTER.prompt("Would you like to play again? " +
                        GREEN + " [N]ew Game " + RESET + "/" + YELLOW +
                        "[R]ematch" + RESET + "/" + RED + "[E]xit " + RESET,
                "(?i)E|N|R", RED + "Please enter 'E', 'R', or 'N'" + RESET);

        if ("N".equalsIgnoreCase(playAgain)) {
            gameOver = false;

        } else if ("R".equalsIgnoreCase(playAgain)) {
            gameOver = false;
            board.clear();
//            Console.clear();
            currentLocation = "warehouse";
            PROMPTER.info("Hello " + username + " welcome back for another round of PushinPrime!");
            getCommands();

        } else {
            gameOver();
        }
        try {
            execute();
        } catch (IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void gameOver() {
        try {
            board.clear();
            Console.blankLines(1);
            String banner = Files.readString(Path.of("resources/thankyou.txt"));
            PROMPTER.asciiArt(banner);
            Console.blankLines(1);
            Thread.sleep(3000);
            System.exit(0);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }


    public static ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public static void setInputStream(ByteArrayInputStream inputStream) {
        PushinPrimeApp.inputStream = inputStream;
    }
}




