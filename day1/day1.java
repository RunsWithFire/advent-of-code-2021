import java.util.*;
import java.math.*;
import java.io.*;

public class day1 {
    
    public static void main(String args[]) {
        List<Integer> input = readInputToIntList("day1Input.txt");
        System.out.println(getDepthIncreases(input));
    }

    public static int getDepthIncreases(List<Integer> input) {
        int count = 0;
        int prevSum = Integer.MAX_VALUE;
        int curSum = 0;
        for (int i=2; i<input.size(); i++) {
            curSum = input.get(i) + input.get(i-1) + input.get(i-2);
            if (curSum > prevSum) {
                count++;
            }
            prevSum = curSum;
        }
        return count;
    }

    public static List<Integer> readInputToIntList(String filename) {
        List<Integer> inputList = new ArrayList<>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                inputList.add(Integer.valueOf(data));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found!");
            e.printStackTrace();
        }

        return inputList;
    }

}
