/**
 * Created by Lenovo on 4/8/2016.
 */

import com.housekeeper.Server.ServerMain;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Server extends Application {

    Button serverStart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("HouseKeeper Server");

        serverStart = new Button();
        serverStart.setText("Start your server,grl");
        serverStart.setOnAction(e -> new ServerMain(9000));

        StackPane layout = new StackPane();
        layout.getChildren().add(serverStart);

        Scene scene = new Scene(layout,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
