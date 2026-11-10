import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Manages a comma-separated list of employee names stored in employees.txt.
 * Run with one argument: l, s, +name, ?name, c, uname or dname.
 */
public class EmployeeManager {
    public static void main(String[] args) {
        // Stop early unless exactly one argument was given
        if (args.length != 1) {
            System.out.println("Usage: java EmployeeManager <command>");
            System.out.println("  l        List all employees");
            System.out.println("  s        Show a random employee");
            System.out.println("  +<name>  Add an employee");
            System.out.println("  ?<name>  Search for an employee");
            System.out.println("  c        Count words and characters");
            System.out.println("  u<name>  Update an employee");
            System.out.println("  d<name>  Delete an employee");
            return;
        }

        // Run the command given as the first argument
        if (args[0].equals("l")) {
            System.out.println("Loading data ...");
            try {
                String[] employees = readEmployeeLine().split(",");
                for (String employee : employees) {
                    System.out.println(employee);
                }
            } catch (Exception e) {
                // Errors are ignored for now (fixed in task 6)
            }
            System.out.println("Data Loaded.");
        } else if (args[0].equals("s")) {
            System.out.println("Loading data ...");
            try {
                String line = readEmployeeLine();
                System.out.println(line);
                String[] employees = line.split(",");
                Random random = new Random();
                int randomIndex = random.nextInt(employees.length);
                System.out.println(employees[randomIndex]);
            } catch (Exception e) {
                // Errors are ignored for now (fixed in task 6)
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("+")) {
            System.out.println("Loading data ...");
            try {
                String newName = args[0].substring(1);
                writeEmployeeLine(", " + newName, true);
            } catch (Exception e) {
                // Errors are ignored for now (fixed in task 6)
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("?")) {
            System.out.println("Loading data ...");
            try {
                String[] employees = readEmployeeLine().split(",");
                boolean found = false;
                String searchName = args[0].substring(1);
                for (int i = 0; i < employees.length && !found; i++) {
                    if (employees[i].equals(searchName)) {
                        System.out.println("Employee found!");
                        found = true;
                    }
                }
            } catch (Exception e) {
                // Errors are ignored for now (fixed in task 6)
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("c")) {
            System.out.println("Loading data ...");
            try {
                char[] chars = readEmployeeLine().toCharArray();
                boolean inWord = false;
                int wordCount = 0;
                for (char c : chars) {
                    if (c == ' ') {
                        if (!inWord) {
                            wordCount++;
                            inWord = true;
                        } else {
                            inWord = false;
                        }
                    }
                }
                System.out.println(wordCount + " word(s) found " + chars.length);
            } catch (Exception e) {
                // Errors are ignored for now (fixed in task 6)
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("u")) {
            System.out.println("Loading data ...");
            try {
                String[] employees = readEmployeeLine().split(",");
                String nameToUpdate = args[0].substring(1);
                for (int i = 0; i < employees.length; i++) {
                    if (employees[i].equals(nameToUpdate)) {
                        employees[i] = "Updated";
                    }
                }
                writeEmployeeLine(String.join(",", employees), false);
            } catch (Exception e) {
                // Errors are ignored for now (fixed in task 6)
            }
            System.out.println("Data Updated.");
        } else if (args[0].contains("d")) {
            System.out.println("Loading data ...");
            try {
                String[] employees = readEmployeeLine().split(",");
                String nameToDelete = args[0].substring(1);
                List<String> remainingEmployees = new ArrayList<>(Arrays.asList(employees));
                remainingEmployees.remove(nameToDelete);
                writeEmployeeLine(String.join(",", remainingEmployees), false);
            } catch (Exception e) {
                // Errors are ignored for now (fixed in task 6)
            }
            System.out.println("Data Deleted.");
        }
    }

    /** Reads the single line of employee names from the file. */
    private static String readEmployeeLine() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("employees.txt")))) {
            return reader.readLine();
        }
    }

    /** Writes text to the file, either appending or replacing its contents. */
    private static void writeEmployeeLine(String text, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", append))) {
            writer.write(text);
        }
    }
}
