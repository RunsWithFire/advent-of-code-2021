import java.util.*;
import java.math.*;
import java.io.*;

public class day5 {
    
    public static void main(String args[]) {
        List<String> input = readInputToStringList("day5Input.txt");
        System.out.println(countDangerousZones(input));
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
