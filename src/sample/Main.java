package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private int port = 8080;

    public void start(Stage primaryStage) {
        Network network = new Network(port);
        LobbyManager lm = new LobbyManager(network);

        // Main Scene
        Button startServerBtn = new Button("Start Server");
        Button joinServerBtn = new Button("Join Server");
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.addColumn(0,startServerBtn,joinServerBtn);

        // Client scene
        Button sendRequestBtn = new Button("Send Request");
        TextField requestTextField = new TextField();
        GridPane clientGrid = new GridPane();
        Scene clientScene = new Scene(clientGrid,300,400);
        clientGrid.setAlignment(Pos.CENTER);
        clientGrid.addRow(0,sendRequestBtn, requestTextField);

        // Server Scene
        GridPane serverGrid = new GridPane();
        Scene serverScene = new Scene(serverGrid,300,400);
        serverGrid.addRow(0,new Label("SERVER"));

        startServerBtn.setOnAction(event -> {
            network.startServer(network);
            primaryStage.setScene(serverScene);
            primaryStage.show();
        });

        joinServerBtn.setOnAction(event -> {
           network.connectToServer();
           primaryStage.setScene(clientScene);
           primaryStage.show();
        });

        sendRequestBtn.setOnAction(event -> {
            if (requestTextField.getText() != null) {
                network.sendRequest(requestTextField.getText());
            } else {
                network.sendRequest("genericRequest");
            }
        });

        Scene mainScene = new Scene(mainGrid,300,400);
        primaryStage.setTitle("SushiGo");
        primaryStage.setScene(mainScene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
