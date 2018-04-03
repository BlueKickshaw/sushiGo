package server;

import java.net.Socket;

public class Client {
    private int playerNumber;
    private Socket socket;
    private Lobby lobby;
    private String name;
    private boolean loggedIn = false;

    public Client(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Socket getSocket(){
        return socket;
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

    public void setLoggedIn(boolean status){
        loggedIn = status;
    }


    public String getName() {
        return name;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean isLoggedIn(){
        return loggedIn;
    }
}
