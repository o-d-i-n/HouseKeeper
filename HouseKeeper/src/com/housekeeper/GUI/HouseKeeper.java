package com.housekeeper.GUI;/**
 * Created by Lenovo on 4/19/2016.
 */

import com.housekeeper.Client.Client;
import com.housekeeper.Packet.client.Attendance;
import com.housekeeper.Server.ServerMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HouseKeeper extends Application {

    Stage window;
    Button login;
    Button seeConnectedClients;
    Button getSubjects;
    Button getTimeTable;
    Button sendStudentInfo;
    Button sendMessage;
    Client client;
    Scene loginScene;
    Scene home;
    String roll_number;
    Button register;
    TextArea chatBox;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        chatBox = new TextArea();
        chatBox.setText("Chat Messenger \n");
        client = new Client(chatBox);
        Label label1 = new Label("Login");
        TextField username = new TextField("roll_number");
        TextField password = new TextField("password");

        TextField name = new TextField("name");
        TextField branch = new TextField("branch");
        TextField percentage = new TextField("percentage");
        TextField section = new TextField("section");

        sendStudentInfo = new Button();
        sendStudentInfo.setText("Send Student Info");
        login = new Button();
        seeConnectedClients = new Button();
        seeConnectedClients.setText("See Connected Clients");
        TextArea clients = new TextArea();
        //clients.setText();

        getSubjects = new Button();
        getTimeTable = new Button();
        getTimeTable.setText("Get Time table");

        login.setText("Login,grl");

        register = new Button();
        register.setText("Register");
        register.setOnAction(e -> {

            try {
                client.sender.register(username.getText(),password.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        login.setOnAction(e -> {
            try {
                roll_number = username.getText();
                client.sender.login(username.getText(),password.getText());
                window.setTitle(roll_number);
                window.setScene(home);

            } catch (IOException e1) {
                e1.printStackTrace();

            }
            //window.setScene(scene2);
        });

        clients.setText("Nothing as yet");


        getSubjects.setText("Get Subjects");

        getSubjects.setOnAction(e -> {
            String s = "";

            if(client.subjectSet != null) {

                for (int i = 0; i < client.subjectSet.length; i++) {
                    s += client.subjectSet[i] + "\n";
                }
            } else {
                s = "Get Time Table first!!";
            }

            clients.setText(s);
        });

        seeConnectedClients.setOnAction(e -> {
            try {
                client.sender.getTimetable();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String s = "Online Clients \n";

            if(client.connectedUsers.isEmpty()) {
                s = "Sorry, no connected Users as of now";
            }


            for(int i=0;i<client.connectedUsers.size();i++) {

                s += "\n" +  client.connectedUsers.get(i);
            }

            clients.setText(s);

        });



        getTimeTable.setOnAction(e -> {
            String s = "";

            if(client.timetable == null) {
                s = "Please Wait a moment before trying again";
            } else {
                client.timetable.displayTimeTable();
                s = client.timetable.s;
            }
            clients.setText(s);

        });

        Button attendance = new Button();
        attendance.setText("Enter your attendance for the day");

        Button getattendance = new Button();
        getattendance.setText("Get your attendance");

        getattendance.setOnAction(e -> {

            try {
                client.sender.getAttendance();

                String s = "Attendance: \n";

                if(client.att == null) {
                    s = "Please wait a moment before trying again.";
                } else {
                    for(int i =0;i<client.subjectSet.length;i++) {
                        System.out.println(client.subjectSet[i] + "'s ");
                        s += client.subjectSet[i] + "attendance is : " + (client.att.attendance[i]*100)/40 + " % \n";
                    }
                }

                clients.setText(s);

            } catch (IOException e1) {
                e1.printStackTrace();
            }


        });


        VBox layout1 = new VBox();
        layout1.getChildren().addAll(label1,login,register,username,password);
        loginScene = new Scene(layout1,200,300);

        Button getInfo = new Button();
        getInfo.setText("Get Student Info");
        TextField getStudent = new TextField("Enter student's roll number");

        getInfo.setOnAction(e ->{
            try {
                client.sender.getStudentInfo(getStudent.getText());

                String s = "";
                if(client.random_user != null)
                    s = "Name : \n" + client.random_user.name  + "\nSection \n" + client.random_user.section + "\nPercentage \n" + client.random_user.percentage + "\nBranch \n" + client.random_user.branch + " " ;
                clients.setText(s);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        TextField chat_roll_number = new TextField("Send to ?");
        TextField chatMessage = new TextField("Write Message");
        sendMessage = new Button();
        sendMessage.setText("Send message");

        CheckBox one = new CheckBox();
        CheckBox two = new CheckBox();
        CheckBox three = new CheckBox();
        CheckBox four = new CheckBox();
        CheckBox five = new CheckBox();


        VBox something = new VBox();
        something.getChildren().addAll(seeConnectedClients,getSubjects,attendance,getattendance);


        Button sendAttendance = new Button("Send Attendance");
        BorderPane layout2 = new BorderPane();
        layout2.setCenter(something);
        layout2.setLeft(getTimeTable);
        layout2.setRight(clients);
        VBox StudentInfo = new VBox();
        StudentInfo.getChildren().addAll(name,branch,section,percentage,sendStudentInfo,getTimeTable,getStudent,getInfo,sendAttendance);
        layout2.setLeft(StudentInfo);


        attendance.setOnAction(e -> {
            Integer[] attendence = new Integer[client.subjectSet.length];
            int j = 1;
            for(int i=0;i<client.subjectSet.length;i++) {



                if (client.timetable.Monday.get(client.subjectSet[i]) != null) {
                    System.out.println("Yo");
                    if(j == 1) {
                        one.setText((String) client.subjectSet[i]);
                        something.getChildren().add(one);
                        //something.getChildren().add(i,one);
                        //System.out.println("Did you attend  " + client.timetable.Tuesday.get(client.subjectSet[i]) + " classes of " + client.subjectSet[i] + " ? (1/0) ");
                    } else if(j == 2) {
                        two.setText((String) client.subjectSet[i]);
                        something.getChildren().add(two);
                    } else if(j == 3) {
                        three.setText((String) client.subjectSet[i]);
                        something.getChildren().add(three);
                    } else if (j == 4) {
                        four.setText((String) client.subjectSet[i]);
                        something.getChildren().add(four);
                    } else {
                        five.setText((String) client.subjectSet[i]);
                        something.getChildren().add(five);
                    }
                }

                j++;
            }


        });



        sendAttendance.setOnAction(e -> {

            Integer[] attendence = new Integer[client.subjectSet.length];


            for(int i=0;i<client.subjectSet.length;i++) {
                if (client.timetable.Monday.get(client.subjectSet[i]) != null) {
                    System.out.println("Did you attend  " + client.timetable.Monday.get(client.subjectSet[i]) + " classes of " + client.subjectSet[i] + " ? (1/0) ");
                    if (i == 0 && one.isSelected()) {
                        attendence[i] = client.timetable.Monday.get(client.subjectSet[i]);
                    } else if (i == 1 && two.isSelected()) {
                        attendence[i] = client.timetable.Monday.get(client.subjectSet[i]);
                    } else if (i == 2 && three.isSelected()) {
                        attendence[i] = client.timetable.Monday.get(client.subjectSet[i]);
                    } else if (i == 3 && four.isSelected()) {
                        attendence[i] = client.timetable.Monday.get(client.subjectSet[i]);
                    } else if (i == 4 && five.isSelected()) {
                        attendence[i] = client.timetable.Monday.get(client.subjectSet[i]);
                    } else {
                        attendence[i] = 0;
                    }
                } else {
                    attendence[i] = 0;
                }
            }

            for(int i=0;i<attendence.length;i++) {
                System.out.println(attendence[i]);
            }

            try {
                client.sender.sendAttendance(new Attendance(attendence));
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            String s = String.valueOf(one.isSelected());
            clients.setText(s);
        });



        BorderPane chat = new BorderPane();



        chat.setRight(chatBox);
        chat.setLeft(chat_roll_number);
        chat.setTop(chatMessage);
        chat.setCenter(sendMessage);
        layout2.setTop(chat);

        sendMessage.setOnAction(e -> {
            try {
                client.sender.sendChatMessage(chat_roll_number.getText(),chatMessage.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        sendStudentInfo.setOnAction(e -> {
            try {
                client.sender.sendStudentInfo(name.getText(),branch.getText(),Integer.parseInt(percentage.getText()),Integer.parseInt(section.getText()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });



        home = new Scene(layout2,400,400);
        window.setScene(loginScene);
        window.show();

    }
}
