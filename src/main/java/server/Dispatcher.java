package server;

import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * CREATED BY Janus @ 2021-03-08 - 14:08
 **/

public class Dispatcher extends Thread {
    BlockingQueue<String> allMsg;
    BlockingQueue<PrintWriter> allWriters;

    public Dispatcher(BlockingQueue<String> allMsg) {
        this.allMsg = allMsg;
        this.allWriters = new ArrayBlockingQueue<>(200);
    }

    public void addWriterToList(PrintWriter pw) {
        allWriters.add(pw);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = allMsg.take();
                sendMessageToAll(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessageToAll(String msg) {
        for (PrintWriter pw : allWriters) {
            pw.println(msg);
        }
    }
}