import java.util.*;
import java.math.*;
import java.io.*;

public class day7 {
    
    public static void main(String args[]) {
        List<Integer> input = readSingleLineInputToIntList("day7Input.txt");
        System.out.println(getMinimumFuelDistance(input));
    }

    public static int getMinimumFuelDistance(List<Integer> input) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : input) {
            if (num < min) {
                min = num;
            }
            if (num > max) {
                max = num;
            }
        }
        
        int minFuel = Integer.MAX_VALUE;
        for (int i=min; i<=max; i++) {
            int thisFuel = 0;
            for (int j=0; j<input.size(); j++) {
                int diff = getSumBetweenNumbers(input.get(j), i);
                thisFuel = thisFuel + diff;
            }

            if (thisFuel < minFuel) {
                minFuel = thisFuel;
            }
        }

        return minFuel;
    }

    public static int getSumBetweenNumbers(int x, int y) {
        int diff = Math.abs(x - y);
        int sum = 0;
        int count = 0;
        for (int i=0; i<diff; i++) {
            sum = sum + count + 1;
            count++;
        }
        return sum;
    }

    public static List<Integer> readSingleLineInputToIntList(String filename) {
        List<Integer> inputList = new ArrayList<>();
        
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            String[] splitData = data.split(",");
            for (String num : splitData) {
                int thisNum = Integer.valueOf(num);
                inputList.add(thisNum);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found!");
            e.printStackTrace();
        }

        return inputList;
    }

}
