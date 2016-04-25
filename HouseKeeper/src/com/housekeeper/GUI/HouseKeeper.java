package com.housekeeper.GUI;/**
 * Created by Lenovo on 4/19/2016.
 */

import com.housekeeper.Client.Client;
import com.housekeeper.Server.ServerMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        client = new Client();
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
        login.setOnAction(e -> {
            try {

                client.sender.login(username.getText(),password.getText());

                window.setScene(home);

            } catch (IOException e1) {
                e1.printStackTrace();

            }
            //window.setScene(scene2);
        });

        clients.setText("Nothing as yet");
        seeConnectedClients.setOnAction(e -> {
            try {
                client.sender.getTimetable();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String s = "";

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

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(label1,login,username,password);
        loginScene = new Scene(layout1,200,300);

        Button getInfo = new Button();
        TextField getStudent = new TextField("Enter student's roll number");

        getInfo.setOnAction(e ->{
            try {
                client.sender.getStudentInfo(getStudent.getText());

                String s = "";
                s = "Name : \n" + client.random_user.name  + "\nSection \n" + client.random_user.section + "Percentage \n" + client.random_user.percentage + "Branch \n" + client.random_user.branch + " " ;
                clients.setText(s);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        BorderPane layout2 = new BorderPane();
        layout2.setCenter(seeConnectedClients);
        layout2.setLeft(getTimeTable);
        layout2.setRight(clients);
        VBox StudentInfo = new VBox();
        StudentInfo.getChildren().addAll(name,branch,section,percentage,sendStudentInfo,getTimeTable,getInfo,getStudent);
        layout2.setLeft(StudentInfo);




        BorderPane chat = new BorderPane();

        TextArea chatBox = new TextArea();
        TextField chat_roll_number = new TextField("Send to ?");
        sendMessage = new Button();
        sendMessage.setText("Send message");
        chat.setRight(chatBox);
        chat.setLeft(chat_roll_number);
        chat.setCenter(sendMessage);
        layout2.setTop(chat);

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
