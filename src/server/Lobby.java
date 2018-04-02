package server;

import Cards.Card;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class Lobby implements Serializable {
    private int seed;
    String name;
    // We do not want to send the passwords when we send the lobby information
    transient String password;
    boolean hasPassword = false;

    InetAddress host;
    public int playerCount;
    public ArrayList<String> ipList = new ArrayList<>();
    public ArrayList<String> playerNames = new ArrayList<>();


    public Lobby(InetAddress host, String name, String password){
        this.seed = new Random().nextInt();
        this.host = host;
        this.name = name;
        this.password = password;
        if (!password.isEmpty()){
            hasPassword = true;
        }
        playerCount = 1;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
