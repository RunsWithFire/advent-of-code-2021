import java.util.*;
import java.math.*;
import java.io.*;

public class day12 {
    
    public static void main(String args[]) {
        List<String> input = readInputToStringList("day12Input.txt");
        List<Cavern> cavernList = new ArrayList<>();
        Cavern startCavern = buildCavernGraph(input, cavernList);

        List<Cavern> cavernPath = new ArrayList<>();
        System.out.println(getCavernPaths(cavernPath, startCavern));
    }

    public static int getCavernPaths(List<Cavern> cavernPath, Cavern cavern) {
        cavernPath.add(cavern);
        int count = 0;

        if (cavern.name.equals("end")) {
            cavernPath.remove(cavern);
            return 1;
        } else {
            for (Cavern connection : cavern.connections) {
                if ((connection.isLarge || !hasSmallDuplicates(cavernPath) || !cavernPath.contains(connection)) && !connection.name.equals("start")) {
                    count = count + getCavernPaths(cavernPath, connection);
                }
            }
        }

        cavernPath.remove(cavern);
        return count;
    }

    public static boolean hasSmallDuplicates(List<Cavern> cavernPath) {
        boolean hasDuplicates = false;
        Set<Cavern> cavernSet = new HashSet<>();

        for (Cavern cavern : cavernPath) {
            if (!cavern.isLarge && cavernSet.contains(cavern)) {
                hasDuplicates = true;
            }
            cavernSet.add(cavern);
        }

        return hasDuplicates;
    }

    public static Cavern buildCavernGraph(List<String> input, List<Cavern> cavernList) {
        Cavern startCavern = new Cavern("temp");
        for (String item : input) {
            String[] cavernNames = item.split("-");
            Cavern cavern = getOrMakeCavern(cavernList, cavernNames[0]);
            Cavern connection = getOrMakeCavern(cavernList, cavernNames[1]);

            cavern.connections.add(connection);
            connection.connections.add(cavern);
            if (cavern.name.equals("start")) {
                startCavern = cavern;
            } else if (connection.name.equals("start")) {
                startCavern = connection;
            }
        }

        return startCavern;
    }

    public static Cavern getOrMakeCavern(List<Cavern> cavernList, String cavernName) {
        for (Cavern cavern : cavernList) {
            if (cavern.name.equals(cavernName)) {
                return cavern;
            } 
        }
        
        Cavern newCavern = new Cavern(cavernName);
        cavernList.add(newCavern);
        return newCavern;
    }

    static class Cavern {
        String name;
        List<Cavern> connections = new ArrayList<>();
        boolean isLarge;

        public Cavern(String name) {
            this.name = name;

            if (name.equals("start")) {
                this.isLarge = false;
            } else if (name.equals("end")) {
                this.isLarge = false;
            } else if (name.toUpperCase().equals(name)) {
                this.isLarge = true;
            } else {
                this.isLarge = false;
            }
        }
    }

    public static List<String> readInputToStringList(String filename) {
        List<String> inputList = new ArrayList<>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                inputList.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found!");
            e.printStackTrace();
        }

        return inputList;
    }
}