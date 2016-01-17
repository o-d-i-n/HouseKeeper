package com.housekeeper.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class ClientConnect implements Runnable{

    protected Socket clientSocket;
    protected String serverText;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientConnect(Socket clientSocket,String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    @Override
    public void run() {
        try {
            setupStreams();
            while(true) {
                auth();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        output.flush(); //only we can flush their crap, we can't flush our own shit
        input = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("Streams now setup! \n");

    }

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