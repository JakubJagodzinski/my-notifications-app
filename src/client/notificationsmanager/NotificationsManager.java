package client.notificationsmanager;

import client.InputScanner;
import client.TerminalVisualizer;
import common.DatetimeParser;
import common.RequestRecord;
import common.ResponseRecord;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NotificationsManager {

    private final ObjectInputStream OIS;
    private final ObjectOutputStream OOS;

    private final NotificationsHistory notificationsHistory;

    public NotificationsManager(String username, ObjectInputStream OIS, ObjectOutputStream OOS) {
        this.notificationsHistory = new NotificationsHistory(username);
        this.OIS = OIS;
        this.OOS = OOS;
    }

    public void printHistory() {
        this.notificationsHistory.print();
    }

    public void clearHistory() {
        if (this.notificationsHistory.isEmpty()) {
            System.out.println("Your notifications history is already empty.");
            return;
        }
        if (InputScanner.getConfirmation("Do you want to clear your notifications history?")) {
            this.notificationsHistory.clear();
            System.out.println("You notifications history has been cleared.");
        }
    }

    public void setNewNotification() {
        TerminalVisualizer.printHeader(TerminalVisualizer.PLUS_SIGN_MARK + " Set new notification");
        try {
            RequestRecord requestRecord = this.enterNotificationContent();
            if (requestRecord == null) {
                System.out.println("Operation cancelled.");
                TerminalVisualizer.printHorizontalLine();
                return;
            }
            this.sendNotificationRequest(requestRecord);
            System.out.println("Notification request sent!");
            TerminalVisualizer.printHorizontalLine();
        } catch (IncorrectNotificationContentException e) {
            System.out.println("Incorrect notification content!");
        } catch (Exception e) {
            System.out.println("Couldn't send notification request!");
        }
    }

    public RequestRecord enterNotificationContent() throws IncorrectNotificationContentException {
        try {
            String notificationContent = InputScanner.getNotificationContent();
            if (notificationContent.isEmpty()) {
                return null;
            }
            String date = InputScanner.getDate();
            return new RequestRecord(DatetimeParser.parseDateToMillis(date), notificationContent);
        } catch (Exception e) {
            throw new IncorrectNotificationContentException();
        }
    }

    public void sendNotificationRequest(RequestRecord requestRecord) throws Exception {
        this.OOS.writeObject(requestRecord);
    }

    public void printNewNotification(String notificationContent) {
        System.out.println();
        TerminalVisualizer.printHeader(TerminalVisualizer.BELL_EMOJI + " New notification!");
        System.out.println(notificationContent);
        TerminalVisualizer.printHorizontalLine();
    }

    public void receiveNotification() {
        while (true) {
            try {
                ResponseRecord responseRecord = (ResponseRecord) this.OIS.readObject();
                this.notificationsHistory.addEntry(DatetimeParser.getCurrentDate(), responseRecord.notification());
                this.printNewNotification(responseRecord.notification());
            } catch (Exception e) {
                if (Thread.interrupted()) {
                    return;
                }
                System.out.println("Disconnected from the server");
                System.exit(-1);
            }
        }
    }

}
