package com.housekeeper.Client;

import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.StudentRegister;
import com.housekeeper.Packet.server.ClientPacket;

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
    public boolean running;
    public int option;

    public Client() {
        inputLine = new Scanner(System.in);
        running = true;
        try {

            connectToServer();
            setupStreams();
            while(running){
                connection();
            }

        }catch(EOFException e) {
            e.printStackTrace();
        }catch(IOException f) {
            System.out.println("Server not accepting connection.Timeout.Please Try Again");
        }
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
            //output.writeObject(getStudentInfo());
            // A dirty command line interface to interact with the Server
            cli();
            if(option == 1) {
                output.writeObject(sendRegistration());
            }else if(option == 2) {

            }else if(option == 3) {

            }

            ClientPacket serverResponse = (ClientPacket)input.readObject();
            System.out.println(serverResponse.message);
        }catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private StudentRegister sendRegistration() {
        /*System.out.println("Enter Roll Number");
        String roll_number = inputLine.nextLine();

        System.out.println("Enter Password");
        String password = inputLine.nextLine();*/


        StudentRegister registerAttempt = new StudentRegister("289/COE/13","007isme");
        return registerAttempt;
    }

    private void cli() {
        System.out.println("Welcome to The HouseKeeper.You have the following options:");
        System.out.println("1. Register");
        System.out.println("2. Login (You need to register to try this step out)");
        System.out.println("3. Share Your Info (You need to login before trying this step out)");
        option = inputLine.nextInt();
    }

    public void getStudentInfo() {
        System.out.println("Enter your Name:");
        String name = inputLine.nextLine();

        System.out.println("Enter your Roll Number:");
        String roll_number = inputLine.nextLine();
        System.out.println("Enter your Section:");
        String section = inputLine.nextLine();
        System.out.println("Enter your Percentage:");
        int percentage = inputLine.nextInt();

        //Packet student = new Packet(Packet.Type.STUDENT_INFO,name,roll_number,section,percentage);

        //return student;
    }


}