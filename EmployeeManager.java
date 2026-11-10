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
        String argument = args[0];
        try {
            if (argument.equals(Constants.LIST)) {
                listEmployees();
            } else if (argument.equals(Constants.SHOW_RANDOM)) {
                showRandomEmployee();
            } else if (isNameCommand(argument, Constants.ADD)) {
                addEmployee(argument.substring(1));
            } else if (isNameCommand(argument, Constants.SEARCH)) {
                searchEmployee(argument.substring(1));
            } else if (argument.equals(Constants.COUNT)) {
                countWords();
            } else if (isNameCommand(argument, Constants.UPDATE)) {
                updateEmployee(argument.substring(1));
            } else if (isNameCommand(argument, Constants.DELETE)) {
                deleteEmployee(argument.substring(1));
            } else {
                System.out.println(Constants.INVALID_COMMAND + argument);
                System.out.println(Constants.USAGE);
            }
        } catch (IOException e) {
            System.err.println(Constants.FILE_ERROR + e.getMessage());
        }
    }

    /** Prints every employee, one per line. */
    private static void listEmployees() throws IOException {
        System.out.println(Constants.LOADING_DATA);
        for (String employee : splitNames(readEmployeeLine())) {
            System.out.println(employee);
        }
        System.out.println(Constants.DATA_LOADED);
    }

    /** Prints one employee chosen at random. */
    private static void showRandomEmployee() throws IOException {
        System.out.println(Constants.LOADING_DATA);
        String[] employees = splitNames(readEmployeeLine());
        int randomIndex = new Random().nextInt(employees.length);
        System.out.println(employees[randomIndex]);
        System.out.println(Constants.DATA_LOADED);
    }

    /** Adds a name to the end of the list. */
    private static void addEmployee(String newName) throws IOException {
        System.out.println(Constants.LOADING_DATA);
        writeEmployeeLine(Constants.SEPARATOR + newName.trim(), true);
        System.out.println(Constants.DATA_LOADED);
    }

    /** Says whether the name is in the list. */
    private static void searchEmployee(String searchName) throws IOException {
        System.out.println(Constants.LOADING_DATA);
        String[] employees = splitNames(readEmployeeLine());
        if (Arrays.asList(employees).contains(searchName)) {
            System.out.println(Constants.EMPLOYEE_FOUND);
        } else {
            System.out.println(Constants.EMPLOYEE_NOT_FOUND);
        }
        System.out.println(Constants.DATA_LOADED);
    }

    /** Prints how many words and characters the file contains. */
    private static void countWords() throws IOException {
        System.out.println(Constants.LOADING_DATA);
        String line = readEmployeeLine();
        int wordCount = 0;
        for (String word : line.split(Constants.WORD_BREAKS)) {
            if (!word.isEmpty()) {
                wordCount++;
            }
        }
        System.out.println(wordCount + Constants.WORDS_FOUND
                + line.length() + Constants.CHARACTERS_FOUND);
        System.out.println(Constants.DATA_LOADED);
    }

    /** Replaces every match of the name with the text "Updated". */
    private static void updateEmployee(String nameToUpdate) throws IOException {
        System.out.println(Constants.LOADING_DATA);
        String[] employees = splitNames(readEmployeeLine());
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].equals(nameToUpdate)) {
                employees[i] = Constants.UPDATED_NAME;
            }
        }
        writeEmployeeLine(String.join(Constants.SEPARATOR, employees), false);
        System.out.println(Constants.DATA_UPDATED);
    }

    /** Removes the first match of the name from the list. */
    private static void deleteEmployee(String nameToDelete) throws IOException {
        System.out.println(Constants.LOADING_DATA);
        List<String> remainingEmployees = new ArrayList<>(Arrays.asList(splitNames(readEmployeeLine())));
        remainingEmployees.remove(nameToDelete);
        writeEmployeeLine(String.join(Constants.SEPARATOR, remainingEmployees), false);
        System.out.println(Constants.DATA_DELETED);
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

    /** Splits the line into names and removes the spaces around each one. */
    private static String[] splitNames(String line) {
        String[] names = line.split(Constants.SEPARATOR);
        for (int i = 0; i < names.length; i++) {
            names[i] = names[i].trim();
        }
        return names;
    }

    /** True if the argument starts with the prefix and has a name after it, like "+Mary". */
    private static boolean isNameCommand(String argument, String prefix) {
        return argument.startsWith(prefix) && argument.length() > prefix.length();
    }
}
