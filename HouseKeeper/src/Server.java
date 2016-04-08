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

public class Server extends Application implements EventHandler<ActionEvent> {

    Button button;

    public static void main(String[] args) {
        launch(args);

       // int port;
       // port = 9000;
       // new ServerMain(port);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("HouseKeeper Server");

        button = new Button();
        button.setText("Start your server,grl");
        button.setOnAction(this);

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == button) {
            new ServerMain(9000);
        }
    }
}
