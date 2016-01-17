package com.housekeeper.Server;

import com.housekeeper.Packet.Packet;

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
            System.out.println("Client disconnected !");
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
            Packet student = (Packet)input.readObject();
            displayStudentInfo(student);
            System.out.println(message);
        }catch(ClassNotFoundException e) {
           System.out.println("Client disconnected !");
        }
    }

    private void displayStudentInfo(Packet student) {

        if(student.type == Packet.Type.STUDENT_INFO) {

            System.out.println("Name: " + student.name);
            System.out.println("Enter your Roll Number: " + student.roll_number);
            System.out.println("Enter your Section: " + student.section);
            System.out.println("Enter your Percentage: " + student.percentage);
        }
    }
}