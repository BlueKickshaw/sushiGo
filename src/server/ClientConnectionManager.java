package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnectionManager {
    private Network network;
    private boolean running = true;
    private ServerSocket serverSocket;

    // Creates something to manage the connections, and starts a listener thread
    public ClientConnectionManager(Network network){
        this.network = network;
        new Thread(new ClientConnectionListener(),"ConnectionListenerThread").start();
    }

    // Creates a listener to someone we have a connection with (allows server to send client messages)
    public ClientConnectionManager(Network network, Socket socket){
        this.network = network;
        new Thread(new ClientConnectionHandler(socket)).start();
    }

    // Creates a listener thread on the server to listen for incoming connections
    private class ClientConnectionListener implements Runnable {
        @Override
        public void run() {
            while (running) {
                System.out.println(Thread.currentThread().getName()+": Listening for connections");
                try {
                    Socket client = network.server.serverSocket.accept();
                    new Thread(new ClientConnectionHandler(client),"Handler:"
                            +client.getInetAddress()).start();
                    System.out.println(Thread.currentThread().getName()
                            +": Accepted connection from "+client.getInetAddress());
                } catch (IOException e) {
                    System.err.println(Thread.currentThread().getName()+": Unable to accept incoming socket");
                }
            }
        }
    }

    private class ClientConnectionHandler implements  Runnable {
        Socket socket;
        RequestManager requestManager;
        ClientConnectionHandler(Socket socket){
            this.socket = socket;
            this.requestManager = new RequestManager(network,socket);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+": Listening for requests from "
                    +socket.getInetAddress().toString());
            while (running) {
                int size;
                byte[] bytes = null;
                DataInputStream dis;
                try {
                    dis = new DataInputStream(socket.getInputStream());
                    if (dis.available() > 0) {
                        size = dis.readInt();
                        bytes = new byte[size];
                        dis.readFully(bytes,0,size);
                        requestManager.handleRequest(socket,bytes);
                    }
                } catch (IOException e) {
                    running = false;
                    e.printStackTrace();
                }
            }
        }
    }
}