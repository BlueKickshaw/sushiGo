package server;

import java.net.InetAddress;
import java.util.ArrayList;

public class LobbyManager {
    Network network;
    public ArrayList<Lobby> lobbyList = new ArrayList<>();

    public LobbyManager(Network network){
        this.network = network;
    }

    public Lobby createLobby(InetAddress host, String name, String password){
        for (Lobby l : lobbyList){
            if (l.name.equals(name)){
                return null;
            }
        }
        Lobby lobby = new Lobby(host,name,password);
        lobbyList.add(lobby);
        lobby.ipList.add(host.getHostAddress());
        System.out.println(host+": created lobby ["+name+":"+password+"]");
        return lobby;
    }
}
