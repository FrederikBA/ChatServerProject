package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ChatServer {
    private static final int DEFAULT_PORT = 5555;
    private ServerSocket serverSocket;
    public BlockingQueue<String> allMsg = new ArrayBlockingQueue<>(250);

    private void startServer(int port) throws IOException {
        Dispatcher dispatcher = new Dispatcher(allMsg);
        dispatcher.start();
        serverSocket = new ServerSocket(port);

        System.out.println("Server started, listening on port: " + port);
        while (true) {
            System.out.println("Waiting for a client");
            Socket client = serverSocket.accept();
            System.out.println("New client connected");
            ClientHandler clientHandler = new ClientHandler(client, allMsg);
            dispatcher.addWriterToList(clientHandler.pw);
            clientHandler.start();
        }

    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        System.out.println("Invalid port number, use default port : " + DEFAULT_PORT);

        ChatServer server = new ChatServer();
        try {
            server.startServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//TODO: CONNECT#Username
//TODO: SEND#Hans#Hello Hans  -->  SEND#Peter,Hans#Hello Hans  -->  MESSAGE#Peter#Hello Hans
//       Map<String,Socket> myMap = new HashMap<>();
//       myMap.put("Kurt",client);
