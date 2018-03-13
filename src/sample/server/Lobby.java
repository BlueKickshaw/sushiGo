package sample.server;

import java.io.Serializable;
import java.util.ArrayList;

public class Lobby implements Serializable {
    String name, password;
    String host;
    int playerCount;
    ArrayList<String> ipList = new ArrayList<>();

    public Lobby(String host, String name, String password){
        this.host = host;
        this.name = name;
        this.password = password;
        playerCount = 1;
    }
}
