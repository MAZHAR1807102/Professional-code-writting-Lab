

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StudentList {
    public static void main(String[] args) {
        // Check if any arguments are passed
        if (args.length == 0) {
            System.out.println("No arguments provided.");
            return;
        }

        // The rest of the code remains the same
        if (args[0].equals("a")) {
            System.out.println("Loading data ...");
            try {
                BufferedReader s = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream("students.txt")
                    )
                );
                String r = s.readLine();
                String[] i = r.split(",");
                for (String j : i) {
                    System.out.println(j.trim());
                }
                System.out.println("Data Loaded.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage()); // Enhanced error message
            }

        } else if (args[0].equals("r")) {
            System.out.println("Loading data ...");
            try {
                BufferedReader g = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream("students.txt")
                    )
                );
                String r = g.readLine();
                //System.out.println(r);
                String[] i = r.split(",");
                Random x = new Random();
                int y = x.nextInt(i.length);
                System.out.println(i[y].trim());
            System.out.println("Data Loaded.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

        } else if (args[0].contains("+")) {
            System.out.println("Loading data ...");
            try {
                BufferedWriter s = new BufferedWriter(
                    new FileWriter("students.txt", true)
                );
                String t = args[0].substring(1);
                Date d = new Date();
                String df = "dd/mm/yyyy-hh:mm:ss a";
                DateFormat dateFormat = new SimpleDateFormat(df);
                String fd = dateFormat.format(d);
                s.write(", " + t + "\nList last updated on " + fd);
                s.close();
            System.out.println("Data Loaded.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } else if (args[0].contains("?")) {
            System.out.println("Loading data ...");
            try {
                BufferedReader g = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream("students.txt")
                    )
                );
                String r = g.readLine();
                String[] i = r.split(",");
                boolean done = false;
                String t = args[0].substring(1);
                for (int idx = 0; idx < i.length && !done; idx++) {
                    if (i[idx].equals(t)) {
                        System.out.println("We found it!");
                        done = true;
                    }
                }
            System.out.println("Data Loaded.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } else if (args[0].contains("c")) {
            System.out.println("Loading data ...");
            try {
                BufferedReader s = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream("students.txt")
                    )
                );
                String D = s.readLine();
                char[] a = D.toCharArray();
                boolean in_word = false;
                int count = 0;
                for (char c : a) {
                    if (c == ' ' || c == ',') {
                        if (!in_word) {
                            count++;
                            in_word = true;
                        }
                    } else {
                        in_word = false;
                    }
                }
                System.out.println(count + " word(s) found ");
            System.out.println("Data Loaded.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
           }
        }
    }
}

