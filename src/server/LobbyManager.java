package server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class LobbyManager {
    Network network;
    public ArrayList<Lobby> lobbyList = new ArrayList<>();
    public ObservableList<Lobby> lobbyObservableList = FXCollections.observableList(lobbyList);

    public LobbyManager(Network network){
        this.network = network;
    }

    public Lobby createLobby(String host, String name, String password){
        for (Lobby l : lobbyList){
            if (l.name.equals(name)){
                return null;
            }
        }
        Lobby lobby = new Lobby(host,name,password);
        lobbyList.add(lobby);
        lobby.ipList.add(host);
        System.out.println(host+": created lobby ["+name+":"+password+"]");
        return lobby;
    }
}
