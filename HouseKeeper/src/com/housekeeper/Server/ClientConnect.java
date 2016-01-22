package com.housekeeper.Server;

import com.housekeeper.Client.Client;
import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.StudentInfo;
import com.housekeeper.Packet.client.StudentLogin;
import com.housekeeper.Packet.client.StudentRegister;
import com.housekeeper.Packet.server.ClientPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

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

    public ClientConnect(Socket clientSocket,String serverText,Server server) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            setupStreams();
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
        }
    }

    private boolean auth(Packet student) throws IOException {
        if(student.type == Packet.Type.STUDENT_INFO) {
            StudentInfo studentInfo = (StudentInfo)student;
            if(server.checkKey(studentInfo.auth_code,studentInfo.roll_number)) {
                displayStudentInfo(studentInfo);
                sendToClient(new ClientPacket("Request Successful"));
            } else {
                return false;
            }

        } else if(student.type == Packet.Type.STUDENT_LOGIN){

            StudentLogin loginAttempt = (StudentLogin)student;
            String temp = loginAttempt.ifValid(server.getPassword(loginAttempt.roll_number));
            if(temp != "Nope") {
                Packet Auth = new ClientPacket(temp,"You have Logged In ! Store Your auth key,use it for all communications from now on.");
                server.storeKey(temp,loginAttempt.roll_number);
                sendAuthKey(Auth);
                System.out.println("Roll Number : " + loginAttempt.roll_number + " has logged in!");
                roll_number = loginAttempt.roll_number;
                return true;
            } else {
                return false;
            }

        } else if(student.type == Packet.Type.STUDENT_REGISTER) {

            StudentRegister registerAttempt = (StudentRegister)student;

           if(server.storePassword(registerAttempt.roll_number,registerAttempt.password)) {
                server.storePassword(registerAttempt.roll_number,registerAttempt.password);
                System.out.println("Roll Number : " + registerAttempt.roll_number + " is registered");
                sendToClient(new ClientPacket("Congratulations,you're registered.You should login to access your account"));
                return true;
            } else {

                return false;
            }



        }

        return false;
    }

    private void displayStudentInfo(StudentInfo studentInfo) {
        System.out.println("The student's name is : " + studentInfo.name);
    }

    private void sendToClient(ClientPacket message) throws IOException {
        output.writeObject(message);
    }
    private void sendAuthKey(Packet ClientPacket) throws IOException {
        output.writeObject(ClientPacket);
    }


}