import java.io.*;
import java.util.ArrayList;

public class FileIO {

    public static Spaceship loadSpaceship(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String currentline = br.readLine();

            //array with 3 entries created, each index stores one line read by br
            String[] a = new String[3];
            for (int i = 0; i < 3 && currentline != null; i++) {
                a[i] = currentline;
                currentline = br.readLine();
            }
            br.close();
            //a new Spaceship is created by calling the constructor, index 0 is passed as a String (name)
            //index 1 is parsed into a double (health), index 2 is parsed into an int (numWins)
            return new Spaceship(a[0], Double.parseDouble(a[1]), Integer.parseInt(a[2]));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file: " + filename);
        } catch (IOException e) {
            throw new IllegalArgumentException("IO Exception: " + filename);
        }
    }

    public static ArrayList<Planet> loadPlanets(String filename) {

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String currentline = br.readLine();

            ArrayList<Planet> a = new ArrayList<>();

            //for every non null line in the file, an array consisting of each line split on " " is created
            //a new planet is created and added to ArrayList<Planet> by calling the constructor with parsed parameters
            while (currentline != null) {
                String[] arr = currentline.split(" ");

                Planet p = new Planet(arr[0], Double.parseDouble(arr[1]), Integer.parseInt(arr[2]));
                a.add(p);
                currentline = br.readLine();
            }
            br.close();
            return a;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file: " + filename);
        } catch (IOException e) {
            throw new IllegalArgumentException("IO Exception: " + filename);
        }
    }

    //a String consisting of the ship name, line break, ship health, line break, and numWins is written to file
    public static void saveSpaceship(Spaceship ship, String filename) throws IOException {

        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(ship.getName() + "\n" + ship.getMaxHealth() + "\n" + ship.getNumWins());

        bw.close();
    }
}