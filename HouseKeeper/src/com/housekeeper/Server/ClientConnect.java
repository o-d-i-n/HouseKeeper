package com.housekeeper.Server;

import com.housekeeper.Database.Statements.Insert;
import com.housekeeper.Database.Statements.Select;
import com.housekeeper.Database.Statements.Update;
import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.*;
import com.housekeeper.Packet.server.ClientPacket;
import com.housekeeper.Parser.RollNumberParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class ClientConnect implements Runnable{

    protected Socket clientSocket;
    protected String serverText;
    public ObjectOutputStream output;
    private ObjectInputStream input;
    public Server server;
    private StudentInfo studentInfo;
    public String roll_number = "NA";
    public boolean running = true;
    private Insert insert;
    private Update update;
    private Select select;
    private String auth_code;
    private SecureRandom random = new SecureRandom();


    public ClientConnect(Socket clientSocket,String serverText,Server server) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.server = server;
    }

    public void run() {
        try {
            setupStreams();

            insert = new Insert(server.mySql);
            update = new Update(server.mySql);
            select = new Select(server.mySql);

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

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        output.flush(); //only we can flush their crap, we can't flush our own shit
        input = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("Streams now setup! \n");

    }

    private void connection() throws IOException {

        try {

            Packet student = (Packet)input.readObject();
            if(auth(student)) { // all communication happens from here

            } else {
                System.out.println("Sorry attempt to auth failed");
                sendToClient(new ClientPacket("Sorry,something went wrong.Please check your password,roll_number or/and API code"));
            }

        }catch(ClassNotFoundException e) {
           System.out.println("Client disconnected !");
        } catch (SQLException e) {

            sendToClient(new ClientPacket("Sorry,something went wrong.Please check your password,roll_number or/and API code"));
            System.out.println("Something went wrong with communicating with the Database.This could occur if you're trying to insert duplicate information");
        }
    }

    private boolean auth(Packet student) throws IOException, SQLException {


        if(student.type == Packet.Type.STUDENT_INFO) {


            if(Objects.equals(student.auth_code, auth_code) ) {

                studentInfo = (StudentInfo)student;
                update.studentInfo(studentInfo, "user");


                sendToClient(new ClientPacket("Request Successful"));

                return true;
            }



        } else if(student.type == Packet.Type.STUDENT_LOGIN){

            StudentLogin loginAttempt = (StudentLogin)student;

            if(select.login(loginAttempt)) {

                auth_code = new BigInteger(130, random).toString(32);
                Packet Auth = new ClientPacket(auth_code,"You have Logged In ! Store Your auth key,use it for all communications from now on.");

                //HouseKeeping chores

                roll_number = loginAttempt.roll_number;
                server.addToConnectedClientList(this);

                //Sending confirmation back to the client

                sendToClient(Auth);
                server.broadcast();

                System.out.println("Roll Number : " + loginAttempt.roll_number + " has logged in!");

                return true;
            }

        } else if(student.type == Packet.Type.STUDENT_REGISTER) {

           StudentRegister registerAttempt = (StudentRegister)student;

           if(!select.checkIfUser(registerAttempt)) {
               insert.studentInfo(registerAttempt, "user");

               System.out.println("Roll Number : " + registerAttempt.roll_number + " is registered");
               sendToClient(new ClientPacket("Congratulations,you're registered.You should login to access your account"));

               return true;
           }




        } else if(student.type == Packet.Type.CHAT) {
            ChatPacket chatRequest = (ChatPacket)student;
            System.out.println(chatRequest.from + " tells " + chatRequest.to + " to, ' " + chatRequest.message + " '" );

            //send to specified user
            ClientConnect client = server.findClientConnection(chatRequest.to);

            if(client != null) {
                client.sendToClient(chatRequest);
                return true;
            }

            System.out.println("The client is returning a null value");


        } else if(student.type == Packet.Type.CONNECTED_USERS) {
            sendToClient(new ConnectedUsers(server.connectedUsers));
            return true;
        } else if(student.type == Packet.Type.TIMETABLE) {

            if(Objects.equals(student.auth_code, auth_code) ) {

                if(studentInfo == null) {
                    studentInfo = select.getStudentInfo(roll_number);
                    if(studentInfo != null) {

                        String[] features = RollNumberParser.rollNumberParser(roll_number);
                        int timeTableCode = RollNumberParser.timeTableCodeGen(Integer.parseInt(studentInfo.section), Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(features[2]), features[1]);

                        TimeTable timetable = select.getTimeTable(timeTableCode);
                        sendToClient(timetable);
                    } else {
                        sendToClient(new ClientPacket("Sorry but we need your studentDetails to get Time Table Information"));
                    }

                } else {
                    TimeTable timetable = select.getTimeTable(36519);
                    sendToClient(timetable);
                }
                return true;
            }
        } else if(student.type == Packet.Type.SUBJECTS) {

            Subjects subject = (Subjects)student;
            insert.subjects(subject.subjects,studentInfo.user_id);

            return true;
        } else if(student.type == Packet.Type.ATTENDANCE) {

            Attendance aat = (Attendance)student;
            insert.sendAttendance(aat,select,studentInfo.user_id);
            sendToClient(new ClientPacket("Success !!"));
            return true;
        } else if(student.type == Packet.Type.STUDENT_REQ) {
            StudentInfo sent = select.getStudentInfoz(student.roll_number);
            sendToClient(sent);
        }

        return false;
    }

    private void sendToClient(Packet message) throws IOException {
        output.writeObject(message);
    }




}