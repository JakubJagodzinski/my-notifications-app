package server;

import common.RequestRecord;
import server.requestqueue.RequestQueue;
import server.requestqueue.RequestQueueRecord;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestManager extends Thread {

    private final Socket clientSocket;

    private final RequestQueue requestQueue;

    private final ClientsList connectedClients;

    private final ObjectInputStream OIS;
    private final ObjectOutputStream OOS;

    public RequestManager(Socket clientSocket, ObjectInputStream OIS, ObjectOutputStream OOS, RequestQueue requestQueue, ClientsList connectedClients) {
        this.clientSocket = clientSocket;
        this.requestQueue = requestQueue;
        this.OIS = OIS;
        this.OOS = OOS;
        this.connectedClients = connectedClients;
        this.connectedClients.addClient(clientSocket);
    }

    public void run() {
        while (true) {
            try {
                RequestRecord requestRecord = (RequestRecord) this.OIS.readObject();
                this.requestQueue.enqueue(requestRecord.sendBackTime(), new RequestQueueRecord(this.clientSocket, this.OOS, requestRecord.notification()));
            } catch (Exception e) {
                this.connectedClients.removeClient(this.clientSocket);
                String log = this.clientSocket + ": disconnected";
                ServerLogsRegister.add(log);
                System.out.println(log);
                return;
            }
        }
    }

}
