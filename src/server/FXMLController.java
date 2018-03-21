package server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class FXMLController implements Initializable {
    private static int screenWidth = 300;
    private static int screenHeight = 500;
    private ActionEvent e_saved;
    private static Stage stage;

    public static FXMLLoader loader;

    public static Network network;

    // URL list (screen locations)
    URL loginScreen = getClass().getResource("scenes/loginScreen.fxml");
    URL serverScreen = getClass().getResource("scenes/serverScreen.fxml");
    URL hostScreen = getClass().getResource("scenes/hostScreen.fxml");
    URL browserScreen = getClass().getResource("scenes/browserScreen.fxml");

    @FXML
    public void entryLogin(ActionEvent e){
        try {
            loadScene(e, loginScreen);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void entryStartServer(ActionEvent e){
        network.startServer(network);
        network.fxmlController = this;
        try {
            loadScene(e,serverScreen);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML private TextField hostLobbyNameText;
    @FXML private PasswordField hostPasswordText;

    @FXML private Button hostCreateLobbyBtn;
    @FXML public void hostCreateLobby(ActionEvent e){
        System.out.println("host attempt");
        network.sendRequest("host");
        network.sendRequest(hostLobbyNameText.getText());
        network.sendRequest(hostPasswordText.getText());
    }

    @FXML private TextField loginNameText;
    @FXML private PasswordField loginPasswordText;
    @FXML public void loginCreateAccount(){
        network.connectToServer();
        network.sendRequest("createAccount");
        network.sendRequest(loginNameText.getText());
        network.sendRequest(loginPasswordText.getText());
    }
    @FXML
    public void loginLogin(){
        network.fxmlController = this;
        network.connectToServer();
        network.sendRequest("login");
        network.sendRequest(loginNameText.getText());
        network.sendRequest(loginPasswordText.getText());

    }

    // Load the scene, get the stage from the button pressed
    @FXML
    public void loadScene(ActionEvent e, URL url) throws IOException {
        Parent root = loader.load(url);
        Scene newScene = new Scene(root, screenWidth, screenHeight);
        Stage s = (Stage)((Node)e.getSource()).getScene().getWindow();
        s.setScene(newScene);
        s.show();

        stage = s;
    }

    // When we call this from other methods, we refer to the saved event
    @FXML
    public void loadScene(URL url) throws IOException {
        Parent root = FXMLLoader.load(url);
        Scene newScene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(newScene);
        stage.show();
    }

    // WELCOME
    public void welcomeCreateLobby(ActionEvent e) {
        try {
            loadScene(e, hostScreen);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void welcomeShowLobby(ActionEvent e) {
        try {
            loadScene(e,browserScreen);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML private TextField serverUserCountField;
    @FXML public void updateUserCount(int i){
        if (serverUserCountField.getText().equals("")){
            serverUserCountField.setText(""+1);
        } else {
            serverUserCountField.setText("" + (Integer.parseInt(serverUserCountField.getText()) + 1));

        }
    }

    @FXML private VBox serverVBox;
    @FXML public AnchorPane serverLobbyTemplate;
    @FXML public void updateServerLobbyDisplay(ArrayList<Lobby> list) {
        ImageView iv = new ImageView(String.valueOf(getClass().getResource("scenes/resources/lock.png")));
        iv.setFitHeight(15);
        iv.setFitWidth(15);
        GridPane gp = new GridPane();

        int i = 0;
        for (Lobby l : list){
            i++;
            gp.setMinWidth(100);
            gp.addRow(i,
                    new Label(l.name),
                    new Label(l.playerCount+"/4"),
                    iv);
        }
        serverVBox.getChildren().add(gp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.fxmlController = this;
    }
}