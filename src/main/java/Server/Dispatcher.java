package Server;

import Domain.User;
import Service.UserService;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
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
    UserService us = new UserService();

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

    private void sendMessage(String msg) {
        String[] msgArr = msg.split("#");
        String users = msgArr[1];
        String[] userArr = users.split(",");

        if (msgArr[1].equals("*")) {
            for (String key:allNameWriters.keySet()) {
                findPrintWriter(key).println(msgArr[2]);
            }
        } else {
            for (int i = 0; i < userArr.length; i++) {
                findPrintWriter(userArr[i]).println(msgArr[2]);
            }
        }
    }

    private PrintWriter findPrintWriter(String name) {
        PrintWriter pw = null;
        pw = allNameWriters.get(name);
        return pw;
    }

    public void sendOnlineMsg(){


        }
    }


