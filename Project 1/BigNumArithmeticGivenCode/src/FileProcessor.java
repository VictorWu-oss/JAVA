import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileProcessor {

    /**
     * Processes arithmetic expressions line-by-line in the given file.
     *
     * @param filePath Path to a file containing arithmetic expressions.
     */
    public static void processFile(String filePath) {
        File infile = new File(filePath);
        try (Scanner scan = new Scanner(infile)) {
            List<String> lines = new ArrayList<>();
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }

            boolean firstLinePrinted = false;

            for (String line : lines) {
                String[] parts = line.trim().split("\\s+");

                if (parts.length != 3) {
                    System.out.println("Incorrect expression for: " + line);
                    continue;
                }

                String number1 = parts[0];
                String operator = parts[1];
                String number2 = parts[2];

                LinkedList a = new LinkedList(number1);
                LinkedList result;

                switch (operator) {
                    case "+" -> {
                        LinkedList b = new LinkedList(number2);
                        result = LinkedList.add(a, b);
                    }
                    case "*" -> {
                        LinkedList b = new LinkedList(number2);
                        result = LinkedList.multiply(a, b);
                    }
                    case "^" -> {
                        int exponent = Integer.parseInt(number2);
                        if (exponent < 0) {
                            System.err.println("Exponent must be positive");
                            continue;
                        }
                        result = LinkedList.power(a, exponent);
                    }
                    default -> {
                        System.err.println("Unsupported operator: " + operator);
                        continue;
                    }
                }

                // Print newline BEFORE each line except the first
                if (firstLinePrinted) {
                    System.out.print("\n");
                }
                System.out.print(number1 + " " + operator + " " + number2 + " = " + result);
                firstLinePrinted = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + infile.getPath());
        }
    }
}
