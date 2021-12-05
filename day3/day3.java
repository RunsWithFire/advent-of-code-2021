import java.util.*;
import java.math.*;
import java.io.*;

public class day3 {
    
    public static void main(String args[]) {
        List<String> input = readInputToStringList("day3Input.txt");
        System.out.println(getGammaEpsilonReading(input));

        int CO2 = getCO2Reading(new ArrayList<>(input));
        int O2 = getO2Reading(new ArrayList<>(input));
        System.out.println(CO2 * O2);
    }

    public static int getCO2Reading(List<String> input) {
        int zeroCount = 0;
        int oneCount = 0;
        char removalBit;
        List<String> removals = new ArrayList<>();

        for (int i=0; i<input.get(0).length(); i++) {
            zeroCount = 0;
            oneCount = 0;

            for (int j=0; j<input.size(); j++) {
                if (input.get(j).charAt(i) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }

            if (oneCount >= zeroCount) {
                removalBit = '1';
            } else {
                removalBit = '0';
            }

            for (int j=0; j<input.size(); j++) {
                if (input.get(j).charAt(i) == removalBit) {
                    removals.add(input.get(j));
                }
            }

            for (int j=0; j<removals.size(); j++) {
                input.remove(removals.get(j));
            }
            removals.clear();

            if (input.size() == 1) {
                return Integer.parseInt(input.get(0), 2);
            }
        }

        return 0;
    }

    public static int getO2Reading(List<String> input) {
        int zeroCount = 0;
        int oneCount = 0;
        char removalBit;
        List<String> removals = new ArrayList<>();

        for (int i=0; i<input.get(0).length(); i++) {
            zeroCount = 0;
            oneCount = 0;

            for (int j=0; j<input.size(); j++) {
                if (input.get(j).charAt(i) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }

            if (zeroCount > oneCount) {
                removalBit = '1';
            } else {
                removalBit = '0';
            }

            for (int j=0; j<input.size(); j++) {
                if (input.get(j).charAt(i) == removalBit) {
                    removals.add(input.get(j));
                }
            }

            for (int j=0; j<removals.size(); j++) {
                input.remove(removals.get(j));
            }
            removals.clear();

            if (input.size() == 1) {
                return Integer.parseInt(input.get(0), 2);
            }
        }

        return 0;
    }

    public static int getGammaEpsilonReading(List<String> input) {
        StringBuilder epsilonBuilder = new StringBuilder();
        StringBuilder gammaBuilder = new StringBuilder();
        int zeroCount = 0;
        int oneCount = 0;
        int epsilon;
        int gamma;

        for (int i=0; i<input.get(0).length(); i++) {
            zeroCount = 0;
            oneCount = 0;
            for (int j=0; j<input.size(); j++) {
                if (input.get(j).charAt(i) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }

            if (oneCount > zeroCount) {
                gammaBuilder.append('1');
                epsilonBuilder.append('0');
            } else {
                gammaBuilder.append('0');
                epsilonBuilder.append('1');
            }
        }

        epsilon = Integer.parseInt(epsilonBuilder.toString(), 2);
        gamma = Integer.parseInt(gammaBuilder.toString(), 2);

        return epsilon * gamma;
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
