package com.housekeeper.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class Server implements Runnable{

    private int port;
    public HashMap<String,String> Passwords;
    public HashMap<String,String> API_KEYS;
    public List<ClientConnect> Clients = new ArrayList<ClientConnect>();
    private ServerSocket server;
    private Socket clientSocket;
    private Thread runningThread = null;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10); // allows for 10 thread connections;
    public boolean running = false;

    public Server(int port) {
        this.port = port;
        Passwords = new HashMap();
        API_KEYS = new HashMap();

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
        ClientConnect newClient = new ClientConnect(clientSocket,"Thread Pooled Server",this);
        Clients.add(newClient);
        this.threadPool.execute(newClient);
        displayConnectedUsers();
    }

    private void displayConnectedUsers() {
        for(int i=0;i<Clients.size();i++) System.out.println(Clients.get(i).roll_number);
    }

    private synchronized boolean isStopped() {
        return this.running;
    }

    public synchronized void stop(){
        this.running = false;
        try {
            this.server.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    public String getPassword(String roll_number) {

        if(Passwords.get(roll_number) != null) {
            return Passwords.get(roll_number);
        }
        else
            return "Nope";
    }
    public boolean storePassword(String roll_number,String password) {
        if(Passwords.get(roll_number) == null) {
            Passwords.put(roll_number,password);
            return true;
        } else
            return false;
    }


    public void storeKey(String key,String roll_number) {

        API_KEYS.put(key,roll_number);
    }
    public boolean checkKey(String key,String roll_number) {

        if(Objects.equals(API_KEYS.get(key),roll_number)) {
            return true;
        } else
            return false;


    }


}