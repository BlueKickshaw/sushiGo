package server;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

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

            // When we want to disconnect a user from the server (booting them when their lobby starts for example)
            case "disconnect":
                Platform.runLater(() -> {
                    Network.fxmlController.updateUserCount(-1);
                    System.out.println(Thread.currentThread().getName()+": Disconnecting "+
                            socket.getInetAddress().toString());
                });
                break;

            // Received a request to host a lobby
            case "host":
                name = network.getNextString(socket);
                password = network.getNextString(socket);
                Lobby l = network.lobbyManager.createLobby(socket.getInetAddress().toString(),name,password);
                if (l != null) {
                    network.sendRequest(socket,"hostSuccess".getBytes());

                    // We need to run this on the main thread where the FXML loader resides, that's why
                    // we use runLater
                    Platform.runLater(() ->
                            network.fxmlController.updateServerLobbyDisplay(network.lobbyManager.lobbyList,
                                    true));
                } else {
                    network.sendRequest(socket,"hostFailed".getBytes());
                }
                break;

            case "hostSuccess": {
                    URL url = getClass().getResource("scenes/hostLobbyScreen.fxml");
                    Platform.runLater(() -> {
                        try {
                            Network.fxmlController.loadScene(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                break;

            case "hostFailed":
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR,
                            "Failed to host lobby; Perhaps the name is taken?",
                            ButtonType.OK).show();
                });
                break;

            // Received a request to join a lobby
            case "joinLobby": {
                String lobbyName = network.getNextString(socket);
                String lobbyPassword = network.getNextString(socket);
                System.out.println("User requesting to join lobby '" + lobbyName + ":" + lobbyPassword + "'");
                boolean success = false;

                for (Lobby lobby : network.lobbyManager.lobbyList) {
                    if (lobbyName.equals(lobby.name)) {
                        if (lobbyPassword.equals(lobby.password)) {
                            // The user successfully joins the lobby, we need to apply the necessary logic
                            success = true;
                            lobby.playerCount++;
                            lobby.ipList.add(socket.getInetAddress().toString());
                            String username = network.getNextString(socket);
                            lobby.playerNames.add(username);
                            network.clientConnectionManager.lobbies.put(lobby,username);

                            // We attempt to send a message to everyone in the lobby now... here goes:
                                for (String player : lobby.playerNames){
                                    network.sendRequest(
                                            network.clientConnectionManager.clients.get(player),
                                            "testCase".getBytes()
                                    );
                                }
                            break;
                        }
                        break;
                    }
                }

                if (success) {
                    network.sendRequest(socket,"joinSuccessful".getBytes());
                } else {
                    network.sendRequest(socket,"joinFailed".getBytes());
                }
            } break;

            case "testCase": {
                System.out.println("TESTCASE");
                break;
            }


            case "joinFailed":
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR,"Unable to join lobby; perhaps it's full" +
                            " or the password was incorrect?",ButtonType.OK).show();
                });
                break;

            case "joinSuccessful": {
                URL url = getClass().getResource("scenes/hostLobbyScreen.fxml");
                Platform.runLater(() -> {
                    try {
                        Network.fxmlController.loadScene(url);
                        Network.fxmlController.removeStartLobbyBtn();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

                break;

            // Received a request to login
            case "login":
                name = network.getNextString(socket);
                password = network.getNextString(socket);
                boolean success = network.server.accounts.verifyAccount(name,password);
                if (success) {
                    network.sendRequest(socket,"loginSuccessful".getBytes());
                    network.clientConnectionManager.clients.put(name,socket);
                    Platform.runLater(() -> Network.fxmlController.updateUserCount(1));
                } else {
                    network.sendRequest(socket,"loginFailed".getBytes());
                }
                break;

            case "loginFailed":
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR,"Login failed; the " +
                        "username/password combination could not be found in the database",ButtonType.OK));
                break;

            case "loginSuccessful":
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
                Platform.runLater(() -> network.fxmlController.updateServerLobbyDisplay(network.lobbyManager.lobbyList,false));
                break;

            case "showLobbyList":
                System.out.println("Serializing/sending lobby list");
                network.sendRequest(socket, "receivedLobbyList".getBytes());
                network.sendRequest(socket, network.serializeObject(network.lobbyManager.lobbyList));
                break;

            case "updateLobbyPlayers": {
                Lobby lobby = (Lobby)network.deserializeObject(network.getNextBytes(socket));
                Platform.runLater(() -> network.fxmlController.updatePlayerGrid(lobby));
                break;
            }

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
