package com.housekeeper.Client;

import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.StudentInfo;
import com.housekeeper.Packet.client.StudentLogin;
import com.housekeeper.Packet.client.StudentRegister;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Lenovo on 1/24/2016.
 */
public class ServerSender implements Runnable {

    public Client client;
    public Integer option;
    public Scanner inputLine;
    public ServerSender(Client client) {

        this.client = client;
        inputLine = new Scanner(System.in);
    }

    @Override
    public void run() {
        while(client.running) {
            cli();
            try {
                if (option == 1) {
                    client.output.writeObject(sendRegistration());
                } else if (option == 2) {
                    client.output.writeObject(sendLoginRequest());
                } else if (option == 3) {
                    client.output.writeObject(sendStudentInfo());
                } else if(option == 4) {
                    displayConnectedUsers();
                }
            } catch (IOException e) {
                // close connections gracefully
            }
        }
    }

    private void cli() {
        System.out.println("Welcome to The HouseKeeper.You have the following options:");
        System.out.println("1. Register");
        System.out.println("2. Login (You need to register to try this step out)");
        System.out.println("3. Share Your Info (You need to login before trying this step out)");
        System.out.println("4. See connected Users");


        option = inputLine.nextInt();
    }

    private StudentLogin sendLoginRequest() {

        System.out.println("Enter Roll Number");
        inputLine.nextLine();
        String roll_number = inputLine.nextLine();

        System.out.println("Enter Password");
        String password = inputLine.nextLine();

        client.roll_number = roll_number;
        StudentLogin loginAttempt = new StudentLogin(roll_number,password);
        return loginAttempt;
    }

    private StudentRegister sendRegistration() {
        System.out.println("Enter Roll Number");
        inputLine.nextLine();
        String roll_number = inputLine.nextLine();

        System.out.println("Enter Password");
        String password = inputLine.nextLine();


        StudentRegister registerAttempt = new StudentRegister(roll_number,password);
        return registerAttempt;
    }

    public StudentInfo sendStudentInfo() {

        StudentInfo student = new StudentInfo(Packet.Type.STUDENT_INFO,"Karanbir","COE",98,2,client.auth_code,client.roll_number);
        return student;
        //Packet student = new Packet(Packet.Type.STUDENT_INFO,name,roll_number,section,percentage);

        //return student;
    }


    private void displayConnectedUsers() {
        if(client.connectedUsers.isEmpty()) {

            System.out.println("Sorry, no connected Users as of now");
            return;
        }

        System.out.println("The users currently connected are: ");

        for(int i=0;i<client.connectedUsers.size();i++) {
            System.out.println(i + ". " + client.connectedUsers.get(i));
        }
    }

}
