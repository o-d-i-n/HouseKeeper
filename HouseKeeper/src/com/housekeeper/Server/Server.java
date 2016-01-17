package com.housekeeper.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class Server implements Runnable{

    private int port;
    private ServerSocket server;
    private Socket clientSocket;
    private Thread runningThread = null;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10); // allows for 10 thread connections;
    public boolean running = false;

    public Server(int port) {
        this.port = port;
        try {
            server = new ServerSocket(port,100);
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }

        running = true;
        runningThread = new Thread(this,"Server");
        runningThread.start();
    }

    public void run() {

        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        while(running) {
            try{
                waitForConnection();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        this.threadPool.shutdown();
        System.out.println("Server Stopped!");

    }

    //wait for a connection, then display connection information

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for someone to connect... \n");
        clientSocket = server.accept();
        System.out.println("Now connected to..."+clientSocket.getInetAddress().getHostName()+"\n");
        this.threadPool.execute(new ClientConnect(clientSocket,"Thread Pooled Server"));
    }

    private synchronized boolean isStopped() {
        return this.running;
    }

    public synchronized void stop(){
        this.running = true;
        try {
            this.server.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }


}