package sample.server;

import java.io.IOException;
import java.net.Socket;
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
            case "host":
                String name = network.getNextString(socket);
                String password = network.getNextString(socket);
                network.lobbyManager.createLobby(socket.getInetAddress().toString(),name,password);
                GUI.UpdateLobbyGrid(GUI.serverGrid,network.lobbyManager);
                break;

            case "joinLobby":
                String lobbyName = network.getNextString(socket);
                System.out.println("Requesting to join lobby '"+lobbyName+"'");
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
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
