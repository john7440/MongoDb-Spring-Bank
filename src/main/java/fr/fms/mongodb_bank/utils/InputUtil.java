package fr.fms.mongodb_bank.utils;

import java.util.Scanner;

public class InputUtil {
    private InputUtil() {
        /* This utility class should not be instantiated */
    }

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

    public static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            double value = readDouble(scanner, prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("The amount cannot be negative ! Please try again");
        }
    }

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
