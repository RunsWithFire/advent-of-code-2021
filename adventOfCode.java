import java.util.*;
import java.math.*;
import java.io.*;

public class adventOfCode {
    
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

    public static boolean stringContains(String longer, String shorter) {
        int i = 0;
        for (char c : shorter.toCharArray()) {
            i = longer.indexOf(c, i) + 1;
            if (i <= 0) { return false; }
        }
        return true;
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

    public static int countDangerousZones(List<String> input) {
        int[][] grid = new int[1000][1000];
        int xStart;
        int yStart;
        int xEnd;
        int yEnd;
        int count = 0;

        for (String item : input) {
            String[] coordinates = item.split(" -> ");
            String[] startCoords = coordinates[0].split(",");
            xStart = Integer.valueOf(startCoords[0]);
            yStart = Integer.valueOf(startCoords[1]);
            String[] endCoords = coordinates[1].split(",");
            xEnd = Integer.valueOf(endCoords[0]);
            yEnd = Integer.valueOf(endCoords[1]);

            if (xStart == xEnd) {
                if (yEnd > yStart) {
                    for (int i=yStart; i<=yEnd; i++) {
                        grid[xStart][i] = grid[xStart][i] + 1;
                    }
                } else {
                    for (int i=yEnd; i<=yStart; i++) {
                        grid[xStart][i] = grid[xStart][i] + 1;
                    }
                }
            } else if (yStart == yEnd) {
                if (xEnd > xStart) {
                    for (int i=xStart; i<=xEnd; i++) {
                        grid[i][yStart] = grid[i][yStart] + 1;
                    }
                } else {
                    for (int i=xEnd; i<=xStart; i++) {
                        grid[i][yStart] = grid[i][yStart] + 1;
                    }
                }
            } else {
                while (xStart != xEnd) {
                    grid[xStart][yStart] = grid[xStart][yStart] + 1;

                    if (xStart > xEnd) {
                        xStart--;
                    } else {
                        xStart++;
                    }

                    if (yStart  > yEnd)  {
                        yStart--;
                    } else {
                        yStart++;
                    }
                }
                grid[xStart][yStart] = grid[xStart][yStart] + 1;
            }
        }

        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] > 1) {
                    count++;
                }
            }
        }

        return count;
    }

    public static int getBoardScore(Board board, int thisNum) {
        int score = 0;
        for (int i=0; i<board.nums.length; i++) {
            for (int j=0; j<board.nums[i].length; j++) {
                if (board.marks[i][j] == false) {
                    score = score + board.nums[i][j];
                }
            }
        }
        score = score * thisNum;
        return score;
    }

    public static boolean checkWin(Board board, int j, int k) {
        if (board.marks[j][0] == true
         && board.marks[j][1] == true
         && board.marks[j][2] == true
         && board.marks[j][3] == true
         && board.marks[j][4] == true) {
             return true;
        } else if ( board.marks[0][k] == true
         && board.marks[1][k] == true
         && board.marks[2][k] == true
         && board.marks[3][k] == true
         && board.marks[4][k] == true) {
            return true;
        }

        return false;
    }

    public static int buildBingo(List<Integer> numCalls, List<Board> boards) {
        int numBoards = 0;
        try {
            File myObj = new File("day4Input.txt");
            Scanner myReader = new Scanner(myObj);

            String callString = myReader.nextLine();
            String[] splitArray = callString.split(",");
            for(int i=0; i<splitArray.length; i++) {
                numCalls.add(Integer.valueOf(splitArray[i]));
            }
            myReader.nextLine();

            while (myReader.hasNextLine()) {
                Board newBoard = new Board();
                numBoards++;

                for (int i=0; i<5; i++) {
                    String data = myReader.nextLine();
                    String[] splitData = data.trim().split("\\s+");
                    int[] boardRow = new int[splitData.length];
                    for (int j=0; j<splitData.length; j++) {
                        boardRow[j] = Integer.valueOf(splitData[j]);
                    }                    
                    newBoard.nums[i] = boardRow;
                }
                boards.add(newBoard);
                if (myReader.hasNextLine()) {
                    myReader.nextLine();
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("day4Input.txt not found!");
            e.printStackTrace();
        }
        return numBoards;
    }

    static class Board {
        int[][] nums = new int[5][5];
        boolean[][] marks = new boolean[5][5];
        boolean didWin = false;
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

    public static void readDay8Input(List<String> inputSegments, List<String> inputOutput, List<String> fullSegments, List<String> fullOutputs) {
        try {
            File myObj = new File("day8Input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] input = data.split(" \\| ");
                fullSegments.add(input[0]);
                fullOutputs.add(input[1]);
                String[] segments = input[0].split(" ");
                String[] outputs = input[1].split(" ");
                for (String segment : segments) {
                    inputSegments.add(segment);
                }
                for (String output : outputs) {
                    inputOutput.add(output);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("day8Input.txt not found!");
            e.printStackTrace();
        }

        return;
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
