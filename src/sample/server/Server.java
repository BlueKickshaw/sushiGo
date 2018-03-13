package sample.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    int port;

    // Start the server on the specified port; start listening for requests
    public Server(Network network, int port){
        this.port = port;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println(Thread.currentThread().getName()+": Started server over port "+port);
            new ClientConnectionManager(network);
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName()+": Unable to start server over port "+port);
        }
    }
}
