package server.requestqueue;

import common.DatetimeParser;
import common.ResponseRecord;
import server.ServerLogsRegister;

import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;

public class RequestQueue {

    private final ConcurrentSkipListMap<Long, Vector<RequestQueueRecord>> queue;
    private boolean closed;

    public RequestQueue() {
        this.queue = new ConcurrentSkipListMap<>();
        this.closed = false;
    }

    public boolean contains(long time) {
        return this.queue.containsKey(time);
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public void close() {
        this.closed = true;
    }

    private int totalQueueSize() {
        int totalRequests = 0;
        for (var vector : this.queue.values()) {
            totalRequests += vector.size();
        }
        return totalRequests;
    }

    public void print() {
        if (this.isEmpty()) {
            System.out.println("There are no enqueued requests");
            return;
        }
        System.out.println("[ENQUEUED REQUESTS]");
        for (var entry : this.queue.entrySet()) {
            System.out.println("\t<" + DatetimeParser.parseToDate(entry.getKey()) + ">");
            for (var requestsVector : entry.getValue()) {
                System.out.println("\t\t" + requestsVector.clientSocket() + " " + requestsVector.notification());
            }
        }
        System.out.println("TOTAL: " + this.totalQueueSize());
    }

    public void send() {
        while (!this.closed) {
            if (!this.isEmpty() && System.currentTimeMillis() > this.queue.firstKey()) {
                Vector<RequestQueueRecord> records = this.queue.pollFirstEntry().getValue();
                for (var record : records) {
                    try {
                        record.OOS().writeObject(new ResponseRecord(record.notification()));
                        String log = "Notification sent to client: " + record.clientSocket();
                        ServerLogsRegister.add(log);
                        System.out.println(log);
                    } catch (Exception e) {
                        String log = "Failed to send notification to client " + record.clientSocket();
                        ServerLogsRegister.add(log);
                        System.out.println(log);
                    }
                }
            }
        }
    }

    public synchronized void enqueue(long sendBackTime, RequestQueueRecord record) {
        if (this.contains(sendBackTime)) {
            this.queue.get(sendBackTime).add(record);
        } else {
            Vector<RequestQueueRecord> newRecords = new Vector<>();
            newRecords.add(record);
            this.queue.put(sendBackTime, newRecords);
        }
        String log = "Enqueued request from client: " + record.clientSocket();
        ServerLogsRegister.add(log);
        System.out.println(log);
    }

}
