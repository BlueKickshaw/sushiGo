package server;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class RequestManager {
    Socket socket;
    Network network;

    public RequestManager(Network network, Socket socket){
        this.network = network;
        this.socket = socket;
    }

    public void handleRequest(Socket socket, byte[] request){
        System.out.println(socket.getInetAddress().toString() + ": received ['"+new String(request)+"']");
        String message = new String(request);
        switch (message) {
            case "accountCreationFailed":
                int error = Integer.parseInt(network.getNextString(socket));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (error == -2) {
                            new Alert(Alert.AlertType.ERROR,"Name already taken!", ButtonType.OK).show();
                        } else {
                            new Alert(Alert.AlertType.ERROR,"Name and password must be alphanumeric!",
                                    ButtonType.OK).show();
                        }
                    }
                });
                break;

            case "createAccount":
                String name = network.getNextString(socket);
                String password = network.getNextString(socket);
                error = network.server.accounts.createAccount(name,password);
                if (error != 0) {
                    network.sendRequest(socket, "accountCreationFailed".getBytes());
                    network.sendRequest(socket, new String("" + error).getBytes());
                }
                break;

            case "host":
                name = network.getNextString(socket);
                password = network.getNextString(socket);
                network.lobbyManager.createLobby(socket.getInetAddress().toString(),name,password);
                GUI.UpdateLobbyGrid(GUI.serverGrid,network.lobbyManager);
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
                GUI.UpdateLobbyGrid(GUI.lobbyViewerGrid,network.lobbyManager);
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
