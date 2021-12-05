import java.util.*;
import java.math.*;
import java.io.*;

public class day4 {
    
    public static void main(String args[]) {
        List<Integer> numCalls = new ArrayList<>();
        List<Board> boards = new ArrayList<>();
        boolean didWin = false;
        int numBoards = buildBingo(numCalls, boards);

        for (int i=0; i<numCalls.size(); i++) {
            int thisNum = numCalls.get(i);
            for (Board board : boards) {
                for (int j=0; j<board.nums.length; j++) {
                    for (int k=0; k<board.nums[j].length; k++) {
                        if (board.nums[j][k] == thisNum) {
                            board.marks[j][k] = true;

                            //check for win
                            if (i > 4 && board.didWin == false) {
                                didWin = checkWin(board, j, k);
                                if (didWin) {
                                    board.didWin = true;
                                    numBoards--;
                                    if (numBoards == 0) {
                                        System.out.println(getBoardScore(board, thisNum));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

}
