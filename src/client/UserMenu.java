package client;

import client.notificationsmanager.NotificationsManager;

public class UserMenu {

    private final NotificationsManager notificationsManager;
    private final String username;

    public UserMenu(NotificationsManager notificationsManager, String username) {
        this.notificationsManager = notificationsManager;
        this.username = username;
    }

    public void printAvailableOperations() {
        TerminalVisualizer.printHeader("Hi, " + this.username + "! Choose operation number:");
        System.out.println("1) set new notification");
        System.out.println("2) display notifications history");
        System.out.println("3) clear notifications history");
        System.out.println("4) quit");
        TerminalVisualizer.printHorizontalLine();
    }

    public void run() {
        Thread notificationsReceiver = new Thread(this.notificationsManager::receiveNotification);
        notificationsReceiver.start();
        while (notificationsReceiver.isAlive()) {
            printAvailableOperations();
            switch (InputScanner.getOperationNumber()) {
                case 1:
                    this.notificationsManager.setNewNotification();
                    break;
                case 2:
                    this.notificationsManager.printHistory();
                    break;
                case 3:
                    this.notificationsManager.clearHistory();
                    break;
                case 4:
                    if (InputScanner.getConfirmation("Do you want to quit?")) {
                        notificationsReceiver.interrupt();
                        TerminalVisualizer.printHorizontalLine();
                        return;
                    }
                    TerminalVisualizer.printHorizontalLine();
                    break;
                default:
                    System.out.println("Error: Incorrect operation number!");
            }
            System.out.println();
        }
    }

}
