package sample.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FXMLController implements Initializable {
    private static int screenWidth = 300;
    private static int screenHeight = 500;
    private ActionEvent e_saved;
    private static Stage stage;

    public static Network network;

    // URL list (screen locations)
    URL loginScreen = getClass().getResource("scenes/loginScreen.fxml");
    URL serverScreen = getClass().getResource("scenes/serverScreen.fxml");
    URL welcomeScreen = getClass().getResource("scenes/welcomeScreen.fxml");

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
        try {
            loadScene(e,serverScreen);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
        Parent root = FXMLLoader.load(url);
        Scene newScene = new Scene(root, screenWidth, screenHeight);
        Stage s = (Stage)((Node)e.getSource()).getScene().getWindow();
        s.setScene(newScene);
        s.show();
        System.out.println(s.getTitle());

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}