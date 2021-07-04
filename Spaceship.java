import java.util.ArrayList;

public class Spaceship {

    private String name;
    private double maxHealth;
    private double health;
    private int numArtifacts;
    private int numWins;
    private Planet currPlanet;
    private static ArrayList<Planet> planets;

    //attributes name, maxHealth and numWins are assigned, IllegalArgumentException are thrown for
    //negative heealth and negative number of wins
    public Spaceship(String name, double maxHealth, int numWins) {

        this.name = name;
        if (maxHealth > 0) {
            this.maxHealth = maxHealth;
            this.health = maxHealth;
        } else
            throw new IllegalArgumentException("Your spaceship needs health!");
        if (numWins >= 0)
            this.numWins = numWins;
        else
            throw new IllegalArgumentException("You need to input a valid number of wins.");
    }

    //different strings are returned if number of wins is singular or plural
    public String toString() {
        if (this.numArtifacts == 1)
            return this.name + " has " + String.format("%1$.2f", health) + " health, it has found "
                    + this.numArtifacts + " artifact.";
        else
            return this.name + " has " + String.format("%1$.2f", health) + " health, it has found "
                    + this.numArtifacts + " artifacts.";
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public int getNumWins() {
        return numWins;
    }

    //new ArrayList is created and assigned the same values as parameter Planets
    public static void setPlanets(ArrayList<Planet> Planets) {
        planets = new ArrayList<Planet>(Planets);

        for (int i = 0; i < planets.size(); i++)
            System.out.println(planets.get(i));
    }

    //the index of the planet we move to is obtained, if it is valid (!=-1)
    //currPlanet is assigned the value of the planet at index "index"
    public void moveTo(String destination) {

        int index = Planet.findPlanet(destination, planets);
        if (index != -1) {
            this.currPlanet = planets.get(index);
            System.out.println(this.name + " has moved to " + planets.get(index).getName() + ".");
        } else
            System.out.println("It looks like the system you're searching for doesn't exist.");
    }

    //if the index of currPlanet is 0, we can't move in any closer
    //otherwise, call moveTo with the name of the planet one index lower as a parameter
    public void moveIn() {

        int index = Planet.findPlanet(this.currPlanet.getName(), planets);

        if (index == 0)
            System.out.println(this.name + " couldn't move in. We're as close as we're gonna get.");
        else
            this.moveTo(planets.get(index - 1).getName());
    }

    //first we check the index is not 1 lower than the size of the ArrayList (last value)
    //if it is, call moveTo with the name of the planet one index greater
    public void moveOut() {

        int index = Planet.findPlanet(this.currPlanet.getName(), planets);

        if (index == planets.size() - 1)
            System.out.println(this.name + " couldn't move out. We're as far out as we can be.");
        else
            this.moveTo(planets.get(index + 1).getName());
    }

    public void increaseWins() {
        this.numWins++;
    }

    public double getHealth() {
        return health;
    }

    public int getNumArtifacts() {
        return numArtifacts;
    }

    public void doSearch() {

        //value of calling searchForArtifact stored as boolean variable
        //if true, increase number of artifacts, then it prints messages indicating if boolean is true or false
        boolean numberArtifacts = currPlanet.searchForArtifact();
        if (numberArtifacts) {
            numArtifacts++;
            System.out.println(name + " has found an artifact.");

        } else
            System.out.println(name + " has not found an artifact.");

        //getDamageTaken stored as double. damageStr is a formatted string of damage
        double damage = currPlanet.getDamageTaken();
        String damageStr = String.format("%1$.2f", damage);

        health -= damage;

        //message indicating damage taken printed first, then message telling the player if they are alive is printed
        System.out.println(name + " has taken " + damageStr + " damage on " + currPlanet.getName());
        if (health <= 0)
            System.out.println(name + " has been destroyed by the Empire.");
        else
            System.out.println(this);
    }
}