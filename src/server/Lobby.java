package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Lobby implements Serializable {
    String name;
    // We do not want to send the passwords when we send the lobby information
    transient String password;
    boolean hasPassword = false;

    String host;
    int playerCount;
    ArrayList<String> ipList = new ArrayList<>();

    public Lobby(String host, String name, String password){
        this.host = host;
        this.name = name;
        this.password = password;
        if (!password.isEmpty()){
            hasPassword = true;
        }
        playerCount = 1;
    }
}
