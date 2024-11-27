public class Constants {
    // File paths
    public static final String EMPLOYEES_FILE = "employees.txt";

    // Date formats
    public static final String DATE_FORMAT = "dd/MM/yyyy - hh:mm:ss a";

    // Error messages
    public static final String INVALID_COMMAND = "Invalid command. Please try again.";
    public static final String ARGUMENT_ERROR = "Error: Please provide exactly one command-line argument.";
    public static final String USAGE_MESSAGE = """
        Usage:
          l  - List all employees
          s  - Show a random employee
         +<name> - Add a new employee
         ?<name> - Search for an employee
          c  - Count words and characters
         u<name> - Update an employee name
         d<name> - Delete an employee
        """;

    // Common messages
    public static final String DATA_LOADED = "Data Loaded.";
    public static final String LOADING_DATA = "Loading data ...";
}

