package server;

import Domain.User;
import Service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * CREATED BY Janus @ 2021-03-08 - 10:15
 **/
public class ClientHandler extends Thread {
    Socket client;
    PrintWriter pw;
    BufferedReader br;
    BlockingQueue allMsgQ;
    UserService us = new UserService();
    User user = new User();
    String name;


    public ClientHandler(Socket client, BlockingQueue<String> allMsgQ) {
        this.client = client;
        this.allMsgQ = allMsgQ;
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
        name = messageArr[1];
        switch (messageArr[0]) {
            case "CONNECT":
                if (us.getUser1().getName().equals(name) || us.getUser2().getName().equals(name) || us.getUser3().getName().equals(name)) {
                    protocol();
                } else {
                    pw.println("User not found");
                    client.close();
                }
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
                    client.close();
                    break;
                case "ALL":
                    handleMsgToAll();
                    break;
                case "USERS":
                    showUsers();
                    break;
                default:
                    pw.println("Connection is closing");
                    go = false;
                    client.close();
                    break;
            }
        }
    }

    private void handleMsgToAll() throws IOException {
        pw.println("What would you like to send: ");
        String msg = br.readLine();
        allMsgQ.add(msg);
    }

    public void showUsers() {
        for (User u : us.getUsers()) {
            pw.println(u);

        }
    }
}
