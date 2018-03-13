package sample.server;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private int port = 8080;
    private int screenWidth = 400;
    private int screenHeight = 500;

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
        Button hostLobbyBtn = new Button("Host a Lobby");
        Button viewOpenLobbyBtn = new Button("View Open Lobby List");
        TextField requestTextField = new TextField();
        GridPane clientGrid = new GridPane();
        Scene clientScene = new Scene(clientGrid,screenWidth,screenHeight);
        clientGrid.setAlignment(Pos.CENTER);
        clientGrid.addRow(0,sendRequestBtn, requestTextField);
        clientGrid.addRow(1,hostLobbyBtn);
        clientGrid.addRow(2, viewOpenLobbyBtn);

        // Lobby Viewer Scene
        GridPane lobbyViewerGrid = new GridPane();
        Scene lobbyViewerScene = new Scene(lobbyViewerGrid,screenWidth,screenHeight);
        GUI.lobbyViewerGrid = lobbyViewerGrid;

        // Client Create Lobby Scene
        GridPane hostLobbyGrid = new GridPane();
        Scene hostLobbyScene = new Scene(hostLobbyGrid,screenWidth,screenHeight);
        Button openLobbyBtn = new Button("Open Lobby");
        Label lobbyNameLabel = new Label("Lobby Name: ");
        Label lobbyPassLabel = new Label("Lobby Password: ");
        TextField lobbyNameText = new TextField();
        TextField lobbyPassText = new PasswordField();
        hostLobbyGrid.addRow(0,lobbyNameLabel,lobbyNameText);
        hostLobbyGrid.addRow(1,lobbyPassLabel,lobbyPassText);
        hostLobbyGrid.addRow(2,openLobbyBtn);

        // Server Scene
        GridPane serverGrid = new GridPane();
        Scene serverScene = new Scene(serverGrid,screenWidth,screenHeight);
        serverGrid.addRow(0,new Label("SERVER"));

        hostLobbyBtn.setOnAction(event -> {
            primaryStage.setTitle("Host a Lobby");
            primaryStage.setScene(hostLobbyScene);
            primaryStage.show();
        });

        viewOpenLobbyBtn.setOnAction(event -> {
            network.connectToServer();
            network.sendRequest("showLobbyList");

            primaryStage.setTitle("View Open Lobbies");
            primaryStage.setScene(lobbyViewerScene);
            primaryStage.show();
        });

        joinServerBtn.setOnAction(event -> {
           network.connectToServer();
           primaryStage.setTitle("Client");
           primaryStage.setScene(clientScene);
           primaryStage.show();
        });

        openLobbyBtn.setOnAction(event -> {
            network.sendRequest("host");
            network.sendRequest(lobbyNameText.getText());
            network.sendRequest(lobbyPassText.getText());
            System.out.println("Sent request to host lobby ["+lobbyNameText.getText()
                    +":"+lobbyPassText.getText()+"]");
        });

        sendRequestBtn.setOnAction(event -> {
            if (requestTextField.getText() != null) {
                network.sendRequest(requestTextField.getText());
            } else {
                network.sendRequest("genericRequest");
            }
        });

        startServerBtn.setOnAction(event -> {
            network.startServer(network);
            primaryStage.setTitle("Server");
            primaryStage.setScene(serverScene);
            primaryStage.show();
        });


        Scene mainScene = new Scene(mainGrid,screenWidth,screenHeight);
        primaryStage.setTitle("SushiGo");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        // Add GUI components
        GUI.serverGrid = serverGrid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
