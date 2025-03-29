package common;

public class DatabasePath {

    public static final String DATABASE_DIR = System.getProperty("user.dir") + "/database";

    public static final String SERVER_LOGS_FILE = DATABASE_DIR + "/$SERVER_LOGS.txt";

    public static final String HISTORY_FILE = "$HISTORY.txt";

    public static String userHistoryFile(String username) {
        return DATABASE_DIR + "/" + username + HISTORY_FILE;
    }
}
