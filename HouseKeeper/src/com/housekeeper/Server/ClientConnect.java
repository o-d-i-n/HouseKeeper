package com.housekeeper.Server;

import com.housekeeper.Database.Statements.Insert;
import com.housekeeper.Database.Statements.Update;
import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.*;
import com.housekeeper.Packet.server.ClientPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class ClientConnect implements Runnable{

    protected Socket clientSocket;
    protected String serverText;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    public Server server;
    public String roll_number = "NA";
    public boolean running = true;
    private Insert insert;
    private Update update;
    public ClientConnect(Socket clientSocket,String serverText,Server server) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            setupStreams();
            insert = new Insert(server.mySql);
            update = new Update(server.mySql);
            while(running) {
                connection();
            }
        }catch (IOException e) {
            System.out.println("Client disconnected !");
            stop();
        }
    }

    public synchronized void stop(){
        this.running = false;
        try {
            this.clientSocket.close();
            removeFromClient();
        } catch (IOException e) {
            throw new RuntimeException("Error closing Client", e);
        }
    }

    private void removeFromClient() {

        for(int i=0;i<this.server.Clients.size();i++) {
            if(server.Clients.get(i).roll_number == roll_number) {
                server.Clients.remove(i);
                break;
            }
        }
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        output.flush(); //only we can flush their crap, we can't flush our own shit
        input = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("Streams now setup! \n");

    }

    private void connection() throws IOException {
        String message = "You are now connected!";
        try {
            Packet student = (Packet)input.readObject();
            if(auth(student)) { // all communication happens from here

            } else {
                sendToClient(new ClientPacket("Sorry,something went wrong.Please check your password,roll_number or/and API code"));
            }
        }catch(ClassNotFoundException e) {
           System.out.println("Client disconnected !");
        } catch (SQLException e) {
            System.out.println("Something went wrong with communicating with the Database.This could occur if you're trying to insert duplicate information");
        }
    }

    private boolean auth(Packet student) throws IOException, SQLException {
        if(student.type == Packet.Type.STUDENT_INFO) {
            StudentInfo studentInfo = (StudentInfo)student;

            displayStudentInfo(studentInfo);
            update.studentInfo(studentInfo,"user");
            //insert.studentInfo(studentInfo,"user");
            sendToClient(new ClientPacket("Request Successful"));


        } else if(student.type == Packet.Type.STUDENT_LOGIN){

            StudentLogin loginAttempt = (StudentLogin)student;
            String temp = loginAttempt.ifValid(server.getPassword(loginAttempt.roll_number));
            if(temp != "Nope") {
                Packet Auth = new ClientPacket(temp,"You have Logged In ! Store Your auth key,use it for all communications from now on.");
                server.storeKey(temp,loginAttempt.roll_number);
                roll_number = loginAttempt.roll_number;
                server.connectedUsers.add(roll_number);

                sendAuthKey(Auth);
                sendToClient(new ConnectedUsers(server.connectedUsers));

                System.out.println("Roll Number : " + loginAttempt.roll_number + " has logged in!");

                return true;
            } else {
                return false;
            }

        } else if(student.type == Packet.Type.STUDENT_REGISTER) {

           StudentRegister registerAttempt = (StudentRegister)student;

           insert.studentInfo(registerAttempt,"user");

           System.out.println("Roll Number : " + registerAttempt.roll_number + " is registered");
           sendToClient(new ClientPacket("Congratulations,you're registered.You should login to access your account"));

           return true;


        } else if(student.type == Packet.Type.CHAT) {
            ChatPacket chatRequest = (ChatPacket)student;
            System.out.println(chatRequest.from + " tells " + chatRequest.to + ",' " + chatRequest.message + " '" );

            //send to specified user
            ClientConnect client = server.findClientConnection(chatRequest.to);
            if(client != null) {
                client.sendToClient(chatRequest);
            }else {
                System.out.println("The client is returning a null value");
            }


        } else if(student.type == Packet.Type.CONNECTED_USERS) {
            sendToClient(new ConnectedUsers(server.connectedUsers));
            return true;
        }

        return false;
    }

    private void displayStudentInfo(StudentInfo studentInfo) {
        System.out.println("The student's name is : " + studentInfo.name);
    }

    private void sendToClient(Packet message) throws IOException {
        output.writeObject(message);
    }
    private void sendAuthKey(Packet ClientPacket) throws IOException {
        output.writeObject(ClientPacket);
    }


}