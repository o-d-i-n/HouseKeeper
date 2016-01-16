package com.student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Lenovo on 1/16/2016.
 */
public class Server implements Runnable{

    private int port;
    private ServerSocket server;
    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Thread run;

    public boolean running = false;

    public Server(int port) {
        this.port = port;
        try {
            server = new ServerSocket(port,100);
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }

        run = new Thread(this,"Server");
        run.start();
    }

    public void run() {
        running = true;
        System.out.println("Server started on port: " + port);
        try {
            while (running) {
                //wait for connection
                waitForConnection();
                setupStreams(); // the output and input stream
                auth(); //auth
                // then begin transfer of data
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    //wait for a connection, then display connection information

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for someone to connect... \n");
        connection = server.accept();
        System.out.println("Now connected to..."+connection.getInetAddress().getHostName()+"\n");

    }

    // get stream to send and recieve data

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); //only we can flush their crap, we can't flush our own shit
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams now setup! \n");

    }

    //initial message exchange

    private void auth() throws IOException{
        String message = "You are now connected!";
        try {
            message = (String)input.readObject();
            System.out.println(message);
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
