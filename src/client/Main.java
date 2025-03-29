package client;

import client.notificationsmanager.NotificationsManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {

    private static final int PORT_NUMBER = 5000;

    public static Socket connectToTheServer() {
        while (true) {
            try {
                return new Socket("localhost", PORT_NUMBER);
            } catch (Exception e) {
                System.out.println("Failed to connect to the server!");
                if (InputScanner.getConfirmation("Do you want to try again?")) {
                    continue;
                }
                return null;
            }
        }
    }

    public static void disconnectFromTheServer(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (Exception ignore) {
        }
    }

    public static String getUsername(String[] args) {
        return (args.length > 0) ? args[0] : "Guest";
    }

    private static UserMenu getUserMenu(String[] args, Socket clientSocket) throws IOException {
        String username = getUsername(args);
        ObjectOutputStream OOS = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream OIS = new ObjectInputStream(clientSocket.getInputStream());
        NotificationsManager notificationsManager = new NotificationsManager(username, OIS, OOS);
        return new UserMenu(notificationsManager, username);
    }

    public static void main(String[] args) {
        TerminalVisualizer.printTitleLabel();
        Socket clientSocket = connectToTheServer();
        if (clientSocket == null) {
            return;
        }
        try {
            UserMenu userMenu = getUserMenu(args, clientSocket);
            userMenu.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        disconnectFromTheServer(clientSocket);
        TerminalVisualizer.printCopyrightLabel();
    }

}
