package com;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Lenovo on 1/16/2016.
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

            message = inputLine.nextLine();
            auth(message);



        }catch(EOFException e) {
            System.out.println("Client ended connection");
        }catch(IOException f) {
            f.printStackTrace();
        }
    }


    public void connectToServer() throws IOException{
        System.out.println("Attempting to connect..");
        connection = new Socket("192.168.1.111",9000);
        System.out.println("Connected!!");
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); //only we can flush their crap, we can't flush our own shit
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams now setup! \n");

    }

    private void auth(String message) {
        try {

            while(true) {
                output.writeObject(message);
                message = inputLine.nextLine();
            }


        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
