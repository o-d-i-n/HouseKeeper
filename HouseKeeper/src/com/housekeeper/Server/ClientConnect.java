package com.housekeeper.Server;

import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.StudentLogin;
import com.housekeeper.Packet.client.StudentRegister;
import com.housekeeper.Packet.server.ClientPacket;

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
    public Server server;

    public ClientConnect(Socket clientSocket,String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    @Override
    public void run() {
        try {
            setupStreams();
            while(true) {
                connection();
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

    private void connection() throws IOException{
        String message = "You are now connected!";
        try {
            Packet student = (Packet)input.readObject();
            if(auth(student)) { // all communication happens from here
                sendToClient("Your request was accepted !");
            } else {
                sendToClient("You screwed up !");
            }
        }catch(ClassNotFoundException e) {
           System.out.println("Client disconnected !");
        }
    }

    private boolean auth(Packet student) throws IOException {
        if(student.type == Packet.Type.STUDENT_INFO) {

        } else if(student.type == Packet.Type.STUDENT_LOGIN){

            StudentLogin loginAttempt = (StudentLogin)student;
            String temp = loginAttempt.ifValid(server.getPassword(loginAttempt.roll_number));
            if(temp != "Nope") {
                Packet Auth = new ClientPacket(temp,"Your auth key,use it for all communications from now on.");
                sendAuthKey(Auth);
            } else {
                return false;
            }

        } else if(student.type == Packet.Type.STUDENT_REGISTER) {

            StudentRegister registerAttempt = (StudentRegister)student;
            System.out.println(server.getPassword("289/COE/13"));


           if(true) {
                //server.storePassword(registerAttempt.roll_number,registerAttempt.password);*/
                return true;
            } else {

                return false;
            }



        }

        return false;
    }

    private void sendToClient(String message) throws IOException {
        output.writeObject(message);
    }
    private void sendAuthKey(Packet ClientPacket) throws IOException {
        output.writeObject(ClientPacket);
    }


}