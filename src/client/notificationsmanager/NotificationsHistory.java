package client.notificationsmanager;

import client.TerminalVisualizer;
import common.DatabasePath;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

public class NotificationsHistory {

    private final String HISTORY_FILE_PATH;
    private final Vector<String> entries;

    public NotificationsHistory(String username) {
        this.HISTORY_FILE_PATH = DatabasePath.userHistoryFile(username);
        this.entries = new Vector<>();
        this.load();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public void print() {
        TerminalVisualizer.printHeader(TerminalVisualizer.HISTORY_EMOJI + " " + " Notifications history");
        if (this.isEmpty()) {
            System.out.println("(empty)");
            TerminalVisualizer.printHorizontalLine();
        }
        for (var entry : this.entries) {
            System.out.println(TerminalVisualizer.LIST_DOT + entry);
        }
        TerminalVisualizer.printHorizontalLine();
    }

    public void clear() {
        this.entries.clear();
        this.save();
    }

    public void addEntry(String date, String notificationContent) {
        this.entries.add("[" + date + "] '" + notificationContent + "'");
        this.save();
    }

    public void load() {
        try {
            File historyFile = new File(this.HISTORY_FILE_PATH);
            if (historyFile.exists()) {
                Scanner scanner = new Scanner(historyFile);
                while (scanner.hasNextLine()) {
                    this.entries.add(scanner.nextLine());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to load notifications history!");
        }
    }

    public void save() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.HISTORY_FILE_PATH));
            for (var entry : this.entries) {
                bufferedWriter.write(entry);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Failed to save notifications history!");
        }
    }

}
