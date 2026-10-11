import java.util.Scanner;

/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Robust input utility class to handle CLI inputs gracefully.
 * Guarantees that improper inputs (type mismatches, empty values, out-of-range numbers)
 * will never crash the application.
 */
public class InputHelper {

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reads a non-empty string line from the user.
     */
    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("  [Error] Input cannot be blank. Please try again.");
        }
    }

    /**
     * Reads an integer within a specified range [min, max].
     */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  [Error] Value must be between %d and %d. Please try again.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [Error] Invalid input. Please enter a valid whole number.");
            }
        }
    }

    /**
     * Reads a positive integer (greater than zero).
     */
    public int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("  [Error] Value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("  [Error] Invalid input. Please enter a valid positive integer.");
            }
        }
    }

    /**
     * Reads a double value within a specified range [min, max].
     */
    public double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  [Error] Value must be between %,.2f and %,.2f UGX.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [Error] Invalid input. Please enter a valid decimal or numeric amount.");
            }
        }
    }

    /**
     * Reads a yes/no confirmation from the user.
     */
    public boolean readConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("  [Error] Please answer 'y' for yes or 'n' for no.");
        }
    }
}
