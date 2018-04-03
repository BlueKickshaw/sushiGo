package server;

import Game.Deck;
import javafx.application.Platform;
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
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {
    private static int screenWidth = 300;
    private static int screenHeight = 500;
    private static Stage stage;

    public static FXMLLoader loader;

    public static Network network;

    // URL list (screen locations)
    URL loginScreen = getClass().getResource("serverScenes/loginScreen.fxml");
    URL serverScreen = getClass().getResource("serverScenes/serverScreen.fxml");
    URL hostScreen = getClass().getResource("serverScenes/hostScreen.fxml");
    URL browserScreen = getClass().getResource("serverScenes/browserScreen.fxml");
    URL hostLobbyScreen = getClass().getResource("serverScenes/hostLobbyScreen.fxml");

    @FXML
    // The user presses a button indicating they want to go back to the welcome screen
    private void toWelcomeBtn(ActionEvent e){
        URL url = getClass().getResource("serverScenes/welcomeScreen.fxml");
        try {
            loadScene(e, url);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    private void endServer(){
        for (Map.Entry<String,Client> e : network.clientConnectionManager.clients.entrySet()) {
            if (e.getValue().getSocket().isConnected()) {
                network.sendRequest(e.getValue().getSocket(), "serverShutdown".getBytes());
                System.exit(0);
            }
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
            ((Stage) ((Node) e.getSource()).getScene().getWindow()).setTitle("Server");
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
        ((Stage) ((Node) e.getSource()).getScene().getWindow()).setTitle("Host");
        network.sendRequest("host");
        network.sendRequest(hostLobbyNameText.getText());
        network.sendRequest(hostPasswordText.getText());
        network.sendRequest(network.username);  // We need to tell the server who we are
    }
    @FXML public void hostMigrate(ActionEvent e){
        // Find out what port we can use
        network.port = network.getOpenPort();

        // Tell the server we're going to branch off and become our own server
        network.sendRequest("migrate");
        network.sendRequest(network.username);
        network.sendRequest("" + network.port);

        // Send the lobby information to the users
        network.sendRequest(network.serializeObject(network.client.getLobby()));

        // Disconnect from the server
        network.sendRequest("disconnect");

        // Become the new server, using our new assigned port
        network.startServer(network);

        // Everyone in the lobby
        network.sendToLobby("startGame".getBytes());
        startGame(network.client.getLobby());
    }

    @FXML public void hostTest(ActionEvent e){
        URL url = Deck.class.getResource("table/gameTable.fxml");
        try {
            loadScene(e,url);
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

    @FXML private TextField loginIPField;
    @FXML
    public void loginLogin(){
        network.fxmlController = this;

        if (!loginIPField.getText().equals("localhost")) {
            System.out.println("Connecting to something other than localhost");
            network.setServerAddress(loginIPField.getText());
        }

        if (!network.isAValidIP(loginIPField.getText())){
            new Alert(Alert.AlertType.INFORMATION,
                    "Sorry, we're expecting the form 'localhost' or 'x.x.x.x'!",
                    ButtonType.OK).showAndWait();
            System.exit(0);
        }
        network.connectToServer();

        network.username = loginNameText.getText();
        network.sendRequest("login");
        network.sendRequest(loginNameText.getText());
        network.sendRequest(loginPasswordText.getText());

        network.client = new Client(network.socket,network.username);
    }

    @FXML
    public void loadLobbyScene(){
        stage.setTitle("Player");
        try {
            loadScene(hostLobbyScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the scene, get the stage from the button pressed; we return the root if we need to add
    // elements in certain instances
    @FXML
    public Parent loadScene(ActionEvent e, URL url) throws IOException {
        Parent root = loader.load(url);
        Scene newScene = new Scene(root, screenWidth, screenHeight);
        Stage s = (Stage)((Node)e.getSource()).getScene().getWindow();
        s.setScene(newScene);
        s.show();

        stage = s;
        return root;
    }

    @FXML
    public void loadScene(URL url, int width, int height) throws IOException {
        Parent root = loader.load(url);
        Scene newScene = new Scene(root, width, height);

        stage.setScene(newScene);
        stage.show();
    }

    // When we call this from other methods, we refer to the saved event
    @FXML
    public Parent loadScene(URL url) throws IOException {
        Parent root = loader.load(url);
        Scene newScene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(newScene);
        stage.show();

        return root;
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

    @FXML public void startGame(Lobby lobby) {

        // Depending on how many players we have, we'l need to load a different scene
        URL gameScene = null;
        if (lobby.playerCount <= 1) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR,"No other players, we're going to wait!",
                        ButtonType.OK).show();
            });
            return;
        }
        switch (lobby.playerCount) {
            case 2:
                gameScene = Deck.class.getResource("gameScenes/2PlayerGame.fxml");
                break;
            case 3:
                gameScene = Deck.class.getResource("gameScenes/3PlayerGame.fxml");
                break;
            case 4:
                gameScene = Deck.class.getResource("gameScenes/4PlayerGame.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR,"Invalid amount of players!", ButtonType.OK).show();
                break;
        }

        try {
            System.out.println("loading: "+gameScene.toString());
            loadScene(gameScene,1100,700);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    @FXML private GridPane lobbyPlayerGrid;
    @FXML public void updatePlayerGrid(Lobby lobby){
        // Update what out active lobby is

        if (lobbyPlayerGrid == null){
            // This will get happen when the player joins the server; not when another player joins. They need
            // time to load the scene since JavaFX is slower than the server is
            new Thread(() -> {
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> network.fxmlController.updatePlayerGrid(lobby));
            }).start();
            System.err.println("Updating the grid before the scene has loaded");
            return;
        }

        hostLobbyNameText.setText(lobby.name);
        // Update our current lobby
        network.client.setLobby(lobby);
        lobbyPlayerGrid.getChildren().clear();

        int i = 0;
        for (String s : lobby.playerNames){
            if (s.equals(network.username)){
                // Update our clients reference
                s += " (You)";
                network.client.setPlayerNumber(i+1);
            }
            Color playerColor;
            switch (i) {
                case 0:
                    playerColor = Color.RED;
                    break;
                case 1:
                    playerColor = Color.BLUE;
                    break;
                case 2:
                    playerColor = Color.CYAN;
                    break;
                case 3:
                    playerColor = Color.PURPLE;
                    break;
                default:
                    playerColor = Color.BLACK;
            }


            Label label = new Label(s);
            label.setTextFill(playerColor);
            Label number = new Label(""+(i+1));
            number.setTextFill(Color.WHITESMOKE);
            lobbyPlayerGrid.addRow(i,number,label);
            i++;
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
            ImageView iv = new ImageView(String.valueOf(getClass().getResource("serverScenes/resources/lock.png")));

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
                    network.sendRequest(network.username);
                    network.client.setLobby(l);
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