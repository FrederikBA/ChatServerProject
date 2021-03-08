package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * CREATED BY Janus @ 2021-03-08 - 10:15
 **/
public class ClientHandler {
    Socket client;
    PrintWriter pw;
    BufferedReader br;

    public ClientHandler(Socket client) {
        this.client = client;
        try {
            pw = new PrintWriter(client.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void protocol() {
        pw.println("You are connected");
    }
}
