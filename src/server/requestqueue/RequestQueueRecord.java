package server.requestqueue;

import java.io.ObjectOutputStream;
import java.net.Socket;

public record RequestQueueRecord(Socket clientSocket, ObjectOutputStream OOS, String notification) {
}
