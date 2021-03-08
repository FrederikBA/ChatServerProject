package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * CREATED BY Janus @ 2021-03-08 - 10:15
 **/
public class ClientHandler extends Thread {
    Socket client;
    PrintWriter pw;
    BufferedReader br;

    @Override
    public void run() {
        try {
            protocol();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientHandler(Socket client) {
        this.client = client;
        try {
            pw = new PrintWriter(client.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void protocol() throws IOException {
        pw.println("You are connected");

        boolean go = true;
        while (go) {
            String message = br.readLine(); //blocking call
            String[] messageArr = message.split("#");

            switch (messageArr[0]) {
                case "CLOSE":
                    client.close();
                    break;
                case "UPPER":
                    pw.println(messageArr[1].toUpperCase());
                    break;
                case "LOWER":
                    pw.println(messageArr[1].toLowerCase());
                    break;
                case "REVERSE":
                    StringBuilder input = new StringBuilder();
                    input.append(messageArr[1]);
                    pw.println(input.reverse());
                case "TRANSLATE":
                    Map<String, String> translateMap = new HashMap<>();
                    translateMap.put("hund", "dog");
                    if (messageArr[1].equals("hund")) {
                        pw.println(translateMap);
                    } else {
                        pw.println("NOT FOUND");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
