package com.servermonks.pushinprime.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD
import java.io.ByteArrayInputStream;
import static com.servermonks.pushinprime.Colors.*;
=======
>>>>>>> a85d36f43eca4304500c37cee65c3785fd4f30b1
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

<<<<<<< HEAD

import com.servermonks.pushinprime.Prompter;
import org.json.JSONException;
import org.json.JSONObject;

import com.apps.util.Console;

import com.servermonks.pushinprime.Board;

//import com.PushinPrimeApp.Player;

public class PushinPrimeApp {

    public static ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
    private Board board = Board.getInstance();
    private Prompter PROMPTER = new Prompter(board);
=======
import static com.servermonks.pushinprime.Colors.*;


public class PushinPrimeApp {

    private final Prompter PROMPTER = new Prompter(new Scanner(System.in));
    private JSONObject data;
    private String currentLocation = "warehouse";

>>>>>>> a85d36f43eca4304500c37cee65c3785fd4f30b1
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
<<<<<<< HEAD
    public void execute() throws IOException {
        //welcome();
        howToPlay();
        promptForUsername();
    }

    private void welcome() throws IOException {
        board.clear();
        String banner = Files.readString(Path.of("resources/data"));
        PROMPTER.info(banner);
    }

    public void getCommands() {
        //PROMPTER.info(game.delimiter());
        PROMPTER.info("Enter username");
        String userName = PROMPTER.prompt();
        PROMPTER.info("Welcome " + userName + " to your first day as a Prime Driver");
        PROMPTER.info("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
        PROMPTER.info("choose your route.");
        PROMPTER.info("A. sunnyside park");
        PROMPTER.info("B. Ballard ");
        PROMPTER.info("C. waterlow row");
        String route = PROMPTER.prompt().toLowerCase();
        PROMPTER.info(route);
        if (route.equals("b")) {
            PROMPTER.info("Looks like we are going to Ballard today"); //need to connect to object from this point on.
        }
        while (gameOver != true) {
            if (PROMPTER.prompt().toLowerCase() == "quit") {
                return;
            }
            if (PROMPTER.prompt().toLowerCase() == "help") {
                PROMPTER.info("To move type 'go' and the direction you want move (go right)");
                PROMPTER.info("To pick up an item type 'grab' and the item (grab snacks)");
                PROMPTER.info("To quit game type 'quit'");
            }
=======
    public void execute() throws IOException, InterruptedException, JSONException {
        data = getJson();
        welcome();
        howToPlay();
        PROMPTER.prompt(GREEN + "Press [enter] to start..." + RESET + "");
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
        PROMPTER.info(banner);
    }


    public void help() {
        System.out.println("Seems that you need some Help!");
        System.out.println("To move type 'go' and the direction you want move (go north)");
        System.out.println("To pick up an item type 'get' and the item (get snacks)");
        System.out.println("To quit game type 'quit game'");
    }

    public void showStatus() {

        try {
            System.out.println("You are in the " + currentLocation + " from here you can go");
            System.out.println(data.getJSONObject(currentLocation).getJSONObject("directions"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void look() {

        try {
            System.out.println("Here you can see: ");
            System.out.println(data.getJSONObject(currentLocation).getJSONArray("item"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Prompts for usernames and password for authentication
    private void promptForUsername() throws InterruptedException {

        username = PROMPTER.prompt("Enter username: ", "^[a-zA-Z]*$", "Please use a valid name! Numbers are not allowed in names");
        password = PROMPTER.prompt("Enter password: ");
        int totalAttempts = 2;

        while (totalAttempts != 0) {
            if (password.equals("password")) {
                System.out.println("Authenticating....please wait");
                Thread.sleep(3000);
                System.out.println("Authentication Successful !");
                System.out.println();
                System.out.println("Welcome " + CYAN + username + RESET + " to your first day as a Prime Driver");
                System.out.println("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
                break;

            } else if (!password.equals("password")) {
                String tryAgain =PROMPTER.prompt("Invalid password,try again:");
                if (tryAgain.equals("password")){
                    System.out.println("Authenticating....please wait");
                    Thread.sleep(3000);
                    System.out.println("Authentication Successful !");
                    System.out.println();
                    System.out.println("Welcome " + CYAN + username + RESET + " to your first day as a Prime Driver");
                    System.out.println("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
                    break;
                }
                else{
                    totalAttempts--;
                    System.out.println("You have " + totalAttempts + " attempts left");
                    password = tryAgain;
                }


            }

            if (totalAttempts == 0) {
                System.out.println("Password limit reached..Goodbye!");
                System.exit(0);
            }

        }
    }

    public void getCommands() {
        Scanner game = new Scanner(System.in);
        showStatus();
        String route = game.nextLine().toLowerCase();

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
>>>>>>> a85d36f43eca4304500c37cee65c3785fd4f30b1

        } else if (route.equals("quit game")) {
            playAgain();
        } else {
            System.out.println("Remember the available commands are: ");
            help();
        }

        getCommands();

    }

    private void howToPlay() {
        PROMPTER.info(YELLOW + "How to play:" + RESET + "\n" + CYAN +
                "   *  Driver moves to loading dock.\n" +
                "   *  Four packages are assigned for delivery, with their routes\n" +
                "   *  driver is expected to delivered all packages to keep customer satisfaction up.\n" +
<<<<<<< HEAD
                "   *  If no obstacle,or you overcome, package is delivered successfully." + RESET + "\n");

        PROMPTER.asciiArt("================\\\n" +
=======
                "   *  If no obstacle,or you overcome, package is delivered successfully. \n" +
                "   *  If you need help type 'help' \n" +
                "   *  The user password is " + RED + "password" + RESET + "\n");

        System.out.println("================\\\n" +
>>>>>>> a85d36f43eca4304500c37cee65c3785fd4f30b1
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

<<<<<<< HEAD
        String content = new String(Files.readAllBytes(Path.of("resources/data")));
        JSONObject json = new JSONObject(content);


        PROMPTER.info(json.toString());
        PROMPTER.info(json.get("Pantry").toString());
    }

    // Prompts for usernames and creates new Player objects
    private void promptForUsername() {
        this.player1 = new Player(PROMPTER.prompt("Enter your name: "));
        PROMPTER.info("Thanks " + player1.getName() + "!");
    }

    public void playAgain() throws IOException {
=======
        try {
            content = new String(Files.readAllBytes(Path.of("resources/data")));
            json = new JSONObject(content);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return json;

    }


    public void playAgain() {
>>>>>>> a85d36f43eca4304500c37cee65c3785fd4f30b1
        Console.blankLines(1);
        String playAgain = PROMPTER.prompt("Would you like to play again? " +
                        GREEN + "[N]ew Game" + RESET + "/" + YELLOW +
                        "[R]ematch" + RESET + "/" + RED + "[E]xit " + RESET,
                "(?i)E|N|R", RED + "Please enter 'E', 'R', or 'N'" + RESET);

        if ("N".equalsIgnoreCase(playAgain)) {
            gameOver = false;

        } else if ("R".equalsIgnoreCase(playAgain)) {
            gameOver = false;
<<<<<<< HEAD
            board.clear();

=======
            Console.clear();
            currentLocation = "warehouse";
            System.out.println("Hello " + username + " welcome back for another round of PushinPrime!");
            getCommands();
>>>>>>> a85d36f43eca4304500c37cee65c3785fd4f30b1
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
            PROMPTER.info(banner);
            Console.blankLines(1);
            Thread.sleep(3000);
            System.exit(0);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

<<<<<<< HEAD
    public static ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public static void setInputStream(ByteArrayInputStream inputStream) {
        PushinPrimeApp.inputStream = inputStream;
    }
}
=======
}

>>>>>>> a85d36f43eca4304500c37cee65c3785fd4f30b1
