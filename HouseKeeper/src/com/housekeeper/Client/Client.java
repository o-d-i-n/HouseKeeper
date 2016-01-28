package com.housekeeper.Client;

import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.*;
import com.housekeeper.Packet.server.ClientPacket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class Client  implements Runnable{

    private Socket connection;
    public ObjectOutputStream output;
    public ObjectInputStream input;
    public String message;
    public boolean running;
    public String roll_number;
    public int option;
    public String auth_code;
    List<String> connectedUsers = new ArrayList<String>();
    Thread listeningThread = null;
    Thread recievingThread = null;

    public Client() {

        try {

            connectToServer();
            setupStreams();

        }catch(IOException f) {

            System.out.println("Server not accepting connection.Timeout.Please Try Again");
        }

        running = true;
        listeningThread = new Thread(this,"Listener");
        listeningThread.start();
    }


    @Override
    public void run() {

        ServerSender sender = new ServerSender(this);
        listeningThread = new Thread(sender,"Sender");
        listeningThread.start();

        synchronized (this) {
            this.recievingThread = Thread.currentThread();
        }

        while(running){
            connection();
        }

        stop();

    }


    public void connectToServer() throws IOException{

        System.out.println("Attempting to connect..");
        connection = new Socket("192.168.0.100",9000);
        System.out.println("Connected!!");
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); //only we can flush their crap, we can't flush our own shit
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams now setup! \n");

    }

    private void connection() {
        try {
            readInput((Packet)input.readObject());
        }catch(IOException | ClassNotFoundException e) {
            System.out.println("There's a problem with the Server.Please Try again in a few minutes");
            running = false;
        }
    }




    private void readInput(Packet p) {
        if(p.type == Packet.Type.SERVER_RESPONSE) {
            System.out.println("The packet type recieved is a normal Server Response");
            ClientPacket serverResponse = (ClientPacket)p;
            dealWith(serverResponse);

        } else if(p.type == Packet.Type.CHAT) {
            ChatPacket chat = (ChatPacket) p;
            System.out.println(chat.from + "says : " + chat.message);

        } else if(p.type == Packet.Type.CONNECTED_USERS) {
            System.out.println("The packet type recieved is a connectedUsers");
            ConnectedUsers users = (ConnectedUsers) p;
            this.connectedUsers = users.connectedUsers;

        }
    }

    private void dealWith(ClientPacket serverResponse) {
        System.out.println(serverResponse.message);
        if(serverResponse.auth_code != null) {
            this.auth_code = serverResponse.auth_code;
            System.out.println("Your API key is : " + auth_code);
        }
    }


    public synchronized void stop(){
        System.out.println("Hey");
        this.running = false;
        try {
            this.connection.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing Client", e);
        }
    }
}