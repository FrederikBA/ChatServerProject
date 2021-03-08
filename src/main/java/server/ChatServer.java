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
            ClientHandler clientHandler = new ClientHandler(client,allMsg);
            dispatcher.addWriterToList(clientHandler.pw);
            clientHandler.start();
        }

    }
    //Call server with arguments like this: 0.0.0.0 8088 logfile.log
    public static void main(String[] args) throws UnknownHostException {
        int port = DEFAULT_PORT;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number, use default port : " + DEFAULT_PORT);
            }
        }
        ChatServer server = new ChatServer();
        try {
            server.startServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/*
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            BufferedReader bw = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //TODO: CONNECT#Username
            //TODO: SEND#Hans#Hello Hans  -->  SEND#Peter,Hans#Hello Hans  -->  MESSAGE#Peter#Hello Hans
            Map<String,Socket> myMap = new HashMap<>();
            myMap.put("Kurt",client);
 */