package server;

import Cards.Card;
import Game.Hand;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

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

            case "accountCreationSucceeded": {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.CONFIRMATION,"Account created!",ButtonType.OK).show();
                });
                break;
            }

            case "addClient": {
                String name = network.getNextString(socket);
                System.out.println("ADDING TO CCM: "+name);
                Client client = new Client(socket, name);
                client.setPlayerNumber(Integer.parseInt(network.getNextString(socket)));
                client.setLobby((Lobby)network.deserializeObject(network.getNextBytes(socket)));
                client.setSocket(socket);
                network.clientConnectionManager.clients.put(name,client);
                break;
            }

            case "connectToHost": {
                InetAddress address = (InetAddress)network.deserializeObject(network.getNextBytes(socket));
                if (address.isAnyLocalAddress()){
                    network.setServerAddress("192.168.2.17");
                }
                int newPort = Integer.parseInt(network.getNextString(socket));
                // From here we're safe to disconnect
                network.sendRequest("disconnect");

                try {
                    network.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // We can get our lobby from here
                InetAddress inetAddress = address;
                network.setServerAddress(network.client.getLobby().host.getHostAddress());
                network.port = newPort;
                network.connectToServer();

            }
                break;

            case "createAccount":
                String name = network.getNextString(socket);
                String password = network.getNextString(socket);
                error = network.server.accounts.createAccount(name,password);
                if (error != 0) {
                    network.sendRequest(socket, "accountCreationFailed".getBytes());
                    network.sendRequest(socket, ("" + error).getBytes());
                } else {
                    network.sendRequest(socket, "accountCreationSucceeded".getBytes());
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

            case "endTurn": {
                // turn name card hand
                String playerName = network.getNextString(socket);
                int playerNumber = Integer.parseInt(network.getNextString(socket));
                System.out.println("RECEIVED PLAYER NUMBER: "+playerNumber);
                Hand playedHand = (Hand)network.deserializeObject(network.getNextBytes(socket));
                Hand rotateHand = (Hand)network.deserializeObject(network.getNextBytes(socket));
                network.gameDriver.passedCards++;

                System.out.println(playerName+": ["+network.gameDriver.passedCards+"/" +
                        +(network.client.getLobby().playerCount-1)+"] "+playedHand);

                int ind = 0;
                for (int i = 0; i < network.gameDriver.storedPlayerNames.size(); i++) {
                   if (network.gameDriver.storedPlayerNames.equals(playerName)){
                       ind = i;
                   }
                }

                network.gameDriver.storedPlayerNames.add(ind, playerName);
                network.gameDriver.storedPlayerNumbers.add(ind, playerNumber);
                network.gameDriver.storedPlayedHands.add(ind, playedHand);
                network.gameDriver.storedRotateHands.add(ind, rotateHand);

                // We don't want to wait for data from the host, so we subtract one
                if (network.gameDriver.passedCards == (network.client.getLobby().playerCount-1)) {
                    while (!network.gameDriver.hostTurnEnded){}

                    // We have everyone's card
                    for (int i = 1; i < network.client.getLobby().playerCount; i++) {
                        network.sendToPlayer(i,"endOfTurnData".getBytes());
                        if (i != 1) {
                            network.sendToPlayer(i, network.serializeObject(
                                    network.gameDriver.storedRotateHands.get(i - 2)
                            ));
                        } else {
                            network.sendToPlayer(i,network.serializeObject(
                                    network.gameDriver.headPlayer.getRotatingHand()
                            ));
                        }
                    }

                    Map<Integer,Hand> tree = new TreeMap<>();
                    for (int i = 0; i < network.gameDriver.storedPlayedHands.size(); i++) {
                        System.out.println("PUTTING: "+
                        network.gameDriver.storedPlayerNumbers.get(i)+" "+
                        network.gameDriver.storedPlayedHands.get(i));
                        tree.put(
                                network.gameDriver.storedPlayerNumbers.get(i),
                                network.gameDriver.storedPlayedHands.get(i));
                    } // This sorts the hands by player name




                    // Send EVERYONE the hand data
                    Vector<Hand> handVector = new Vector<>();
                    for (Map.Entry<Integer, Hand> entry : tree.entrySet()) {
                        handVector.add(entry.getValue());
                    }

                    // Need to send the host's *played* hand
                    handVector.add(0,network.gameDriver.headPlayer.getHand());
                    network.sendToLobby(network.serializeObject(handVector));

                    //
                    for (Hand hand : handVector) {
                        System.out.println("V:"+hand.toString());
                    }

                    // Update the host's hand
                    System.out.println(network.gameDriver.storedRotateHands.get(
                            network.gameDriver.storedRotateHands.size()-1));
                    network.gameDriver.receiveEndOfTurnData(
                            handVector,
                            // Get the last player in the games hand [-2, we also aren't in this list]
                            network.gameDriver.storedRotateHands.get(network.gameDriver.storedRotateHands.size() -1));
                    network.gameDriver.storedPlayerNames.clear();
                    network.gameDriver.storedRotateHands.clear();
                    network.gameDriver.storedPlayedHands.clear();
                    network.gameDriver.passedCards = 0;
                }

                break;
            }

            case "endOfTurnData": {
                Hand newHand = (Hand)network.deserializeObject(network.getNextBytes(socket));
                System.out.println("PASSED HAND: "+newHand.toString());
                Vector<Hand> allCards = (Vector<Hand>)network.deserializeObject(network.getNextBytes(socket));
                Platform.runLater(() -> {
                    network.gameDriver.receiveEndOfTurnData(allCards,newHand);
                });
                break;
            }

            // Received a request to host a lobby
            case "host": {
                    String lobbyName = network.getNextString(socket);
                    password = network.getNextString(socket);
                    Lobby l = network.lobbyManager.createLobby(socket.getInetAddress(), lobbyName, password);
                    String username = network.getNextString(socket);
                    l.playerNames.add(username);
                    l.ipList.add(socket.getInetAddress().toString());
                    l.host = socket.getInetAddress();
                    if (l != null) {
                        network.sendRequest(socket, "hostSuccess".getBytes());

                        // We need to run this on the main thread where the FXML loader resides, that's why
                        // we use runLater
                        Platform.runLater(() ->
                                network.fxmlController.updateServerLobbyDisplay(network.lobbyManager.lobbyList,
                                        true));
                    } else {
                        network.sendRequest(socket, "hostFailed".getBytes());
                    }
                }
                break;
            case "endHostTurn": {
                network.gameDriver.hostTurnEnded = true;
                break;
            }

            case "hostSuccess": {
                    URL url = getClass().getResource("serverScenes/hostLobbyScreen.fxml");
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
                network.sendRequest(socket,"joinSuccessful".getBytes());
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

                            // Update the clients lobby
                            Client client = network.clientConnectionManager.clients.get(username);
                            network.clientConnectionManager.clients.remove(client);
                            client.setLobby(lobby);
                            network.clientConnectionManager.clients.put(username,client); // is there an easier way?

                            // We send a message to everyone in the lobby
                                for (String player : lobby.playerNames){
                                    Socket clientSocket =
                                            network.clientConnectionManager.clients.get(player).getSocket();
                                    network.sendRequest(clientSocket,"updateLobbyPlayers".getBytes());
                                    network.sendRequest(clientSocket,
                                            network.serializeObject(lobby));
                                }
                            break;
                        }
                        break;
                    }
                }

                if (!success) {
                    network.purge(socket);
                    network.sendRequest(socket,"joinFailed".getBytes());
                }
            } break;

            case "joinFailed": {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR, "Unable to join lobby; perhaps it's full" +
                            " or the password was incorrect?", ButtonType.OK).show();
                    });
                URL url = getClass().getResource("serverScenes/welcomeScreen.fxml");
                try {
                    network.fxmlController.loadScene(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } break;

            case "joinSuccessful": {
                Platform.runLater(() -> {
                        Network.fxmlController.loadLobbyScene();
                        Network.fxmlController.removeStartLobbyBtn();
                    });
                }
                break;

            // Received a request to login
            case "login":
                name = network.getNextString(socket);
                password = network.getNextString(socket);
                network.client = new Client(socket,name);
                boolean success = network.server.accounts.verifyAccount(name,password);
                if (success) {
                    network.sendRequest(socket,"loginSuccessful".getBytes());

                    // We create a reference to the client for later calls
                    network.client = new Client(socket,name);
                    network.clientConnectionManager.clients.put(name,network.client);

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
                        URL url = getClass().getResource("serverScenes/welcomeScreen.fxml");
                        try {
                            network.fxmlController.loadScene(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            // The host has started their server and is accepting connections, and disconnected from the server
            // We want to disconnect from the server and migrate over now!
            case "migrate": {


                // We send the name of the host so that we can get a reference to the client and thus lobby
                String hostName = network.getNextString(socket);
                InetAddress hostAddress = socket.getInetAddress();
                String newPort = network.getNextString(socket);

                Lobby lobby =
                        (Lobby)network.deserializeObject(network.getNextBytes(socket));

                for (String user :  lobby.playerNames) {
                    // This is a lengthy way of finding out of we're comparing the host....
                    if (!user.equals(hostName)) {
                        Client client = network.clientConnectionManager.clients.get(user);
                        network.sendRequest(client.getSocket(), "connectToHost".getBytes());
                        network.sendRequest(client.getSocket(), hostAddress);
                        network.sendRequest(client.getSocket(), newPort.getBytes());
                    }
                }

                // We can now remove this lobby from our active lobby list
                for (Lobby l : (ArrayList<Lobby>)network.lobbyManager.lobbyList.clone()){
                    if (l.name.equals(lobby.name)){
                        network.lobbyManager.lobbyList.remove(l);
                    }
                }

                network.purge(socket);
                break;
            }

            case "receivedLobbyList":
                network.lobbyManager.lobbyList =
                        (ArrayList<Lobby>)network.deserializeObject(network.getNextBytes(socket));
                Platform.runLater(() -> network.fxmlController.updateServerLobbyDisplay(network.lobbyManager.lobbyList,false));
                break;

            // The server has been shutdown, after we've confirmed that we know that, close
            case "serverShutdown": {
                Platform.runLater(()->{
                    new Alert(Alert.AlertType.ERROR,"The server has shutdown",ButtonType.OK).showAndWait();
                    System.exit(0);
                });
                break;
            }

            case "showLobbyList":
                System.out.println("Serializing/sending lobby list");
                network.sendRequest(socket, "receivedLobbyList".getBytes());
                network.sendRequest(socket,network.lobbyManager.lobbyList);
                break;

            case "startGame": {
                Platform.runLater(() -> {
                    network.fxmlController.startGame(network.client.getLobby());
                });
                break;
            }

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
