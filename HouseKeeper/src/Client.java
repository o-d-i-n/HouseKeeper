/**
 * Created by Lenovo on 4/16/2016.
 */

import com.housekeeper.Server.ServerMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Client extends Application {

    Button login,register;
    Stage window;
    com.housekeeper.Client.Client client;
    Scene scene1,scene2;
    ServerMain server;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        Label label1 = new Label("Start the server");
        Label label2 = new Label("Stop the server");
        client = new com.housekeeper.Client.Client();
        login = new Button();
        login.setText("Start your server,grl");
        login.setOnAction(e -> {
            client.sender.
            window.setScene(scene2);
        });

        StackPane layout1 = new StackPane();
        layout1.getChildren().addAll(label1,serverStart);
        scene1 = new Scene(layout1,200,200);


        serverStop = new Button();
        serverStop.setText("Stop your server");
        serverStop.setOnAction(e -> {
            server.server.stop();
            window.setScene(scene1);
        });

        StackPane layout2 = new StackPane();
        layout2.getChildren().addAll(label2,serverStop);
        scene2 = new Scene(layout2,200,200);

        window.setScene(scene1);
        window.show();

    }
}
