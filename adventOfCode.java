import java.util.*;
import java.math.*;
import java.io.*;

public class adventOfCode {
    
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

    // public static Long getBracketScore(List<String> input) {
    //     int score = 0;
    //     Map<Character, Integer> scoreMap = new HashMap<>();
    //     scoreMap.put(')', 3);
    //     scoreMap.put(']', 57);
    //     scoreMap.put('}', 1197);
    //     scoreMap.put('>', 25137);

    //     Map<Character, Character> bracketMap = new HashMap<>();
    //     bracketMap.put('(', ')');
    //     bracketMap.put('[', ']');
    //     bracketMap.put('{', '}');
    //     bracketMap.put('<', '>');
    //     Set<Character> bracketSet = bracketMap.keySet();

    //     List<Stack<Character>> incompleteStack = new ArrayList<>();
        
    //     for (int i=0; i<input.size(); i++) {
    //         boolean isCorrupted = false;
    //         String thisLine = input.get(i);
    //         Stack<Character> bracketStack = new Stack();

    //         for (int j=0; j<thisLine.length(); j++) {
    //             char thisChar = thisLine.charAt(j);
    //             if (bracketSet.contains(thisChar)) {
    //                 bracketStack.push(thisChar);
    //             } else {
    //                 char checkBracket = bracketStack.pop();
    //                 if (bracketMap.get(checkBracket) != thisChar && !isCorrupted) {
    //                     isCorrupted = true;
    //                     score = score + scoreMap.get(thisChar);
    //                 }
    //             }
    //         }

    //         if (!bracketStack.isEmpty() && !isCorrupted) {
    //             incompleteStack.add(bracketStack);
    //         }
    //     }

    //     // return score; // - Part 1

    //     List<Long> finalScores = new ArrayList<>();
    //     scoreMap.clear();
    //     scoreMap.put(')', 1);
    //     scoreMap.put(']', 2);
    //     scoreMap.put('}', 3);
    //     scoreMap.put('>', 4);

    //     for (int i=0; i<incompleteStack.size(); i++) {
    //         Stack thisStack = incompleteStack.get(i);
    //         Long thisScore = 0L;
    //         while (!thisStack.isEmpty()) {
    //             thisScore = thisScore * 5;
    //             thisScore = thisScore + scoreMap.get(bracketMap.get(thisStack.pop()));
    //         }
    //         finalScores.add(thisScore);
    //         //System.out.println(thisScore);
    //     }

    //     Collections.sort(finalScores);

    //     return finalScores.get((int) finalScores.size()/2);
    // }

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

    public static List<List<Integer>> readInputToIntListMatrix(String filename) {
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
