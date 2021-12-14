import java.util.*;
import java.math.*;
import java.io.*;

public class day10 {
    
    public static void main(String args[]) {
        // Again - not clean but I'm playing catchup so I'm just trying to make it work.
        List<String> input = readInputToStringList("day10Input.txt");
        System.out.println(getBracketScore(input));
    }

    public static Long getBracketScore(List<String> input) {
        int score = 0;
        Map<Character, Integer> scoreMap = new HashMap<>();
        scoreMap.put(')', 3);
        scoreMap.put(']', 57);
        scoreMap.put('}', 1197);
        scoreMap.put('>', 25137);

        Map<Character, Character> bracketMap = new HashMap<>();
        bracketMap.put('(', ')');
        bracketMap.put('[', ']');
        bracketMap.put('{', '}');
        bracketMap.put('<', '>');
        Set<Character> bracketSet = bracketMap.keySet();

        List<Stack<Character>> incompleteStack = new ArrayList<>();
        
        for (int i=0; i<input.size(); i++) {
            boolean isCorrupted = false;
            String thisLine = input.get(i);
            Stack<Character> bracketStack = new Stack();

            for (int j=0; j<thisLine.length(); j++) {
                char thisChar = thisLine.charAt(j);
                if (bracketSet.contains(thisChar)) {
                    bracketStack.push(thisChar);
                } else {
                    char checkBracket = bracketStack.pop();
                    if (bracketMap.get(checkBracket) != thisChar && !isCorrupted) {
                        isCorrupted = true;
                        score = score + scoreMap.get(thisChar);
                    }
                }
            }

            if (!bracketStack.isEmpty() && !isCorrupted) {
                incompleteStack.add(bracketStack);
            }
        }

        // return score; // - Part 1

        List<Long> finalScores = new ArrayList<>();
        scoreMap.clear();
        scoreMap.put(')', 1);
        scoreMap.put(']', 2);
        scoreMap.put('}', 3);
        scoreMap.put('>', 4);

        for (int i=0; i<incompleteStack.size(); i++) {
            Stack thisStack = incompleteStack.get(i);
            Long thisScore = 0L;
            while (!thisStack.isEmpty()) {
                thisScore = thisScore * 5;
                thisScore = thisScore + scoreMap.get(bracketMap.get(thisStack.pop()));
            }
            finalScores.add(thisScore);
            //System.out.println(thisScore);
        }

        Collections.sort(finalScores);

        return finalScores.get((int) finalScores.size()/2);
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
