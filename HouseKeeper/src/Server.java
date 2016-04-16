/**
 * Created by Lenovo on 4/8/2016.
 */

import com.housekeeper.Server.ServerMain;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Server extends Application {

    Button serverStart,serverStop;
    Stage window;
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

        serverStart = new Button();
        serverStart.setText("Start your server,grl");
        serverStart.setOnAction(e -> {
            server = new ServerMain(9000);
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
