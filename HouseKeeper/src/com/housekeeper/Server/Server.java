package com.housekeeper.Server;



import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.ConnectedUsers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class Server implements Runnable{

    private int port;

    public List<ClientConnect> Clients = new ArrayList<ClientConnect>();
    public List<String> connectedUsers = new ArrayList<String>();

    private ServerSocket server;
    private Socket clientSocket;
    private Thread runningThread = null;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10); // allows for 10 thread connections;
    public boolean running = false;
    public Database mySql;

    public Server(int port) {
        this.port = port;


        try {
            server = new ServerSocket(port,100);

            mySql = new Database();


        }catch(Exception e) {
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

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for someone to connect... \n");
        clientSocket = server.accept();
        System.out.println("Now connected to..."+ clientSocket.getInetAddress().getHostName() +"\n");
        ClientConnect newClient = new ClientConnect(clientSocket,"Thread Pooled Server",this);
        this.threadPool.execute(newClient);
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

    public ClientConnect findClientConnection(String roll_number) {
        for(int i=0;i<Clients.size();i++) {

            if(Objects.equals(Clients.get(i).roll_number,roll_number)) {
                return Clients.get(i);
            }
        }
        return null;
    }

    public void addToConnectedClientList(ClientConnect client) {
        Clients.add(client);
        connectedUsers.add(client.roll_number);
    }

    public void broadcast() throws IOException {

        for(int i=0;i<Clients.size();i++) {
            Clients.get(i).output.writeObject(new ConnectedUsers(connectedUsers));
        }
    }

}
