import java.util.*;
import java.math.*;
import java.io.*;

public class day9 {
    
    public static void main(String args[]) {
        // Again - not clean but I'm playing catchup so I'm just trying to make it work.
        List<List<Integer>> input = readInputToIntMatrix("day9Input.txt");
        int score = 0;
        List<Integer> rowCoord = new ArrayList<>();
        List<Integer> colCoord = new ArrayList<>();

        for (int i=0; i<input.size(); i++) {
            for (int j=0; j<input.get(i).size(); j++) {
                int thisVal = input.get(i).get(j);
                boolean checkUp = false;
                boolean checkDown = false;
                boolean checkLeft = false;
                boolean checkRight = false;

                // CheckUp
                if (i == 0) {
                    checkUp = true;
                } else if (thisVal < input.get(i-1).get(j)) {
                    checkUp = true;
                } else {
                    checkUp = false;
                }

                // CheckDown
                if (i == input.size()-1) {
                    checkDown = true;
                } else if (thisVal < input.get(i+1).get(j)) {
                    checkDown = true;
                } else {
                    checkDown = false;
                }

                // CheckLeft
                if (j == 0) {
                    checkLeft = true;
                } else if (thisVal < input.get(i).get(j-1)) {
                    checkLeft = true;
                } else {
                    checkLeft = false;
                }

                // CheckRight
                if (j == input.get(i).size()-1) {
                    checkRight = true;
                } else if (thisVal < input.get(i).get(j+1)) {
                    checkRight = true;
                } else {
                    checkRight = false;
                }

                if (checkUp && checkDown && checkLeft && checkRight) {
                    rowCoord.add(i);
                    colCoord.add(j);
                    score = score + (thisVal + 1);
                }
            }
        }
        System.out.println("Part 1 - " + score);

        boolean[][] visited = new boolean[input.size()][input.get(0).size()];
        int top1 = 1;
        int top2 = 1;
        int top3 = 1;

        for (int i=0; i<rowCoord.size(); i++) {
            int size = 0;
            size = dfs(input, visited, rowCoord.get(i), colCoord.get(i), size);
            
            if (size >= top1) {
                top3 = top2;
                top2 = top1;
                top1= size;
            } else if (size >= top2) {
                top3 = top2;
                top2 = size;
            } else if (size >= top3) {
                top3 = size;
            }
            
        }
        System.out.println("Part 2 - " + top1 * top2 * top3);
    }

    public static int dfs(List<List<Integer>> input, boolean[][] visited, int row, int col, int size) {
        visited[row][col] = true;
        size++;
        int thisVal = input.get(row).get(col);

        // CheckUp
        if (row != 0 && thisVal < input.get(row-1).get(col) && input.get(row-1).get(col) != 9 && !visited[row-1][col]) {
            size = dfs(input, visited, row-1, col, size);
        }

        // CheckDown
        if (row != input.size()-1 && thisVal < input.get(row+1).get(col) && input.get(row+1).get(col) != 9 && !visited[row+1][col]) {
            size = dfs(input, visited, row+1, col, size);
        }

        // CheckLeft
        if (col != 0 && thisVal < input.get(row).get(col-1) && input.get(row).get(col-1) != 9 && !visited[row][col-1]) {
            size = dfs(input, visited, row, col-1, size);
        }

        // CheckRight
        if (col != input.get(row).size()-1 && thisVal < input.get(row).get(col+1) && input.get(row).get(col+1) != 9 && !visited[row][col+1]) {
            size = dfs(input, visited, row, col+1, size);
        }

        return size;
    }

    public static List<List<Integer>> readInputToIntMatrix(String filename) {
        List<List<Integer>> finalList = new ArrayList<>();

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                List<Integer> rowList = new ArrayList<>();
                
                for (int i=0; i<data.length(); i++) {
                    rowList.add(Character.getNumericValue(data.charAt(i)));
                }
                finalList.add(rowList);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found!");
            e.printStackTrace();
        }

        return finalList;
    }

}
