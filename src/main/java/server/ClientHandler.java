package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * CREATED BY Janus @ 2021-03-08 - 10:15
 **/
public class ClientHandler {
ConcurrentMap<String, PrintWriter> allNamePrintWriters;
ConcurrentMap<String, Socket> allNamedSockets;
BlockingQueue<PrintWriter> allWriteToClientLine;
BlockingQueue<String> allMessages;
BlockingQueue<String> allUsers;

}
