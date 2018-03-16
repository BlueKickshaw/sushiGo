package sample.server;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class RequestManager {
    Network network;

    public RequestManager(Network network, Socket socket){
        this.network = network;
    }

    public void handleRequest(Socket socket, byte[] request){
        System.out.println(socket.getInetAddress().toString() + ": received ['"+new String(request)+"']");
        String message = new String(request);
        switch (message) {
            case "accountCreationFailed":
                int error = Integer.parseInt(network.getNextString(socket));
                Platform.runLater(() -> {
                    if (error == -2) {
                        new Alert(Alert.AlertType.ERROR,"Name already taken!", ButtonType.OK).show();
                    } else {
                        new Alert(Alert.AlertType.ERROR,"Name and password must be alphanumeric!",
                                ButtonType.OK).show();
                    }
                });
                break;

            case "createAccount":
                String name = network.getNextString(socket);
                String password = network.getNextString(socket);
                error = network.server.accounts.createAccount(name,password);
                if (error != 0) {
                    network.sendRequest(socket, "accountCreationFailed".getBytes());
                    network.sendRequest(socket, ("" + error).getBytes());
                }
                break;

            case "disconnect":
                Platform.runLater(() -> {
                    Network.fxmlController.updateUserCount(-1);
                    System.out.println(Thread.currentThread().getName()+": Disconnecting "+
                            socket.getInetAddress().toString());
                });
                break;

            case "host":
                name = network.getNextString(socket);
                password = network.getNextString(socket);
                Lobby l = network.lobbyManager.createLobby(socket.getInetAddress().toString(),name,password);
                if (l != null) {
                    network.sendRequest(socket,"hostSuccess".getBytes());
                    network.fxmlController.updateServerLobbyDisplay(network.lobbyManager.lobbyList);
                } else {
                    network.sendRequest(socket,"hostFailed".getBytes());
                }
                break;

            case "hostSuccess":
                URL url = getClass().getResource("scenes/hostLobbyScreen.fxml");
                Platform.runLater(() -> {
                    try {
                        Network.fxmlController.loadScene(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;

            case "hostFailed":
                new Alert(Alert.AlertType.ERROR,
                        "Failed to host lobby; Perhaps the name is taken?",
                        ButtonType.OK);
                break;

            case "joinLobby":
                String lobbyName = network.getNextString(socket);
                System.out.println("Requesting to join lobby '"+lobbyName+"'");
                break;

            case "login":
                name = network.getNextString(socket);
                password = network.getNextString(socket);
                boolean success = network.server.accounts.verifyAccount(name,password);
                if (success) {
                    network.sendRequest(socket,"loginSuccessful".getBytes());
                    Platform.runLater(() -> Network.fxmlController.updateUserCount(1));
                } else {
                    network.sendRequest(socket,"loginFailed".getBytes());
                }
                break;

            case "loginFailed":
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new Alert(Alert.AlertType.ERROR,"Login failed; the username/password combination" +
                                "could not be found in the database",ButtonType.OK);
                    }
                });
                break;

            case "loginSuccessful":
                // TODO: Send the user to the welcome page
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        URL url = getClass().getResource("scenes/welcomeScreen.fxml");
                        try {
                            network.fxmlController.loadScene(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            case "receivedLobbyList":
                network.lobbyManager.lobbyList =
                        (ArrayList<Lobby>)network.deserializeObject(network.getNextBytes(socket));
                break;

            case "showLobbyList":
                System.out.println("Serializing/sending lobby list");
                network.sendRequest(socket, "receivedLobbyList".getBytes());
                network.sendRequest(socket, network.serializeObject(network.lobbyManager.lobbyList));
                break;

            default:
                System.out.println(socket.getInetAddress().toString()+": illegal request");
                try {
                    socket.getInputStream().close();
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Unable to close socket; already closed?");
                }
                break;
        }
    }
}
