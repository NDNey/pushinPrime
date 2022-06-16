package com.servermonks.pushinprime.app;

import com.servermonks.pushinprime.Board;
import com.servermonks.pushinprime.FileDataReader;
import com.servermonks.pushinprime.Prompter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.servermonks.pushinprime.Colors.*;


public class PushinPrimeApp {

    public static ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
    private Board board = Board.getInstance();
    private Prompter PROMPTER = new Prompter(board);

    private JSONObject data ;
    private String currentLocation = "warehouse";


    private boolean gameOver;
    private String password = "password";
    private Player user;

    private boolean fightOver = false;
    private boolean playing = true;



    /*
     * Initial game execution:
     *  -> displays welcome banner, instructions and promps for player name
     */


    public void execute() throws InterruptedException {
        data = new FileDataReader().getData();
        welcome();
        howToPlay();
        promptForUsername();
        countdown();


    }

    private void welcome() {
        PROMPTER.info("<img src=\"https://i.ibb.co/Wxf5cJ4/pushin-Prime-banner.png\" '/>");
    }

    public void help() {
        PROMPTER.info(" ");
        PROMPTER.info(YELLOW + "Seems that you need some Help!\n" + RESET +
                "* To move type 'go' and the direction you want move (go north)\n" +
                "* To pick up an item type 'get' and the item (get snacks)\n" +
                "* To look around the area type 'look'\n" +
                "* To to defend yourself against thieves use the word 'Attack' to start a combat match\n" +
                "* To quit game type 'quit game'");
    }

