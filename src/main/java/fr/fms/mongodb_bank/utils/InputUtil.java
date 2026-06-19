package fr.fms.mongodb_bank.utils;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputUtil {
    private InputUtil() {
        /* This utility class should not be instantiated */
    }

    /**
     * Prompts the user to enter a numeric value and reads it as a {@code double}
     *
     * <p>If the input cannot be parsed as a valid number, an error message is displayed
     * and the user is prompted again until a valid value is entered</p>
     *
     * @param scanner the {@link Scanner}
     * @param prompt  the message displayed before input
     * @return a valid {@code double} value entered by the user
     */
    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number !");
            }
        }
    }

    /**
     * Prompts the user to enter a non-negative numeric value and reads it as a {@code double}
     *
     * <p>Internally calls {@link #readDouble(Scanner, String)} and rejects any value
     * strictly less than zero, prompting the user again until a valid value is entered</p>
     *
     * @param scanner the {@link Scanner}
     * @param prompt  the message displayed before input
     * @return a non-negative {@code double} value entered by the user
     */
    public static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            double value = readDouble(scanner, prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("The amount cannot be negative ! Please try again");
        }
    }

    /**
     * Prompts the user to enter a value for a required field and validates it
     * against a given rule
     *
     * <p>The user is repeatedly prompted until the entered value satisfies
     * the provided {@link Predicate}. Leading and trailing whitespace is trimmed
     * before validation</p>
     *
     * @param scanner the {@link Scanner}
     * @param fieldName the name of the field displayed as the input prompt label
     * @param rule a {@link Predicate} defining the validation rule the input must satisfy
     * @param errorMessage the message displayed when validation fails
     * @return the trimmed input string that satisfies the validation rule
     */
     public static String promptUntilValid(Scanner scanner, String fieldName,
                                           java.util.function.Predicate<String> rule, String errorMessage) {
        while (true) {
            System.out.print(fieldName + ": ");
            String input = scanner.nextLine().trim();
            if (rule.test(input)) {
                return input;
            }
            System.out.println(errorMessage + "! Please try again !");
        }
    }

    /**
     * Prompts the user to optionally update a field's current value
     *
     * <p>If the user presses Enter without typing anything, the {@code currentValue}
     * is returned unchanged. Otherwise, the new input is validated against the provided
     * {@link Predicate} and the user is re-prompted until a valid value is entered</p>
     *
     * <p>This method is intended for update/edit forms where fields are optional
     * and the existing value should be preserved if no new value is given</p>
     *
     * @param scanner the {@link Scanner}
     * @param fieldName the name of the field displayed in the prompt
     * @param currentValue the current value of the field, displayed as a hint to the user
     * @param rule a {@link Predicate} defining the validation rule for a new value
     * @param errorMessage the message displayed when the new value fails validation
     * @return the new trimmed input if valid, or {@code currentValue} if the input was empty
     */
    public static String promptOptional(Scanner scanner, String fieldName, String currentValue,
                                  java.util.function.Predicate<String> rule, String errorMessage) {
        while (true) {
            System.out.printf("New %s (%s): ", fieldName, currentValue);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return currentValue;
            if (rule.test(input)) return input;
            System.out.println(errorMessage + "! Please try again");
        }
    }
}
