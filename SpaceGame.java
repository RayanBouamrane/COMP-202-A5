import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SpaceGame {

    private Scanner scan;
    private Spaceship player;
    private Spaceship enemy;
    private static final int NUM_ARTIFACTS_WIN = 5;

    public SpaceGame(String solarSystem) {

        player = FileIO.loadSpaceship("player.txt");
        enemy = FileIO.loadSpaceship("enemy.txt");

        System.out.println("Welcome to the cold dead vacuum of space...");

        ArrayList<Planet> planets = new ArrayList<Planet>();

        scan = new Scanner(System.in);

        //planets are loaded an ArrayList created above
        try {
            planets = FileIO.loadPlanets(solarSystem);
        } catch (IllegalArgumentException e) {
            System.out.println("This is not a valid file name for a Solar System.");
        }

        System.out.println("Solar system obtained from " + solarSystem + "\n");

        Spaceship.setPlanets(planets);

        //the first and last planet names are stored as Strings. ships moveTo their respective planets
        String sP = planets.get(0).getName();
        player.moveTo(sP);
        String sE = planets.get(planets.size() - 1).getName();
        enemy.moveTo(sE);

        System.out.println("\nYou must find " + NUM_ARTIFACTS_WIN + " artifacts to survive.");
    }

    //if the player dies, 1 is returned. If the enemy dies, 2 is returned
    //if the game should continue, 0 is returned
    private int checkForDestroyed() {
        if (this.player.getHealth() <= 0)
            return 1;
        else if (this.enemy.getHealth() <= 0)
            return 2;
        else
            return 0;
    }

    //checks either ship has obtained 5 artifacts
    private int checkForWins() {
        if (this.player.getNumArtifacts() >= NUM_ARTIFACTS_WIN)
            return 1;
        else if (this.enemy.getNumArtifacts() >= NUM_ARTIFACTS_WIN)
            return 2;
        else
            return 0;
    }

    public void playGame() {

        //these if conditions call values based on valid user inputs into a scanner
        while (this.checkForDestroyed() == 0 && this.checkForWins() == 0) {
            System.out.println("\nWhat is your command:");
            scan = new Scanner(System.in);
            String input = scan.next();
            if (input.equalsIgnoreCase("moveIn"))
                this.player.moveIn();
            else if (input.equalsIgnoreCase("moveOut"))
                this.player.moveOut();
            else if (input.equalsIgnoreCase("moveTo")) {
                System.out.println("Where to, Captain?");
                input = scan.next();
                this.player.moveTo(input);
            } else if (input.equalsIgnoreCase("search"))
                player.doSearch();
            else {
                System.out.println("These are not the commands I'm looking for.");
                System.out.println("What do you mean by: " + input + "?");
            }

            System.out.println();

            //artificial intelligence. int consisting of 0, 1 or 2 is assigned to r
            //enemy acts according to the value of r
            Random rand = new Random();
            int r = (int) (3 * rand.nextDouble());

            if (r == 0)
                enemy.doSearch();
            else if (r == 1)
                enemy.moveIn();
            else if (r == 2)
                enemy.moveOut();

        }

        //player wins if they find 5 artifacts or enemy explodes
        if (this.checkForDestroyed() == 2 || this.checkForWins() == 1) {
            player.increaseWins();
            System.out.println("You win! Another happy landing!");
        }
        //enemy wins if it finds 5 artifacts or player explodes
        if (this.checkForDestroyed() == 1 || this.checkForWins() == 2) {
            enemy.increaseWins();
            System.out.println("Failed you have. Into exile you must go.");
        }

        System.out.println("\n" + player.getName() + " has won: " + player.getNumWins() + " times.");
        System.out.println(enemy.getName() + " has won: " + enemy.getNumWins() + " times.");

        //writing to files done by calling saveSpaceship with spaceship and filename as parameters
        //as saveSpaceship throws exceptions, they must be caught here
        try {
            FileIO.saveSpaceship(player, "player.txt");
            System.out.println("File: player.txt successfully overwritten");
        } catch (IOException e) {
            System.out.println("IO Exception: player.txt");
        }

        try {
            FileIO.saveSpaceship(enemy, "enemy.txt");
            System.out.println("File: enemy.txt successfully overwritten");
        } catch (IOException e) {
            System.out.println("IO Exception: enemy.txt");
        }
    }
}