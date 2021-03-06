package com.servermonks.pushinprime.app;

import com.servermonks.pushinprime.Board;
import com.servermonks.pushinprime.FileDataReader;
import com.servermonks.pushinprime.MysticSquare;
import com.servermonks.pushinprime.Prompter;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.servermonks.pushinprime.Colors.*;


public class PushinPrimeApp {

    public static ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
    private Board board = Board.getInstance();
    private Prompter PROMPTER = new Prompter(board);

    private FileDataReader data;
    private final String START_LOCATION = "warehouse";
    private String currentLocation = START_LOCATION;
    private Hashtable<String, Boolean> deliveryStatus = new Hashtable<>();


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
        data = new FileDataReader();
        distributePackages();
        welcome();
        howToPlay();
        promptForUsername();
        countdown();
    }

    private void welcome() {
        PROMPTER.info("<img src=\"https://res.cloudinary.com/dile8hu1p/image/upload/v1655777095/pushinPrime_banner_t2m09s.png\" '/>");
        PROMPTER.info("In pushingPrime you are an Amazon delivery driver. You are responsible for maximizing customer satisfaction by transporting and delivering goods, merchandise, and/or other items in a safe and timely manner.");
    }

    public void help() {
        PROMPTER.info(" ");

        PROMPTER.info(CYAN + "Seems that you need some Help!\n"+
                "* To move type " + ORANGE + " go " + CYAN + " and the direction you want move " + GREEN + "(go north) " + CYAN + "\n" +
                "* To look around type " + ORANGE + "look " + CYAN + "you will find useful items to complete your journey.\n" +
                "* To pick up an item type" + ORANGE + " get, grab, take or pick up " + CYAN + "and the item " + GREEN + "(get snacks)" + CYAN + "\n" +
                "* If you miss a delivery type" + ORANGE + " deliver" + CYAN + " and you will be prompted to deliver package" + CYAN + "\n" +
                "* If you have a map you can type " + ORANGE + "map " + CYAN + "to see your location.\n" +
                "* Type " + ORANGE + "heal " + CYAN + "to heal yourself when needed.\n" +
                "* To mute volume press the " +  ORANGE +"&#8594;" + CYAN + " key, to un-mute press &#8592; \n" +
                "* To lower volume press the " + ORANGE + "&#8595;" + CYAN +" key, to raise press &#8593; \n" +
                "* To quit game type " + ORANGE +"quit game."+ RESET );

    }

    private void howToPlay() {
        PROMPTER.info("");
        PROMPTER.info(ORANGE + "How to play:" + RESET + "\n" + CYAN +
                "   *  Deliver the correct packages to customers to earn customer satisfaction points.\n" +
                "   *  As you explore the streets new locations will become available to explore.\n" +
                "   *  A direction guide will show up under ever location to show what location can be explored next. \n" +
                "   *  Don't forget your being timed. You got this! \n" +
                "   *  If you need help type " + ORANGE + " help. \n"+ CYAN +
                "   *  The user password is " + ORANGE  + "password." + RESET);

        PROMPTER.asciiArt(ORANGE + "================\\\n" +
                " |----------||@  \\\\   ___\n" +
                " |____|_____|||_/_\\\\_|___|_\n" +
                "<|  ___\\    ||     | ____  |\n" +
                "<| /    |___||_____|/    | |\n" +
                "||/  O  |__________/  O  |_||\n" +
                "  \\___/            \\___/\n" + RESET);
    }

    public void showStatus() {
        PROMPTER.info(" ");

        PROMPTER.info(CYAN + "You are in the " + ORANGE + currentLocation + CYAN + " from here you can go" + RESET);
        PROMPTER.info(data.getDirections(currentLocation));
        String skidRow = data.getAdversary(currentLocation);

        if (skidRow.equals("thief") && !fightOver) {
            PROMPTER.info(CYAN + "The neighborhood " + RED + "thief" + CYAN + " is coming straight towards you! use " + ORANGE + " Attack " + CYAN + "to fight for your packages!" + RESET);
        }
    }

    public void getItem(String item) {
        PROMPTER.info(" ");
        List inventory = user.getInventory();
        try {
            String[] items = data.getItems(currentLocation).join("-").split("-");

            for (int i = 0; i < items.length; i++) {
                if (items[i].toLowerCase().contains(item)) {
                    inventory.add(item);
                    user.setInventory(inventory);
                    data.getItems(currentLocation).remove(i);
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
                JSONArray locationItems = data.getItems(currentLocation);
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

    public void locations() {
        PROMPTER.info("");
        PROMPTER.info(CYAN + "Here is a list of locations you can go" + RESET);
        PROMPTER.info(data.getDirections(currentLocation));
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

        String username = PROMPTER.prompt("username ");
        password = PROMPTER.prompt("password ");
        user = new Player(username);
        int totalAttempts = 2;

        while (totalAttempts != 0) {
            if (password.equals("password")) {
                PROMPTER.info("Authenticating....please wait");
                Thread.sleep(3000);
                PROMPTER.info("Authentication Successful !\n");
                PROMPTER.info(" ");
                PROMPTER.info(CYAN +"Welcome " + ORANGE +  username  + CYAN + " to your first day as a Prime Driver!");
                PROMPTER.info(CYAN +"Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
                PROMPTER.info(CYAN +"The orders for today are:"+ RESET);
                PROMPTER.info("");
                PROMPTER.info(data.getOrders());
                PROMPTER.prompt( "If you have your order memorized Type 'Ready' to start your journey!");
                board.clear();
                howToPlay();
                break;

            } else if (!password.equals("password")) {
                String tryAgain = PROMPTER.prompt("Invalid password,try again:");
                if (tryAgain.equals("password")) {
                    PROMPTER.info("Authenticating....please wait");
                    Thread.sleep(3000);
                    PROMPTER.info("Authentication Successful !\n");
                    PROMPTER.info(" ");
                    PROMPTER.info(CYAN +"Welcome "+ ORANGE +  username  + CYAN +" to your first day as a Prime Driver");
                    PROMPTER.info(CYAN +"Your mission today is to deliver all of the packages correctly to our customers. I hope you're up for the challenge!");
                    PROMPTER.info(CYAN +"The orders for today are:"+ RESET);
                    PROMPTER.info("");
                    PROMPTER.info(data.getOrders());
                    PROMPTER.prompt( "If you have your order memorized Type 'Ready' to start your journey");
                    board.clear();
                    howToPlay();
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
            String nextLocation = data.goToLocation(currentLocation, commands[1]) == null ? currentLocation : data.goToLocation(currentLocation, commands[1]);
            currentLocation = nextLocation;
            showStatus();
            askForPackage();

        } else if (commands[0].equals("look")) {
            user.look(data, currentLocation);

        } else if (route.equals("deliver")) {
            askForPackage();
        } else if (route.equals("location")) {
            locations();
        } else if (commands[0].equals("grab") || commands[0].equals("take") || commands[0].equals("pick up")
                || commands[0].equals("get")) {
            getItem(commands[1]);
        } else if (route.equals("talk")) {
            user.talk(data, currentLocation);

        } else if (route.equals("heal")) {
            user.heal();
        } else if (route.equals("health")) {
            PROMPTER.info("your health is: " + user.getHealth());
        } else if (commands[0].equals("drop")) {
            dropItem(commands[1]);
        } else if (route.equals("map")) {
      if(user.getInventory().contains("map")){
            map();
       }else{
          PROMPTER.info("");
           PROMPTER.info(RED + "It seems that you don't have any map in your inventory!" + RESET);
        }
      }else if (route.equals("quit game")) {
            playAgain();

        } else if (route.equals("attack")) {
            combat();

        } else if (route.equals("inventory")) {
            showInventory();

        } else {
            PROMPTER.info("Invalid command!");
            help();
        }

        checkForWinner();
        getCommands();

    }


    //    String streetFight = "yoo";
    public void combat() {
        String streetFight = data.getAdversary(currentLocation);
        if (streetFight.equals("thief")) {
            fight();
        }
    }

    private void fight() {

        int thiefHealth = 100;
        while (user.getHealth() > 0 && thiefHealth > 0) {
            PROMPTER.info("Thief health: " + thiefHealth + " Your health: " + user.getHealth());
            String playerAttack = PROMPTER.prompt("Choose your attacks: \n (A) Punch.\n (B) Kick. \n (C) BodySlam.\n (D) Open Hand smack.").toLowerCase();
            if (playerAttack.equals("a")) {
                PROMPTER.info(ORANGE + "Crack! Right in the kisser!" + RESET);
                thiefHealth = thiefHealth - 25;
            }
            if (playerAttack.equals("b")) {
                PROMPTER.info(ORANGE + "Phenomenal head kick! You may be in the wrong profession here" + RESET);
                thiefHealth = thiefHealth - 30;
            }
            if (playerAttack.equals("c")) {
                PROMPTER.info( ORANGE + "OHHHHH Snap! You pick the thief up and slammed them!" + RESET);
                thiefHealth = thiefHealth - 40;
            }
            if (playerAttack.equals("d")) {
                PROMPTER.info(ORANGE + "WHAP! You didn't do much damage but you certainly showed them who's boss!" + RESET);
                thiefHealth = thiefHealth - 10;
            }
            Random rand = new Random();
            int randomNum = rand.nextInt((3 - 1) + 1) + 1;
            if (randomNum == 1) {
                PROMPTER.info(RED + "The thief backhanded you.....Disrespectful" + RESET);
                user.setHealth(user.getHealth() - 10);
            }
            if (randomNum == 2) {
                PROMPTER.info(RED + "thief throws a nasty uppercut that connected...ouch" + RESET);
                user.setHealth(user.getHealth() - 30);
            }
            if (randomNum == 3) {
                PROMPTER.info(RED + "OH no the thief body slammed you into the pavement...That has to hurt" + RESET);
                user.setHealth(user.getHealth() - 40);
            }
        }
        String badge = "PrimeMedallion";
        if (user.getHealth() > thiefHealth || user.getHealth() == thiefHealth) {
            PROMPTER.info(GREEN + "You fought like a pro !" + RESET);
            PROMPTER.info(GREEN + "You have earned yourself a " + RESET + ORANGE + badge + RESET);
        } else {
            try{
                PROMPTER.info(RED + "The thief won :( " + RESET);
                PROMPTER.info(RED + "You live to fight another day" + RESET);
                board.clear();
                String banner = Files.readString(Path.of("resources/loser"));
                PROMPTER.asciiArt(banner);
                Thread.sleep(3000);
                gameOver();
            }
            catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        fightOver = true;
    }

    public void playAgain() {


        String playAgain = PROMPTER.prompt("Would you like to play again? " +
                GREEN + " [N]ew Game " + RESET + YELLOW +
                "[R]ematch" + RESET + RED + " [E]xit" + RESET + CYAN + " [S]ave " + RESET,
                "^[EeRrNnSs]{1}$","Please enter 'E', 'R', 'N' or 'S'");

//        PROMPTER.info("Would you like to play again? " +
//                GREEN + " [N]ew Game " + RESET + "/" + YELLOW +
//                "[R]ematch" + RESET + "/" + RED + "[E]xit " + RESET + CYAN + "/" + "[S]ave " + RESET + "\n" +
//                ORANGE + "Please enter 'N', 'R','E' or 'S'" + RESET);
//
//        String playAgain = PROMPTER.prompt("");


        if ("N".equalsIgnoreCase(playAgain)) {
            try {
                board.stopClock();
                currentLocation = START_LOCATION;
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if ("R".equalsIgnoreCase(playAgain)) {
            board.clear();

            currentLocation = START_LOCATION;

            PROMPTER.info("Hello " + user.getName() + " welcome back for another round of PushinPrime!");
            getCommands();

        } else if ("S".equalsIgnoreCase(playAgain)) {
            board.clear();
            welcome();
            PROMPTER.info("Hello " + user.getName() + " you can resume de game you saved");
            String keepPlaying = PROMPTER.prompt("Would you like to load your saved game?").toLowerCase();
            if (keepPlaying.equals("y")) {
                getCommands();
            } else {
                playAgain();
            }

        } else {
            gameOver();
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
        int timeElapsed = 6;
        long displayMinutes = 0;
        long startTime = System.currentTimeMillis();
        PROMPTER.info(CYAN + "You have " + RED + timeElapsed + CYAN + " minutes till game over" + RESET);
        getCommands();
        while (playing) {
            TimeUnit.SECONDS.sleep(1);
            long timePassed = System.currentTimeMillis() - startTime;
            long secondsPassed = timePassed / 1000;
            if (secondsPassed == 60) {
                secondsPassed = 0;
                startTime = System.currentTimeMillis();
            }
            if ((secondsPassed % 60) == 0) {
                displayMinutes++;
            }

            if (displayMinutes == timeElapsed && secondsPassed == 0) {
                PROMPTER.info("Time is over");
                board.stopClock();
                playAgain();
            }
        }
    }


    public void playMysticSquare() {
        MysticSquare mysticSquare = new MysticSquare();
        mysticSquare.createBoard();
        String gameBoard = mysticSquare.showBoard();
        System.out.println(gameBoard);
        PROMPTER.info(gameBoard);
        while(!mysticSquare.isOver()) {
            PROMPTER.prompt("choose a square: " + mysticSquare.showValidMoves());
        }
    }


    public void map() {
        if(currentLocation.equals("Ballard")){
            PROMPTER.info("<img src=\"https://res.cloudinary.com/dile8hu1p/image/upload/c_scale,w_386/v1655776418/ballard_q45ymc.png\" '/>");
        }else if(currentLocation.equals("warehouse")){
            PROMPTER.info("<img src=\"https://res.cloudinary.com/dile8hu1p/image/upload/c_scale,w_386/v1655776451/warehouse_hsdhze.png\" '/>");
        }
        else if(currentLocation.equals("waterlow row")){
            PROMPTER.info("<img src=\"https://res.cloudinary.com/dile8hu1p/image/upload/c_scale,w_386/v1655776457/water_w52jzo.png\" '/>");
        }
        else if(currentLocation.equals("hollywood blvd")){
            PROMPTER.info("<img src=\"https://res.cloudinary.com/dile8hu1p/image/upload/c_scale,w_386/v1655776438/hollywood_za0zlp.png\" '/>");
        }
        else if(currentLocation.equals("sunnyside park")){
            PROMPTER.info("<img src=\"https://res.cloudinary.com/dile8hu1p/image/upload/c_scale,w_386/v1655776444/sunnyside_xcdujb.png\" '/>");
        }

    }

    private void distributePackages() {
        int packagesNum = 2;
        int random = 0;
        try {
            String[] packages = data.getPackages(START_LOCATION).join("-").split("-");

            ArrayList<String> customers = data.getKeys();
            ArrayList<String> setOfPackages = new ArrayList<>();


            for (int i = 0; i < customers.size(); i++) {

                JSONArray custPackages = data.getPackages(customers.get(i));

                while (custPackages.length() < packagesNum) {
                    random = (int) (Math.random() * packages.length);

                    if (!setOfPackages.contains(packages[random])) {

                        setOfPackages.add(packages[random]);

                        custPackages = custPackages.put(custPackages.length(), packages[random]);
                    }


                }
                deliveryStatus.put(customers.get(i), false);
            }


        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

    public void askForPackage() {
        ArrayList<String> locations = data.getKeys();
        int counter = 0;
        ArrayList<JSONArray> customerOrders = new ArrayList<>();
        ArrayList<JSONArray> randomDisplay = new ArrayList<>();

        if (locations.contains(currentLocation) && deliveryStatus.get(currentLocation).equals(false)) {

            PROMPTER.info(CYAN + "You can deliver your package here" +
                    ORANGE + " talk " + RESET + CYAN +
                    "to the customer to get their name and deliver the package" + RESET);
            String playerTalks = PROMPTER.prompt("Talk?").toLowerCase();

            if (playerTalks.equals("talk")) {
                int random = 0;
                ArrayList<Integer> temp = new ArrayList<>();
                user.talk(data, currentLocation);
                for (int i = 0; i < locations.size(); i++) {

                    int asciiValue = 65 + i;
                    char convertedChar = (char) asciiValue;
                    random = (int) (Math.random() * locations.size());

                    if (!temp.contains(random)) {
                        temp.add(random);
                        randomDisplay.add(data.getPackages(locations.get(random)));
                        PROMPTER.info(convertedChar + " : " + data.getPackages(locations.get(random)));
                    } else {
                        i -= 1;
                    }
                    if (counter < locations.size()) {
                        customerOrders.add(data.getPackages(locations.get(counter)));
                        counter++;
                    }

                }


                String deliverPackage = PROMPTER.prompt("Please choose from the following packages");
                int index = deliverPackage.toUpperCase().charAt(0) - 65;

                if (index < locations.size()) {
                    if (data.getPackages(currentLocation).equals(randomDisplay.get(index))) {
                        user.setCustomerSatisfaction(user.getCustomerSatisfaction() + (100 / locations.size()));
                        PROMPTER.info("Congrats! " + data.getNpc(currentLocation) + " is happy with the service");
                        PROMPTER.info("your customer satisfaction is: " + user.getCustomerSatisfaction());
                        deliveryStatus.put(currentLocation, true);

                    } else {
                        user.setCustomerSatisfaction(user.getCustomerSatisfaction() - (100 / locations.size()));
                        PROMPTER.info(data.getNpc(currentLocation) + " says sorry that was not what I ordered, I want a refund!");
                        PROMPTER.info("your customer satisfaction is: " + user.getCustomerSatisfaction());
                        deliveryStatus.put(currentLocation, true);
                    }
                } else {
                    PROMPTER.info("That is not a correct option! Please try to " +
                            GREEN + "deliver" + RESET +
                            " again. ");
                }
            }
        } else {
            PROMPTER.info("");
            PROMPTER.info(RED + "It seems that you have been here already! This customer already sent feedback of your service." + RESET);
            user.talk(data, currentLocation);
        }
    }

    private void checkForWinner() {
        if (!deliveryStatus.values().contains(false)) {
            if (user.getCustomerSatisfaction() > 50) {
                try {
                    board.clear();

                    String banner = Files.readString(Path.of("resources/winner"));
                    PROMPTER.asciiArt(banner);
                    PROMPTER.info("Customer satisfaction: " + user.getCustomerSatisfaction());
                    Thread.sleep(3000);
                    playAgain();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    board.clear();
                    String banner = Files.readString(Path.of("resources/loser"));
                    PROMPTER.asciiArt(banner);
                    PROMPTER.info("Sorry you couldn't complete the challenge. Your Customer satisfaction: " + user.getCustomerSatisfaction() + " Is to low for a PushinPrime Driver");
                    Thread.sleep(3000);
                    playAgain();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}

