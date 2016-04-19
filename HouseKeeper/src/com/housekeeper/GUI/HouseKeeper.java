package com.housekeeper.GUI;/**
 * Created by Lenovo on 4/19/2016.
 */

import com.housekeeper.Client.Client;
import com.housekeeper.Server.ServerMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HouseKeeper extends Application {

    Stage window;
    Button login;
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

        login = new Button();
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

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(label1,login,username,password);
        loginScene = new Scene(layout1,200,300);


        StackPane layout2 = new StackPane();
        Text message = new Text("You've logged in !!");
        layout2.getChildren().addAll(message);
        home = new Scene(layout2,400,400);


        window.setScene(loginScene);
        window.show();

    }
}
