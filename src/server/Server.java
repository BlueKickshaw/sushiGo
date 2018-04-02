package server;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    // When we convert the host to the new server owner, we want to store the new users here
    public ArrayList<Socket> lobbyUsers = new ArrayList<>();
    ServerSocket serverSocket;

    int port;
    public AccountLists accounts = new AccountLists();

    // Start the server on the specified port; start listening for requests
    public Server(Network network, int port){
        this.port = port;

        // Load the accounts before we start the server, or else we might run in to errors at login
        accounts.loadAccounts();


        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            System.out.println(Thread.currentThread().getName()+": Started server over port "+port);
            new ClientConnectionManager(network);
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName()+": Unable to start server over port "+port);
            new Alert(Alert.AlertType.ERROR,"Unable to start server over port "+port+". It may be in use",
                    ButtonType.OK).showAndWait();
            System.exit(0);
            e.printStackTrace();
        }
    }
}
