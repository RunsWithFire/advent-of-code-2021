import java.util.*;
import java.math.*;
import java.io.*;

public class day13 {
    
    public static void main(String args[]) {
        List<String> foldInstructions = new ArrayList<>();
        char[][] input = readDay13Input(foldInstructions);

        for (String instruction : foldInstructions) {
            String[] instructionSplit = instruction.split("=");
            char direction = instructionSplit[0].charAt(instructionSplit[0].length()-1);
            int lineNum = Integer.valueOf(instructionSplit[1]);

            if (direction == 'x') {
                input = foldLeft(input, lineNum);
            } else {
                input = foldUp(input, lineNum);
            }
        }

        for (int i=0; i<input.length; i++) {
            for (int j=0; j<input[i].length; j++) {
                System.out.print(input[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println(countDots(input));
    }

    public static int countDots(char[][] input) {
        int count = 0;
        for (int i=0; i<input.length; i++) {
            for (int j=0; j<input[i].length; j++) {
                if (input[i][j] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    public static char[][] foldLeft(char[][] input, int lineNum) {
        char[][] output = new char[input.length][lineNum];
        for (int i=0; i<output.length; i++) {
            for (int j=0; j<output[i].length; j++) {
                output[i][j] = '.';
            }
        }

        for (int i=0; i<output.length; i++) {
            for (int j=0; j<output[i].length; j++) {
                if (input[i][j] == '#') {
                    output[i][j] = '#';
                }
            }

            for (int j=lineNum+1; j<input[0].length; j++) {
                if (input[i][j] == '#') {
                    output[i][lineNum-(j-lineNum)] = '#';
                }
            }
        }

        return output;
    }

    public static char[][] foldUp(char[][] input, int lineNum) {
        char[][] output = new char[lineNum][input[0].length];
        for (int i=0; i<output.length; i++) {
            for (int j=0; j<output[i].length; j++) {
                output[i][j] = '.';
            }
        }

        for (int i=0; i<output.length; i++) {
            for (int j=0; j<output[i].length; j++) {
                if (input[i][j] == '#') {
                    output[i][j] = '#';
                }
            }
        }

        for (int i=lineNum+1; i<input.length; i++) {
            for (int j=0; j<output[0].length; j++) {
                if (input[i][j] == '#') {
                    output[lineNum-(i-lineNum)][j] = '#';
                }
            }
        }

        return output;
    }

    public static char[][] readDay13Input(List<String> instructions) {
        try {
            File myObj = new File("day13Input.txt");
            Scanner myReader1 = new Scanner(myObj);
            Scanner myReader2 = new Scanner(myObj);
            int maxCol = Integer.MIN_VALUE;
            int maxRow = Integer.MIN_VALUE;

            while(myReader1.hasNextLine()) {
                String data = myReader1.nextLine();
                if (data.contains(",")) {
                    String[] splitData = data.split(",");
                    if (Integer.valueOf(splitData[0]) > maxCol) {
                        maxCol = Integer.valueOf(splitData[0]);
                    }
                    if (Integer.valueOf(splitData[1]) > maxRow) {
                        maxRow = Integer.valueOf(splitData[1]);
                    }
                }
            }

            char[][] input = new char[maxRow+1][maxCol+1];
            while (myReader2.hasNextLine()) {
                String data = myReader2.nextLine();
                
                if (data.contains(",")) {
                    String[] splitData = data.split(",");
                    input[Integer.valueOf(splitData[1])][Integer.valueOf(splitData[0])] = '#';
                } else if (data.contains("=")) {
                    instructions.add(data);
                }
            }
            myReader1.close();
            myReader2.close();

            for (int i=0; i<input.length; i++) {
                for (int j=0; j<input[i].length; j++) {
                    if (input[i][j] != '#') {
                        input[i][j] = '.';
                    }
                }
            }

            return input;
        } catch (FileNotFoundException e) {
            System.out.println("day13Input.txt not found!");
            e.printStackTrace();
        }

        return new char[0][0];
    }

}
