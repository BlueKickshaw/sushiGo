package server;

import Cards.Card;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class Lobby implements Serializable {
    String name;
    // We do not want to send the passwords when we send the lobby information
    transient String password;
    boolean hasPassword = false;

    InetAddress host;
    public int playerCount;
    ArrayList<String> ipList = new ArrayList<>();
    ArrayList<String> playerNames = new ArrayList<>();


    public Lobby(InetAddress host, String name, String password){
        this.host = host;
        this.name = name;
        this.password = password;
        if (!password.isEmpty()){
            hasPassword = true;
        }
        playerCount = 1;
    }
}
