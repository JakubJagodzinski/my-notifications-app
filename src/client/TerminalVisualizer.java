package client;

public class TerminalVisualizer {

    public static final int LINE_WIDTH = 70;

    public static final String PLUS_SIGN_MARK = "\u2795";
//    public static final String LIST_DOT = "• ";
    public static final String LIST_DOT = "\u2022";
    public static final String BELL_EMOJI = "\uD83D\uDD14";
    public static final String HISTORY_EMOJI = "\uD83D\uDCD6";

    public static void printHorizontalLine() {
        System.out.println("-".repeat(LINE_WIDTH));
    }

    public static void printCenteredText(String message) {
        int paddingWidth = (LINE_WIDTH - message.length()) / 2;
        System.out.println(" ".repeat(paddingWidth) + message);
    }

    public static void printHeader(String message) {
        printHorizontalLine();
        printCenteredText(message);
        printHorizontalLine();
    }

    public static void printTitleLabel() {
        printHeader(BELL_EMOJI + " My Notifications App");
    }

    public static void printCopyrightLabel() {
        printHorizontalLine();
        printCenteredText(BELL_EMOJI + " My Notifications App");
        printCenteredText("© 2024 Jakub Jagodziński");
        printCenteredText("All Rights Reserved");
        printHorizontalLine();
    }

}
