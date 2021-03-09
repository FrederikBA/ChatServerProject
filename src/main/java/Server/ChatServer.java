package Server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChatServer {
    private static final int DEFAULT_PORT = 5555;
    private ServerSocket serverSocket;
    public BlockingQueue<String> allMsg = new ArrayBlockingQueue<>(250);
    public ConcurrentMap<String, PrintWriter> allNameWriters = new ConcurrentHashMap<>();

    private void startServer(int port) throws IOException {
        Dispatcher dispatcher = new Dispatcher(allMsg, allNameWriters);
        dispatcher.start();
        serverSocket = new ServerSocket(port);

        System.out.println("Server started, listening on port: " + port);
        while (true) {
            System.out.println("Waiting for a client");
            Socket client = serverSocket.accept();
            System.out.println("New client connected");
            ClientHandler clientHandler = new ClientHandler(client, allMsg, allNameWriters);
            dispatcher.addWriterToList(clientHandler.pw);
            clientHandler.start();
        }

    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        ChatServer server = new ChatServer();
        try {
            server.startServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
