import java.util.*;
import java.math.*;
import java.io.*;

public class day6 {
    
    public static void main(String args[]) {
        List<Long> input = readSingleLineInputToLongListCount("day6Input.txt");
        //System.out.println(getCountLanternFish(input, 80));
        System.out.println(getCountLanternFish(input, 256));
    }

    public static Long getCountLanternFish(List<Long> input, int days) {
        Long count = 0L;
        for (int i=0; i<days; i++) {
            Long temp = input.get(0);
            for (int j=1; j<input.size(); j++) {
                input.set(j-1, input.get(j));
            }
            input.set(6, input.get(6) + temp);
            input.set(8, temp);
        }

        for (int i=0; i<input.size(); i++) {
            count = count + input.get(i);
        }

        return count;
    }

    public static List<Long> readSingleLineInputToLongListCount(String filename) {
        List<Long> inputList = new ArrayList<>();
        inputList.add(0L); // max number is 8 (zero index)
        inputList.add(0L);
        inputList.add(0L);
        inputList.add(0L);
        inputList.add(0L);
        inputList.add(0L);
        inputList.add(0L);
        inputList.add(0L);
        inputList.add(0L);
        
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            String[] splitData = data.split(",");
            for (String num : splitData) {
                int thisNum = Integer.valueOf(num);
                inputList.set(thisNum, inputList.get(thisNum)+1);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found!");
            e.printStackTrace();
        }

        return inputList;
    }

}
