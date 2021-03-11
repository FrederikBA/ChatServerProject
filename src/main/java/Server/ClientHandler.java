package Server;

import Domain.User;
import Service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class ClientHandler extends Thread {
    Socket client;
    PrintWriter pw;
    BufferedReader br;
    BlockingQueue allMsgQ;
    UserService us = new UserService();
    String name;
    public ConcurrentMap<String, PrintWriter> allNameWriters;

    public ClientHandler(Socket client, BlockingQueue<String> allMsgQ, ConcurrentMap<String, PrintWriter> allNameWriters) {
        this.client = client;
        this.allMsgQ = allMsgQ;
        this.allNameWriters = allNameWriters;
        try {
            pw = new PrintWriter(client.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() throws IOException {
        pw.println("Proceed to log in:");
        String message = br.readLine();
        String[] messageArr = message.split("#");
        switch (messageArr[0]) {
            case "CONNECT":
                name = messageArr[1];
                if (us.getUsernames().contains(name)) {
                    allNameWriters.put(name, pw);
                    handleStatusMsg(messageArr);
                    protocol();
                } else {
                    pw.println("User not found");
                    System.out.println("Client disconnected");
                    client.close();
                }
                break;
            default:
                pw.println("Unknown command, connection is closing");
                System.out.println("Client disconnected");
                client.close();
                break;
        }
    }

    public void protocol() throws IOException {
        pw.println("You are connected");
        pw.println("Connected as: " + name);
        boolean go = true;
        while (go) {
            String message = br.readLine();
            String[] messageArr = message.split("#");

            switch (messageArr[0]) {
                case "CLOSE":
                    pw.println("Connection is closing");
                    System.out.println("Client " + "\"" + name + "\"" + " disconnected");
                    go = false;
                    allNameWriters.remove(name, pw);
                    handleStatusOfflineMsg(message);
                    client.close();
                    break;
                case "SEND":
                    handleMsg(messageArr);
                    break;
                case "USERS":
                    showUsers();
                    break;
                case "ONLINE":
                    showOnlineUsers();
                    break;
                default:
                    pw.println("Unknown Command");
                    break;
            }
        }
    }

    private void handleMsg(String[] messageArr) {
        String msg = messageArr[0] + "#" + messageArr[1] + "#" + "\"" + messageArr[2] + "\"" + " from " + name;
        allMsgQ.add(msg);
    }

    private synchronized void handleStatusMsg(String[] messageArr) {
        if (messageArr[0].equals("CONNECT")) {
            String msg = messageArr[0] + "#" + messageArr[1] + ",";
            allMsgQ.add(msg);
        }
    }

    private void handleStatusOfflineMsg(String message) {
        if (message.equals("CLOSE")) {
            allMsgQ.add(message);
        }
    }


    public void showUsers() {
        for (User u : us.getUsers()) {
            pw.println(u);
        }
    }

    public void showOnlineUsers() {
        for (String key : allNameWriters.keySet()) {
            pw.println(key);
        }
    }
}
