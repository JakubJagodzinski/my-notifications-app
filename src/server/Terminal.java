package server;

import common.DatetimeParser;
import server.requestqueue.RequestQueue;

import java.net.ServerSocket;
import java.time.ZoneId;
import java.util.Scanner;

public class Terminal extends Thread {

    private final ServerSocket serverSocket;

    private final ClientsList connectedClients;

    private final RequestQueue requestQueue;

    private final long serverLaunchTime;

    public Terminal(ServerSocket serverSocket, ClientsList connectedClients, RequestQueue requestQueue) {
        this.serverSocket = serverSocket;
        this.connectedClients = connectedClients;
        this.requestQueue = requestQueue;
        this.serverLaunchTime = System.currentTimeMillis();
    }

    private void printHelp() {
        System.out.println("[COMMANDS]");
        System.out.println("\tinfo - prints server info");
        System.out.println("\ttime - prints server time");
        System.out.println("\tclients - list clients connected to the server");
        System.out.println("\trequests - prints enqueued notifications");
        System.out.println("\tshutdown - shuts down the server");
    }

    private void printTimeInfo() {
        System.out.println("Time zone: " + ZoneId.systemDefault());
        System.out.println("Current time: " + DatetimeParser.parseToDate(System.currentTimeMillis()));
        System.out.println("Server launched: " + DatetimeParser.parseToDate(this.serverLaunchTime));
        System.out.println("Server is up for: " + DatetimeParser.parseToTime(DatetimeParser.getTimeDiff(this.serverLaunchTime)));
    }

    private void printServerInfo() {
        System.out.println("Server name: My Notifications App Server");
        System.out.println("Version: 1.0.0");
        System.out.println("Owner: Jakub Jagodziński");
        System.out.println("Purpose: server for \"My Notifications App\" to resend notifications from clients at specified time.");
        System.out.println("Features:");
        System.out.println("\t- receiving and enqueueing notifications (separate thread for each client)");
        System.out.println("\t- resending notifications (one thread to browse through request queue and resend notifications)");
        System.out.println("\t- browsing enqueued requests");
        System.out.println("\t- browsing connected clients");
        System.out.println("\t- storing history in server logs");
    }

    private void shutdown() throws Exception {
        System.out.println("Server is shutting down...");
        System.out.println("Closing server socket...");
        this.serverSocket.close();
        System.out.println("Server socket closed");
        System.out.println("Closing request queue...");
        this.requestQueue.close();
        System.out.println("Request queue closed");
        System.out.println("Closing clients connections...");
        this.connectedClients.disconnectAll();
        System.out.println("Clients connections closed");
        Thread.sleep(1_000);
        ServerLogsRegister.close();
        System.out.println("Server shut down.");
    }

    public void run() {
        System.out.println("Type 'help' for commands");
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine().trim();
                switch (command) {
                    case "help":
                        this.printHelp();
                        break;
                    case "time":
                        this.printTimeInfo();
                        break;
                    case "info":
                        this.printServerInfo();
                        break;
                    case "clients":
                        this.connectedClients.print();
                        break;
                    case "requests":
                        this.requestQueue.print();
                        break;
                    case "shutdown":
                        this.shutdown();
                        return;
                    default:
                        System.out.println("Unknown command: " + command);
                }
            }
        } catch (Exception e) {
            System.out.println("Terminal crashed!");
            System.exit(-1);
        }
    }

}
