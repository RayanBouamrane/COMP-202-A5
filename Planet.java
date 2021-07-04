import java.util.ArrayList;
import java.util.Random;

public class Planet {

    private String name;
    private double chance;
    private double damage;

    public Planet(String name, double chance, double damage) {

        this.name = name;

        //checking chance is between 0 and 1, else throws an IllegalArgumentException
        if (chance >= 0 || chance <= 1)
            this.chance = chance;
        else
            throw new IllegalArgumentException("Chance needs to be between 0 and 1 (both included)");

        //checks damage is greater than 0
        if (damage >= 0)
            this.damage = damage;
        else
            throw new IllegalArgumentException("Damage must be greater than 0");
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Name: " + this.name + " | Artifact Chance: " + this.chance * 100 + "% | Max Damage: " + this.damage;
    }

    //if the string planet equals the name of an instance of Planet
    //its index is returned, else -1 is returned
    public static int findPlanet(String planet, ArrayList<Planet> arr) {
        for (int i = 0; i < arr.size(); i++)
            if ((arr.get(i).name).equalsIgnoreCase(planet))
                return i;

        return -1;
    }

    //if the instance of the random class is less than the chance attribute, return true
    public boolean searchForArtifact() {
        Random gen = new Random();
        double rand = gen.nextDouble();
        return rand < this.chance;
    }

    //return damage multiplied by double between 0 and 1
    public double getDamageTaken() {
        Random gen = new Random();
        return this.damage * gen.nextDouble();
    }
}