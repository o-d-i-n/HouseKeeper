package com.housekeeper.Client;

import com.housekeeper.Packet.Packet;
import com.housekeeper.Packet.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private void cli() {

        System.out.println("Welcome to The HouseKeeper.You have the following options:");
        System.out.println("1. Register");
        System.out.println("2. Login (You need to register to try this step out)");
        System.out.println("3. Share Your Info (You need to login before trying this step out)");
        System.out.println("4. See connected Users");
        System.out.println("5. Send a private message ");
        System.out.println("6. Get Time Table ");
        System.out.println("7. Send subject Set");
        System.out.println("8. Send In Attendance");


        option = inputLine.nextInt();
    }

    public void run() {
        while(client.running) {
            cli();
            try {

                if (option == 1) {
                    client.output.writeObject(sendRegistration());
                } else if (option == 2) {
                    client.output.writeObject(sendLoginRequest());
                } else if (option == 3) {
                    //client.output.writeObject(sendStudentInfo());
                } else if(option == 4) {
                    displayConnectedUsers();
                } else if(option == 5) {
                    client.output.writeObject(sendMessage());
                } else if(option == 6) {
                    if(client.timetable == null) {
                        client.output.writeObject(sendTimeTableReq());
                    } else {
                        client.timetable.displayTimeTable();
                    }
                } else if(option == 7) {
                    if(client.subjectSet != null) {
                        client.output.writeObject(sendSubjectSet());
                    } else {
                        System.out.println("Please get your timetable first,before pushing subjects in");
                    }
                } else if(option == 8) {

                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);



                    /*while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        System.out.println(pair.getKey() + " = " + pair.getValue());

                        it.remove(); // avoids a ConcurrentModificationException
                    }*/
                    Integer[] attendence = new Integer[client.subjectSet.length];

                    for(int i=0;i<client.subjectSet.length;i++) {
                        if(client.timetable.Tuesday.get(client.subjectSet[i]) != null) {
                            System.out.println("Did you attend  "+ client.timetable.Tuesday.get(client.subjectSet[i]) +" classes of " +client.subjectSet[i] + " ? (1/0) ");
                            if (inputLine.nextInt() == 1) {

                                attendence[i] = client.timetable.Tuesday.get(client.subjectSet[i]);
                            } else {
                                attendence[i] = 0;
                            }
                        } else {
                            attendence[i] = 0;
                        }
                    }

                    for(int i = 0;i<attendence.length;i++) {
                        System.out.println(attendence[i]);
                    }

                    client.output.writeObject(sendAttendanceList(attendence));


                }
            } catch (IOException e) {

                // close connections gracefully
            }
        }
    }

    public void getTimetable() throws IOException {
        if(client.timetable == null) {
            client.output.writeObject(sendTimeTableReq());
        } else {
            client.timetable.displayTimeTable();
        }
    }

    public void login(String roll_number,String password) throws IOException {
        client.roll_number = roll_number;
        client.output.writeObject(new StudentLogin(roll_number,password));
    }

    private Attendance sendAttendanceList(Integer[] attendance) {
        return new Attendance(attendance);
    }

    private Subjects sendSubjectSet() {
        return new Subjects(client.subjectSet);
    }

    private TimeTable sendTimeTableReq() {
        return new TimeTable(client.auth_code);
    }

    private ChatPacket sendMessage() {
        System.out.println("Enter the roll_number of the reciepient");
        inputLine.nextLine();
        String to = inputLine.nextLine();
        System.out.println("Enter message: ");
        String message = inputLine.nextLine();
        ChatPacket chatMessage = new ChatPacket(to,client.roll_number,message);
        return chatMessage;
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
        client.roll_number = roll_number;
        System.out.println("Enter Password");
        String password = inputLine.nextLine();


        StudentRegister registerAttempt = new StudentRegister(roll_number,password);
        return registerAttempt;
    }


    public void sendStudentInfo(String name,String branch,int percentage,int section) throws IOException {

        StudentInfo student = new StudentInfo(Packet.Type.STUDENT_INFO,name,branch,percentage,section,client.auth_code,client.roll_number);

        client.output.writeObject(student);
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
            System.out.println(i+1 + ". " + client.connectedUsers.get(i));
        }
    }

    public void getStudentInfo(String text) throws IOException {
        client.output.writeObject(new StudentReq(text));
    }

    public void sendChatMessage(String to_roll_number,String text) throws IOException {
        client.output.writeObject(new ChatPacket(to_roll_number,client.roll_number,text));
    }
}
