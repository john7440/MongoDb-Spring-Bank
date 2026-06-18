package fr.fms.mongodb_bank.utils;

import java.util.Scanner;

public class InputUtil {

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
}
