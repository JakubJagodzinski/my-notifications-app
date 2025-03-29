package server;

import java.net.Socket;
import java.util.Vector;

public class ClientsList {

    private final Vector<Socket> connectedClients;

    public ClientsList() {
        this.connectedClients = new Vector<>();
    }

    public boolean isEmpty() {
        return this.connectedClients.isEmpty();
    }

    public void print() {
        if (this.connectedClients.isEmpty()) {
            System.out.println("There are no connected clients");
            return;
        }
        System.out.println("[CONNECTED CLIENTS]");
        for (Socket client : this.connectedClients) {
            System.out.println("\t" + client);
        }
        System.out.println("TOTAL: " + this.connectedClients.size());
    }

    public void disconnectAll() {
        try {
            for (Socket client : this.connectedClients) {
                client.close();
            }
        } catch (Exception ignore) {
        }
        this.connectedClients.clear();
    }

    public synchronized void addClient(Socket client) {
        this.connectedClients.add(client);
    }

    public synchronized void removeClient(Socket client) {
        this.connectedClients.remove(client);
    }

}
