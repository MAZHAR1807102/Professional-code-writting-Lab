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
            System.out.println(Constants.USAGE);
            return;
        }

        // Run the command given as the first argument
        if (args[0].equals(Constants.LIST)) {
            System.out.println(Constants.LOADING_DATA);
            try {
                String[] employees = readEmployeeLine().split(Constants.SEPARATOR);
                for (String employee : employees) {
                    System.out.println(employee);
                }
                System.out.println(Constants.DATA_LOADED);
            } catch (IOException e) {
                System.err.println(Constants.FILE_ERROR + e.getMessage());
            }
        } else if (args[0].equals(Constants.SHOW_RANDOM)) {
            System.out.println(Constants.LOADING_DATA);
            try {
                String line = readEmployeeLine();
                System.out.println(line);
                String[] employees = line.split(Constants.SEPARATOR);
                Random random = new Random();
                int randomIndex = random.nextInt(employees.length);
                System.out.println(employees[randomIndex]);
                System.out.println(Constants.DATA_LOADED);
            } catch (IOException e) {
                System.err.println(Constants.FILE_ERROR + e.getMessage());
            }
        } else if (args[0].contains(Constants.ADD)) {
            System.out.println(Constants.LOADING_DATA);
            try {
                String newName = args[0].substring(1);
                writeEmployeeLine(Constants.SEPARATOR + " " + newName, true);
                System.out.println(Constants.DATA_LOADED);
            } catch (IOException e) {
                System.err.println(Constants.FILE_ERROR + e.getMessage());
            }
        } else if (args[0].contains(Constants.SEARCH)) {
            System.out.println(Constants.LOADING_DATA);
            try {
                String[] employees = readEmployeeLine().split(Constants.SEPARATOR);
                boolean found = false;
                String searchName = args[0].substring(1);
                for (int i = 0; i < employees.length && !found; i++) {
                    if (employees[i].equals(searchName)) {
                        System.out.println(Constants.EMPLOYEE_FOUND);
                        found = true;
                    }
                }
                System.out.println(Constants.DATA_LOADED);
            } catch (IOException e) {
                System.err.println(Constants.FILE_ERROR + e.getMessage());
            }
        } else if (args[0].contains(Constants.COUNT)) {
            System.out.println(Constants.LOADING_DATA);
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
                System.out.println(Constants.DATA_LOADED);
            } catch (IOException e) {
                System.err.println(Constants.FILE_ERROR + e.getMessage());
            }
        } else if (args[0].contains(Constants.UPDATE)) {
            System.out.println(Constants.LOADING_DATA);
            try {
                String[] employees = readEmployeeLine().split(Constants.SEPARATOR);
                String nameToUpdate = args[0].substring(1);
                for (int i = 0; i < employees.length; i++) {
                    if (employees[i].equals(nameToUpdate)) {
                        employees[i] = Constants.UPDATED_NAME;
                    }
                }
                writeEmployeeLine(String.join(Constants.SEPARATOR, employees), false);
                System.out.println(Constants.DATA_UPDATED);
            } catch (IOException e) {
                System.err.println(Constants.FILE_ERROR + e.getMessage());
            }
        } else if (args[0].contains(Constants.DELETE)) {
            System.out.println(Constants.LOADING_DATA);
            try {
                String[] employees = readEmployeeLine().split(Constants.SEPARATOR);
                String nameToDelete = args[0].substring(1);
                List<String> remainingEmployees = new ArrayList<>(Arrays.asList(employees));
                remainingEmployees.remove(nameToDelete);
                writeEmployeeLine(String.join(Constants.SEPARATOR, remainingEmployees), false);
                System.out.println(Constants.DATA_DELETED);
            } catch (IOException e) {
                System.err.println(Constants.FILE_ERROR + e.getMessage());
            }
        }
    }

    /** Reads the single line of employee names from the file. */
    private static String readEmployeeLine() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(Constants.EMPLOYEES_FILE)))) {
            return reader.readLine();
        }
    }

    /** Writes text to the file, either appending or replacing its contents. */
    private static void writeEmployeeLine(String text, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.EMPLOYEES_FILE, append))) {
            writer.write(text);
        }
    }
}
