package com.servermonks.pushinprime.app;


import java.io.ByteArrayInputStream;
import static com.servermonks.pushinprime.Colors.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


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
    private boolean gameOver;
    private Player player1;

    public void PushinPrimeApp() {
        board = Board.getInstance();
    }

    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player name
     */
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

        PROMPTER.asciiArt("================\\\n" +
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


        PROMPTER.info(json.toString());
        PROMPTER.info(json.get("Pantry").toString());
    }

    // Prompts for usernames and creates new Player objects
    private void promptForUsername() {
        this.player1 = new Player(PROMPTER.prompt("Enter your name: "));
        PROMPTER.info("Thanks " + player1.getName() + "!");
    }

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
            board.clear();

        } else {
            gameOver();
        }
    }

    private void gameOver() throws IOException {
        try {
            board.clear();
            Console.blankLines(1);
            String banner = Files.readString(Path.of("resources/thankyou.txt"));
            PROMPTER.info(banner);
            Console.blankLines(1);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
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