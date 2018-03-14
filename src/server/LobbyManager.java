package server;

import java.util.ArrayList;

public class LobbyManager {
    Network network;
    public ArrayList<Lobby> lobbyList = new ArrayList<>();

    public LobbyManager(Network network){
        this.network = network;
    }

    public Lobby createLobby(String host, String name, String password){
        Lobby lobby = new Lobby(host,name,password);
        lobbyList.add(lobby);
        lobby.ipList.add(host);
        System.out.println(host+": created lobby ["+name+":"+password+"]");
        return lobby;
    }
}
