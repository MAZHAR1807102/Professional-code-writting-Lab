import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        List<String> employees = loadEmployees();
        if (employees.isEmpty()) {
            System.out.println(Constants.NO_EMPLOYEES);
        }
        for (String employee : employees) {
            System.out.println(employee);
        }
        System.out.println(Constants.DATA_LOADED);
    }

    /** Prints one employee chosen at random. */
    private static void showRandomEmployee() throws IOException {
        System.out.println(Constants.LOADING_DATA);
        List<String> employees = loadEmployees();
        if (employees.isEmpty()) {
            System.out.println(Constants.NO_EMPLOYEES);
        } else {
            int randomIndex = new Random().nextInt(employees.size());
            System.out.println(employees.get(randomIndex));
        }
        System.out.println(Constants.DATA_LOADED);
    }

    /** Adds a name to the end of the list. */
    private static void addEmployee(String newName) throws IOException {
        System.out.println(Constants.LOADING_DATA);
        List<String> employees = loadEmployees();
        employees.add(newName.trim());
        saveEmployees(employees);
        System.out.println(Constants.DATA_LOADED);
    }

    /** Says whether the name is in the list. */
    private static void searchEmployee(String searchName) throws IOException {
        System.out.println(Constants.LOADING_DATA);
        if (loadEmployees().contains(searchName)) {
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
        List<String> employees = loadEmployees();
        employees.replaceAll(name -> name.equals(nameToUpdate) ? Constants.UPDATED_NAME : name);
        saveEmployees(employees);
        System.out.println(Constants.DATA_UPDATED);
    }

    /** Removes the first match of the name from the list. */
    private static void deleteEmployee(String nameToDelete) throws IOException {
        System.out.println(Constants.LOADING_DATA);
        List<String> employees = loadEmployees();
        employees.remove(nameToDelete);
        saveEmployees(employees);
        System.out.println(Constants.DATA_DELETED);
    }

    /** Reads the line of employee names, or "" if the file is missing or empty. */
    private static String readEmployeeLine() throws IOException {
        if (!new File(Constants.EMPLOYEES_FILE).exists()) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(Constants.EMPLOYEES_FILE)))) {
            String line = reader.readLine();
            return line == null ? "" : line;
        }
    }

    /** Returns the trimmed, non-empty names from the file as a list that can be changed. */
    private static List<String> loadEmployees() throws IOException {
        List<String> employees = new ArrayList<>();
        for (String name : readEmployeeLine().split(Constants.SEPARATOR)) {
            if (!name.isBlank()) {
                employees.add(name.trim());
            }
        }
        return employees;
    }

    /** Replaces the file contents with the given names, separated by commas. */
    private static void saveEmployees(List<String> employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.EMPLOYEES_FILE))) {
            writer.write(String.join(Constants.SEPARATOR, employees));
        }
    }

    /** True if the argument starts with the prefix and has a name after it, like "+Mary". */
    private static boolean isNameCommand(String argument, String prefix) {
        return argument.startsWith(prefix) && argument.length() > prefix.length();
    }
}
