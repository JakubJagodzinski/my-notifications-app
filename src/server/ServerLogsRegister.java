package server;

import common.DatabasePath;
import common.DatetimeParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.ConcurrentSkipListMap;

public class ServerLogsRegister extends Thread {

    private static final ConcurrentSkipListMap<Long, String> logs = new ConcurrentSkipListMap<>();

    private static boolean closed = true;

    public static void open() {
        closed = false;
    }

    public static void close() {
        closed = true;
    }

    public static synchronized void add(String log) {
        logs.put(System.currentTimeMillis(), log);
    }

    private static void pushToFile() {
        try {
            File serverLogsRegister = new File(DatabasePath.SERVER_LOGS_FILE);
            FileWriter fileWriter = new FileWriter(serverLogsRegister, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (var log : logs.entrySet()) {
                bufferedWriter.write(DatetimeParser.parseToDate(log.getKey()) + " " + log.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
        }
        logs.clear();
    }

    public void run() {
        open();
        add("Server turned on");
        pushToFile();
        while (!closed) {
            try {
                Thread.sleep(5_000);
            } catch (Exception ignore) {
            }
            pushToFile();
        }
        add("Server turned off");
        pushToFile();
    }

}
