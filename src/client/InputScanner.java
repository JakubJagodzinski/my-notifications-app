package client;

import common.DatetimeParser;

import java.util.Scanner;

public class InputScanner {

    public static Scanner scanner = new Scanner(System.in);

    public static int getOperationNumber() {
        System.out.print("> ");
        String operationNumber = scanner.nextLine().trim();
        System.out.println();
        try {
            return Integer.parseInt(operationNumber);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getNotificationContent() {
        System.out.println("Enter notification message (empty message cancels operation):");
        return scanner.nextLine().trim();
    }

    public static String getDate() {
        System.out.println("Enter date in format " + DatetimeParser.DATE_FORMAT);
        return scanner.nextLine().trim();
    }

    public static boolean getConfirmation(String message) {
        TerminalVisualizer.printHeader(message);
        System.out.println("[y] confirm / [any other key] cancel");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            return true;
        } else {
            System.out.println("Operation cancelled.");
            return false;
        }
    }

}
