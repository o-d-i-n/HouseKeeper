package com.housekeeper.Client;

import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.*;
import com.housekeeper.Packet.server.ClientPacket;
import javafx.scene.control.TextArea;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class Client  implements Runnable {

    private Socket connection;
    public ObjectOutputStream output;
    public ObjectInputStream input;
    public ServerSender sender;
    public StudentInfo random_user;
    public boolean running;
    public TimeTable timetable;
    public String roll_number;
    public String auth_code;
    public List<String> connectedUsers = new ArrayList<String>();
    Thread listeningThread = null;
    Thread recievingThread = null;
    Object[] subjectSet;
    TextArea chatBox;

    public Client(TextArea chatBox) {
        this.chatBox = chatBox;
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



    public void run() {

        this.sender = new ServerSender(this);
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
        connection = new Socket("192.168.0.103",9000);
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
            String s = chat.from + "says : " + chat.message;
            chatBox.setText(chatBox.getText() + "\n" + s);

        } else if(p.type == Packet.Type.CONNECTED_USERS) {
            ConnectedUsers users = (ConnectedUsers) p;
            System.out.println(users.message);
            this.connectedUsers = users.connectedUsers;

        } else if(p.type == Packet.Type.TIMETABLE) {
                timetable = (TimeTable) p;
                this.subjectSet = timetable.displayTimeTable().toArray();
        } else if(p.type == Packet.Type.STUDENT_REQ) {
                random_user = (StudentInfo)p;
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
        System.out.println("Connection closing down");
        this.running = false;
        try {
            this.connection.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing Client", e);
        }
    }


}