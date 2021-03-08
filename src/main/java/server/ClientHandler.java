package server;

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
            protocol();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void protocol() throws IOException {
        pw.println("You are connected");

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
}
