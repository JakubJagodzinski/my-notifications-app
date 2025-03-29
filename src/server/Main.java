package server;

import server.requestqueue.RequestQueue;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int PORT_NUMBER = 5000;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

            ServerLogsRegister serverLogsRegisterThread = new ServerLogsRegister();
            serverLogsRegisterThread.start();

            System.out.println("\"My Notifications App\" server launched.");
            System.out.println("Listening on port " + PORT_NUMBER);

            ClientsList clientsList = new ClientsList();

            RequestQueue requestQueue = new RequestQueue();
            Thread sendingThread = new Thread(requestQueue::send);
            sendingThread.start();

            Terminal terminalThread = new Terminal(serverSocket, clientsList, requestQueue);
            terminalThread.start();

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ObjectInputStream OIS = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream OOS = new ObjectOutputStream(clientSocket.getOutputStream());
                    String log = clientSocket + ": connected";
                    ServerLogsRegister.add(log);
                    System.out.println(log);
                    RequestManager requestManager = new RequestManager(clientSocket, OIS, OOS, requestQueue, clientsList);
                    requestManager.start();
                } catch (Exception e) {
                    if (serverSocket.isClosed()) {
                        return;
                    }
                    System.out.println("Failed to connect with client!");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }

}
