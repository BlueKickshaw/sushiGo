package server;

import java.net.Socket;

public class Client {
    private Socket socket;
    private Lobby lobby;
    private String name;

    public Client(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
