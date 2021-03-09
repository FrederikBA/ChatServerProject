package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * CREATED BY Janus @ 2021-03-08 - 14:08
 **/

public class Dispatcher extends Thread {
    BlockingQueue<String> allMsg;
    BlockingQueue<PrintWriter> allWriters;
    ConcurrentMap<String, PrintWriter> allNameWriters;

    public Dispatcher(BlockingQueue<String> allMsg, ConcurrentMap<String, PrintWriter> allNameWriters) {
        this.allMsg = allMsg;
        this.allWriters = new ArrayBlockingQueue<>(200);
        this.allNameWriters = allNameWriters;
    }

    public void addWriterToList(PrintWriter pw) {
        allWriters.add(pw);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = allMsg.take();
                sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessageToAll(String msg) {
        for (PrintWriter pw : allWriters) {

        }
    }

    private void sendMessage(String msg) {
        String[] msgArr;
        findPrintWriter("Lone").println(msg);
        System.out.println(msg);


        //TODO: SEND#Kurt,Lone#Hej Lone -->  MESSAGE#Kurt#Hej Lone
        //TODO: Find Lones PrintWriter
    }

    private PrintWriter findPrintWriter(String name) {
        PrintWriter pw = null;
        pw = allNameWriters.get(name);
        return pw;
    }
}


//TODO: SEND#Hans#Hello Hans  -->  SEND#Peter,Hans#Hello Hans  -->  MESSAGE#Peter#Hello Hans
//      Map<String,Socket> myMap = new HashMap<>();
//      myMap.put("Kurt",client);
