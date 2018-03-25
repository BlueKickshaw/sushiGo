package server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class FXMLController implements Initializable {
    private static int screenWidth = 300;
    private static int screenHeight = 500;
    private static Stage stage;

    public static FXMLLoader loader;

    public static Network network;

    // URL list (screen locations)
    URL loginScreen = getClass().getResource("scenes/loginScreen.fxml");
    URL serverScreen = getClass().getResource("scenes/serverScreen.fxml");
    URL hostScreen = getClass().getResource("scenes/hostScreen.fxml");
    URL browserScreen = getClass().getResource("scenes/browserScreen.fxml");


    @FXML
    // The user presses a button indicating they want to go back to the welcome screen
    private void toWelcomeBtn(ActionEvent e){
        URL url = getClass().getResource("scenes/welcomeScreen.fxml");
        try {
            loadScene(e, url);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

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

        network.username = loginNameText.getText();
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

    public String promptForPassword(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Lobby Password");
        dialog.setHeaderText("This lobby requires a password, please enter the password below");

        GridPane gp = new GridPane();
        Label label = new Label("Password: ");
        TextField password = new PasswordField();

        gp.addRow(0,label,password);
        dialog.getDialogPane().setContent(gp);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setResultConverter(s -> password.getText());

        return dialog.showAndWait().get();
    }
    @FXML private Button hostStartLobbyBtn;
    @FXML public void removeStartLobbyBtn(){
        hostStartLobbyBtn.setDisable(true);
    }

    @FXML private GridPane hostPlayerGridPane;
    @FXML public void updatePlayerGrid(Lobby lobby){
        int i = 0;
        for (String name : lobby.playerNames) {
            hostPlayerGridPane.addRow(i++,new Label(name));
        }
    }


    @FXML private TextField serverUserCountField;
    @FXML public void updateUserCount(int i){
        if (serverUserCountField.getText().equals("")){
            serverUserCountField.setText(""+i);
        } else {
            serverUserCountField.setText("" + (Integer.parseInt(serverUserCountField.getText()) + i));

        }
    }

    @FXML private GridPane serverGridPane;
    @FXML private GridPane browserGridPane;
    @FXML public void updateServerLobbyDisplay(ArrayList<Lobby> list, boolean isServer) {
        // Clear all the previous entries
        if (isServer) {
            serverGridPane.getChildren().remove(0, serverGridPane.getChildren().size());
        } else {
            browserGridPane.getChildren().remove(0, browserGridPane.getChildren().size());
        }

        int i = 0;
        for (Lobby l : list){
            // Create a new imageview; cannot reuse it every time because we get duplicate node errors
            ImageView iv = new ImageView(String.valueOf(getClass().getResource("scenes/resources/lock.png")));

            iv.setFitHeight(15);
            iv.setFitWidth(15);

            // If there's no password, we want to hide the lock graphic
            if (!l.hasPassword) {
                iv.setVisible(false);
            }

            Label name = new Label (l.name);
            name.setTextFill(Color.WHITESMOKE);

            Label playerCount = new Label(l.playerCount+"/4");
            playerCount.setTextFill(Color.WHITESMOKE);


            // We want to change whether or not the join button is shown to prevent the server from
            // throwing some wacky errors
            if (isServer) {
                serverGridPane.addRow(
                        i,
                        name,
                        playerCount,
                        iv);
            } else {

                // Create and add the button which will ask us to join the game
                Button join = new Button("Join");
                join.setOnAction(e -> {
                    String password = promptForPassword();
                    network.sendRequest("joinLobby");
                    network.sendRequest(l.name);
                    network.sendRequest(password);
                    // We want to tell the server who's joining as well
                    network.sendRequest(network.username);
                });

                browserGridPane.addRow(
                        i,
                        name,
                        playerCount,
                        iv,
                        join);
            }

            i++;
        }
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
            // Request the lobby list
            network.sendRequest("showLobbyList");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.fxmlController = this;
    }
}