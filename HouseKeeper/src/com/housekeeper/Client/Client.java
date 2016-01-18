package com.housekeeper.Client;

import com.housekeeper.Packet.Packet;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class Client {

    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    public String message;
    public Scanner inputLine;

    public Client() {
        inputLine = new Scanner(System.in);
        try {

            connectToServer();
            setupStreams();
            auth();

        }catch(EOFException e) {
            e.printStackTrace();
        }catch(IOException f) {
            System.out.println("Server not accepting connection.Timeout.Please Try Again");
        }
    }


    public void connectToServer() throws IOException{
        System.out.println("Attempting to connect..");
        connection = new Socket("169.254.186.2",9000);
        System.out.println("Connected!!");
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); //only we can flush their crap, we can't flush our own shit
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams now setup! \n");

    }

    private void auth() {
        try {
            output.writeObject(getStudentInfo());
            message = (String)input.readObject();
            System.out.println(message);
        }catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Packet getStudentInfo() {
        System.out.println("Enter your Name:");
        String name = inputLine.nextLine();

        System.out.println("Enter your Roll Number:");
        String roll_number = inputLine.nextLine();
        System.out.println("Enter your Section:");
        String section = inputLine.nextLine();
        System.out.println("Enter your Percentage:");
        int percentage = inputLine.nextInt();

        Packet student = new Packet(Packet.Type.STUDENT_INFO,name,roll_number,section,percentage);

        return student;
    }
}