    public void showStatus() {
        PROMPTER.info(" ");
        try {
            PROMPTER.info("You are in the " + currentLocation + " from here you can go");
            PROMPTER.info(data.getJSONObject(currentLocation).getJSONObject("directions").toString());
            String skidRow = data.getJSONObject(currentLocation).get("adversary").toString();
                if (skidRow.equals("thief") && !fightOver) {
                    PROMPTER.info("The neighborhood thief is coming straight towards you! use 'Attack' to fight for your packages!");
                }
        } catch (JSONException e) {
//            e.printStackTrace();
            System.out.println("");
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

    public void getItem(String item) {
        PROMPTER.info(" ");
        List inventory = user.getInventory();
        try {
            String[] items = data.getJSONObject(currentLocation).getJSONArray("item").join("-").split("-");

            for (int i = 0; i < items.length; i++) {
                if (items[i].toLowerCase().contains(item)) {
                    inventory.add(item);
                    user.setInventory(inventory);
                    data.getJSONObject(currentLocation).getJSONArray("item").remove(i);
                    break;
                } else {
                    PROMPTER.info("It seems that there is not any " + item + " around");
                    help();
                }
            }
            PROMPTER.info(item + " has been added to your inventory!");
            PROMPTER.info(user.getName() + " your inventory looks like this: " + user.getInventory());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void dropItem(String item) {
        PROMPTER.info(" ");
        List inventory = user.getInventory();
        try {
            if (inventory.contains(item)) {
                JSONArray locationItems = data.getJSONObject(currentLocation).getJSONArray("item");
                int nextIndex = locationItems.length();
                locationItems.put(nextIndex, item);
                inventory.remove(item);
                PROMPTER.info(item + " has been dropped in " + currentLocation +
                        " from your inventory!");
            } else {
                PROMPTER.info("You don't have " + item + " in your Inventory");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void showInventory() {
        List inventory = user.getInventory();
        if (inventory.size() > 0) {
            PROMPTER.info(user.getName() + " This is your inventory: " + user.getInventory());
        } else {
            PROMPTER.info("Hey!" + user.getName() + " it seems that you don't have anything in your inventory yet!\n" +
                    "look around to check what you can add to your inventory.");
        }
    }


    // Prompts for usernames and password for authentication
    private void promptForUsername() throws InterruptedException {

        String username = PROMPTER.prompt("username: ");
        password = PROMPTER.prompt("password: ");
        user = new Player(username);
        int totalAttempts = 2;

        while (totalAttempts != 0) {
            if (password.equals("password")) {
                PROMPTER.info("Authenticating....please wait");
                Thread.sleep(3000);
                PROMPTER.info("Authentication Successful !\n");
                PROMPTER.info(" ");
                PROMPTER.info("Welcome " + CYAN + username + RESET + " to your first day as a Prime Driver");
                PROMPTER.info("Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
                break;

            } else if (!password.equals("password")) {
                String tryAgain = PROMPTER.prompt("Invalid password,try again:");
                if (tryAgain.equals("password")) {
                    PROMPTER.info("Authenticating....please wait");
                    Thread.sleep(3000);
                    PROMPTER.info("Authentication Successful !\n");
                    PROMPTER.info(" ");
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
        String route = PROMPTER.prompt("route").toLowerCase();
        String[] commands = route.replaceAll("\\s+", " ").split(" ");


        if (route.equals("help")) {
            help();
        } else if (commands[0].equals("go")) {
            try {
                currentLocation = (String) data.getJSONObject(currentLocation).getJSONObject("directions").get(commands[1]);
            } catch (JSONException e) {
                help();
            }
        } else if (commands[0].equals("look")) {
            look();
        } else if (commands[0].equals("get")) {
            getItem(commands[1]);
        } else if (commands[0].equals("drop")) {
            dropItem(commands[1]);
        } else if (route.equals("quit game")) {
            playAgain();

        } else if (route.equals("attack")) {
            combat();

        } else if (route.equals("inventory")) {
            showInventory();

        } else {
            PROMPTER.info("Invalid command!");
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
                "   *  To win the game successfully deliver all the packages to the correct customers before time runs out.\n"+
                "   *  The user password is " + RED + "password" + RESET);

        PROMPTER.asciiArt("================\\\n" +
                " |----------||@  \\\\   ___\n" +
                " |____|_____|||_/_\\\\_|___|_\n" +
                "<|  ___\\    ||     | ____  |\n" +
                "<| /    |___||_____|/    | |\n" +
                "||/  O  |__________/  O  |_||\n" +
                "   \\___/            \\___/\n");
    }




    //    String streetFight = "yoo";
    public void combat() {
        try {
            String streetFight = data.getJSONObject(currentLocation).get("adversary").toString();
            if (streetFight.equals("thief")) {
                PROMPTER.info("Its GO Time! Protect those packages at all cost!");
                fight();
            }
        } catch (JSONException e) {
            PROMPTER.info("We are delivery drivers. We don't attack unless to protect our packages!");
        }
    }

    private void fight() {
        int playersHealth = 100;
        int thiefHealth = 100;
        while (playersHealth > 0 && thiefHealth > 0) {
            PROMPTER.info("Thief health: " + thiefHealth + " Your health: " + playersHealth);
            String playerAttack = PROMPTER.prompt("Choose your attacks 'A' Punch. 'B' Kick. 'C' BodySlam. 'D' Open Hand smack.");
            if (playerAttack.toLowerCase().equals("a")) {
                PROMPTER.info("Crack! Right in the kisser!");
                thiefHealth = thiefHealth - 25;
            }
            if (playerAttack.toLowerCase().equals("b")) {
                PROMPTER.info("Phenomenal head kick! You may be in the wrong profession here");
                thiefHealth = thiefHealth - 30;
            }
            if (playerAttack.toLowerCase().equals("c")) {
                PROMPTER.info("OHHHHH Snap! You pick the thief up and slammed them!");
                thiefHealth = thiefHealth - 40;

            }
            if (playerAttack.toLowerCase().equals("d")) {
                PROMPTER.info("WHAP! You didn't do much damage but you certainly showed them who's boss!");
                thiefHealth = thiefHealth - 10;
            }
            Random rand = new Random();
            int randomNum = rand.nextInt((3 - 1) + 1) + 1;

            if (randomNum == 1) {
                PROMPTER.info("The thief backhanded you.....Disrespectful");
                playersHealth = playersHealth - 10;
            }
            if (randomNum == 2) {
                PROMPTER.info("thief throws a nasty uppercut that connected...ouch");
                playersHealth = playersHealth - 30;
            }
            if (randomNum == 3) {
                PROMPTER.info("OH no the thief body slammed you into the pavement...That has to hurt");
                playersHealth = playersHealth - 40;
            }

        }
        if (playersHealth <= 0){
            PROMPTER.info("You lost the fight!"); // maybe add a trophy of some sort!
            PROMPTER.info("Bobby Singer package has been stolen");
        }
        if (thiefHealth <= 0 || playersHealth <= 0 && thiefHealth <= 0){
            PROMPTER.info("You won the fight!"); // Do we want the user to restart or continue.
        }
        fightOver = true;
    }

    public void playAgain() {
//        Console.blankLines(1);
        String playAgain = PROMPTER.prompt("Would you like to play again? " +
                        GREEN + " [N]ew Game " + RESET + "/" + YELLOW +
                        "[R]ematch" + RESET + "/" + RED + "[E]xit " + RESET +CYAN + "/" + "[S]ave " + RESET +
                WHITE + "Please enter 'E', 'R','N' or 'S'" + RESET);
        if ("N".equalsIgnoreCase(playAgain)) {
            gameOver = false;

        } else if ("R".equalsIgnoreCase(playAgain)) {
            gameOver = false;
            board.clear();
//            Console.clear();
            currentLocation = "warehouse";
            PROMPTER.info("Hello " + user.getName() + " welcome back for another round of PushinPrime!");
            getCommands();

        }else if ("S".equalsIgnoreCase(playAgain)) {

            board.clear();
            welcome();
            PROMPTER.info("Hello " + user.getName() + "you can resume de game you saved");
            String keepPlaying = PROMPTER.prompt("Would you like to load your saved game?" ).toLowerCase();

            if ("Y".equalsIgnoreCase(keepPlaying) ){
                getCommands();
            }else{
                playAgain();
            }


        }  else {
            gameOver();
        }
        try {
            execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void gameOver() {
        try {
            board.clear();
            String banner = Files.readString(Path.of("resources/thankyou.txt"));
            PROMPTER.asciiArt(banner);
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

    public void countdown() throws InterruptedException {
        board.startClock();

        int timeElapsed = 3;
        long displayMinutes = 0;
        long starttime = System.currentTimeMillis();
        PROMPTER.info(YELLOW + "You have 3 minutes till game over" + RESET);
        getCommands();
        while (playing) {
            //Thread.sleep(1);
            TimeUnit.SECONDS.sleep(1);
            long timepassed = System.currentTimeMillis() - starttime;
            long secondspassed = timepassed / 1000;
            if (secondspassed == 60) {
                secondspassed = 0;
                starttime = System.currentTimeMillis();

            }
            if ((secondspassed % 60) == 0) {
                displayMinutes++;
            }

            if (displayMinutes == timeElapsed && secondspassed == 0) {
                PROMPTER.info("Time is over");
                board.stopClock();
                playAgain();
            }
        }
    }
}

///lasss changes