/**
 * Fixed values used by EmployeeManager: file name, commands and messages.
 */
public final class Constants {
    // Data file
    public static final String EMPLOYEES_FILE = "employees.txt";
    public static final String SEPARATOR = ",";

    // Commands (the first character of the argument)
    public static final String LIST = "l";
    public static final String SHOW_RANDOM = "s";
    public static final String ADD = "+";
    public static final String SEARCH = "?";
    public static final String COUNT = "c";
    public static final String UPDATE = "u";
    public static final String DELETE = "d";

    // Messages
    public static final String LOADING_DATA = "Loading data ...";
    public static final String DATA_LOADED = "Data Loaded.";
    public static final String DATA_UPDATED = "Data Updated.";
    public static final String DATA_DELETED = "Data Deleted.";
    public static final String EMPLOYEE_FOUND = "Employee found!";
    public static final String UPDATED_NAME = "Updated";
    public static final String FILE_ERROR = "Error: could not access " + EMPLOYEES_FILE + ": ";
    public static final String USAGE = """
            Usage: java EmployeeManager <command>
              l        List all employees
              s        Show a random employee
              +<name>  Add an employee
              ?<name>  Search for an employee
              c        Count words and characters
              u<name>  Update an employee
              d<name>  Delete an employee""";

    // No objects of this class are ever needed
    private Constants() {
    }
}
