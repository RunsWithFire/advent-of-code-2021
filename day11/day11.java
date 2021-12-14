import java.util.*;
import java.math.*;
import java.io.*;

public class day11 {
    
    public static void main(String args[]) {
        // Again - not clean but I'm playing catchup so I'm just trying to make it work.
        int[][] input = readInputToIntMatrix10("day11Input.txt");
        System.out.println(getFlashCount(input));
    }

    public static int getFlashCount(int[][] input) {
        int steps = 1000;
        int flashCount = 0;
        int thisStepFlashCount = 0;

        for (int i=0; i<steps; i++) {
            boolean[][] hasFlashed = new boolean[10][10];
            thisStepFlashCount = 0;

            // Step 1
            for (int j=0; j<input.length; j++) {
                for (int k=0; k<input[j].length; k++) {
                    input[j][k] = input[j][k] + 1;
                }
            }

            // Step 2
            for (int j=0; j<input.length; j++) {
                for (int k=0; k<input[j].length; k++) {
                    flash(input, j, k, hasFlashed);
                }
            }

            // Step 3
            for (int j=0; j<input.length; j++) {
                for (int k=0; k<input[j].length; k++) {
                    if (hasFlashed[j][k]) {
                        thisStepFlashCount++;
                        input[j][k] = 0;
                    }
                }
            }

            flashCount = flashCount + thisStepFlashCount;
            if (thisStepFlashCount == 100) {
                return i + 1;
            }
        }
        
        System.out.println("Need more steps!");
        return flashCount;
    }

    public static void flash(int[][] input, int row, int col, boolean[][] hasFlashed) {
        if (!hasFlashed[row][col] && input[row][col] > 9) { 
            hasFlashed[row][col] = true;           
            boolean hasLeft = col > 0;
            boolean hasRight = col < 9;
            boolean hasUp = row > 0;
            boolean hasDown = row < 9;

            if (hasLeft && hasUp) {
                input[row-1][col-1] = input[row-1][col-1] + 1;
                flash(input, row-1, col-1, hasFlashed);
            }
            if (hasUp) {
                input[row-1][col] = input[row-1][col] + 1;
                flash(input, row-1, col, hasFlashed);
            }
            if (hasUp && hasRight) {
                input[row-1][col+1] = input[row-1][col+1] + 1;
                flash(input, row-1, col+1, hasFlashed);
            }
            if (hasLeft) {
                input[row][col-1] = input[row][col-1] + 1;
                flash(input, row, col-1, hasFlashed);
            }
            if (hasRight) {
                input[row][col+1] = input[row][col+1] + 1;
                flash(input, row, col+1, hasFlashed);
            }
            if (hasDown && hasLeft) {
                input[row+1][col-1] = input[row+1][col-1] + 1;
                flash(input, row+1, col-1, hasFlashed);
            }
            if (hasDown) {
                input[row+1][col] = input[row+1][col] + 1;
                flash(input, row+1, col, hasFlashed);
            }
            if (hasDown && hasRight) {
                input[row+1][col+1] = input[row+1][col+1] + 1;
                flash(input, row+1, col+1, hasFlashed);
            }
        }
    }

    public static int[][] readInputToIntMatrix10(String filename) {
        int[][] intArray = new int[10][10];

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                
                for (int j=0; j<data.length(); j++) {
                    intArray[i][j] = (Character.getNumericValue(data.charAt(j)));
                }
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found!");
            e.printStackTrace();
        }

        return intArray;
    }

}
