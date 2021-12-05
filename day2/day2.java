import java.util.*;
import java.math.*;
import java.io.*;

public class day2 {
    
    public static void main(String args[]) {
        List<String> input = readInputToStringList("day2Input.txt");
        System.out.println(getNavigationArea(input));
    }

    public static int getNavigationArea(List<String> input) {
        int depth = 0;
        int position = 0;
        int aim = 0;

        String thisItem;
        String thisCommand;
        int spaceInd;
        int val;

        for (int i=0; i<input.size(); i++) {
            thisItem = input.get(i);
            spaceInd = thisItem.indexOf(" ");
            val = Integer.valueOf(thisItem.substring(spaceInd+1, spaceInd+2));
            thisCommand = thisItem.substring(0, spaceInd);

            if (thisCommand.equals("forward")) {
                position = position + val;
                depth = depth + (aim * val);
            } else if (thisCommand.equals("down")) {
                aim = aim + val;
            } else if (thisCommand.equals("up")) {
                aim = aim - val;
            }
        }

        return depth * position;
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
