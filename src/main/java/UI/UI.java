package UI;

import server.ChatServer;

public class UI {

    public void startMessage() {
        ChatServer cs = new ChatServer();
        System.out.println("Server started, listening on ");
    }

}
