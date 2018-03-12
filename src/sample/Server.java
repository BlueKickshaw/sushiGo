package sample;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    ServerSocket socket;
    int port;
    private Network network;
    private ClientConnectionManager listener;

    // Start the server on the specified port
    public Server(Network network, int port){
        this.network = network;
        this.port = port;

        try {
            socket = new ServerSocket(port);
            System.out.println(Thread.currentThread().getName()+": Started server over port "+port);
            listener = new ClientConnectionManager(network);
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName()+": Unable to start server over port "+port);
        }
    }
}
