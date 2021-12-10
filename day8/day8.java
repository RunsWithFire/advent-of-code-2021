import java.util.*;
import java.math.*;
import java.io.*;

public class day8 {
    
    public static void main(String args[]) {
        // This is horribly inefficient but I missed a few days and I'm playing catchup!
        List<String> inputSegments = new ArrayList<>();
        List<String> inputOutput = new ArrayList<>();
        List<String> fullSegments = new ArrayList<>();
        List<String> fullOutputs = new ArrayList<>();
        readDay8Input(inputSegments, inputOutput, fullSegments, fullOutputs);
        
        int count=0;
        for (String output : inputOutput) {
            if (output.length() == 2 || output.length() == 4 || output.length() == 3 || output.length() == 7) {
                count++;
            }
        }
        System.out.println("Part 1 - " + count);

        int score = 0;

        for (int i=0; i<fullSegments.size(); i++) { 
            String segments = fullSegments.get(i);
            String[] thisSegments = segments.split(" ");
            String outputs = fullOutputs.get(i);
            Map<String, String> segmentMap = new HashMap<>();
            Map<String, String> numberMap = new HashMap<>();

            for (int j=0; j<thisSegments.length; j++) { 
                String sorted = thisSegments[j].chars()
                    .sorted()
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
                thisSegments[j] = sorted;
            }

            // Get 1, 4, 7, and 8
            for (String segment : thisSegments) {
                if (segment.length() == 2) {
                    segmentMap.put(segment, "1");
                    numberMap.put("1", segment);
                }
                if (segment.length() == 4) {
                    segmentMap.put(segment, "4");
                    numberMap.put("4", segment);
                }
                if (segment.length() == 3) {
                    segmentMap.put(segment, "7");
                    numberMap.put("7", segment);
                }
                if (segment.length() == 7) {
                    segmentMap.put(segment, "8");
                    numberMap.put("8", segment);
                }
            }

            // Get 3 and 6
            for (String segment : thisSegments) {
                if (segment.length() == 5 && stringContains(segment, numberMap.get("1"))) {
                    segmentMap.put(segment, "3");
                    numberMap.put("3", segment);
                }

                if (segment.length() == 6 && !stringContains(segment, numberMap.get("1"))) {
                    segmentMap.put(segment, "6");
                    numberMap.put("6", segment);
                }
            }

            // Get 9
            for (String segment : thisSegments) {
                if (segment.length() == 6 && stringContains(segment, numberMap.get("3"))) {
                    segmentMap.put(segment, "9");
                    numberMap.put("9", segment);
                }
            }

            // Get 0
            for (String segment : thisSegments) {
                if (segment.length() == 6 && stringContains(segment, numberMap.get("1")) && !stringContains(segment, numberMap.get("9"))) {
                    segmentMap.put(segment, "0");
                    numberMap.put("0", segment);
                }
            }

            // Get 5
            for (String segment : thisSegments) {
                if (segment.length() == 5 && !stringContains(segment, numberMap.get("1"))) {
                    String nine = numberMap.get("9");

                    for (char ch : segment.toCharArray()) {
                        nine = nine.replaceAll(String.valueOf(ch), "");
                    }

                    if (nine.length() == 1) {
                        segmentMap.put(segment, "5");
                        numberMap.put("5", segment);
                    }
                }
            }

            // Get 2
            for (String segment : thisSegments) {
                if (segment.length() == 5 && !stringContains(segment, numberMap.get("3")) && !stringContains(segment, numberMap.get("5"))) {
                    segmentMap.put(segment, "2");
                    numberMap.put("2", segment);
                }
            }

            StringBuilder finalString = new StringBuilder();
            String[] outputsSplit = outputs.split(" ");

            for (int j=0; j<outputsSplit.length; j++) { 
                String sorted = outputsSplit[j].chars()
                    .sorted()
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
                outputsSplit[j] = sorted;
            }

            for (int j=0; j<outputsSplit.length; j++) {
                String output = outputsSplit[j];
                finalString.append(segmentMap.get(output));
            }
            score = score + Integer.valueOf(finalString.toString());
        }
        System.out.println("Part 2 - " + score);
    }

    public static boolean stringContains(String longer, String shorter) {
        int i = 0;
        for (char c : shorter.toCharArray()) {
            i = longer.indexOf(c, i) + 1;
            if (i <= 0) { return false; }
        }
        return true;
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

}
