package server;

import java.io.*;
import java.net.*;

public class Network {
    public Client client;
    public int port;
    public Socket socket;
    public Server server;
    public String username;

    public static FXMLController fxmlController;

    public LobbyManager lobbyManager = new LobbyManager(this);
    public ClientConnectionManager clientConnectionManager;

    private String address = "localhost";

    Network(int port){
        this.port = port;
    }

    // Connects to the server (either lobby server, or host server)
    public void connectToServer() {
        try {
            // TODO: the address is hardcoded to the localhost, this should be changed
            socket = new Socket(address,port);
            // Create a connectionManager to listen for the server's responses
            clientConnectionManager = new ClientConnectionManager(this, socket);
            System.out.println(Thread.currentThread().getName()
                    +": Created connection to "+ InetAddress.getByName(address));
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName()+": Unable to connect to server over port "+port);
        }
    }

    public Object deserializeObject(byte[] bytes){
        ObjectInputStream ois;
        ByteArrayInputStream bis;
        Object o = null;

        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    // Queries a specific webservice in order to get the users IP address (useful for sharing server information)
    public String getMyIP(){
        try {
            URL url = new URL("http://bot.whatismyipaddress.com/");
            URLConnection uc = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            return br.readLine();
        } catch (IOException e) {
            System.err.println("Network: Unable to get IP");
        }
        return null;
    }

    // Sometimes we receive a lot of data from a socket, but we want to cut it short and disregard the rest.
    // This is how we do that
    public void purge(Socket socket) {
        DataInputStream dis;
        try {
            dis = new DataInputStream(socket.getInputStream());
            while (dis.available() > 0){
               dis.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getNextBytes(Socket socket){
        DataInputStream dis;
        try {
            dis = new DataInputStream(socket.getInputStream());
            int size = dis.readInt();
            byte[] buffer = new byte[size];
            dis.readFully(buffer,0,size);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public String getNextString(Socket socket){
        return new String(getNextBytes(socket));
    }

    public int getOpenPort() {
        int freePort = 0;
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.close();
            freePort = socket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return freePort;
    }

    // Sends the size of the request, then the request itself (in bytes)
    public void sendRequest(String request){
        System.out.println(Thread.currentThread().getName()+": sending ['"+request+"']");
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(request.length());
            dos.write(request.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
            System.err.println("Null Socket; did you use the wrong Send Request to communicate to the client?");
            System.err.println("["+request+"]");
        }
    }

    public void sendRequest(byte[] request){
        System.out.println(Thread.currentThread().getName()+": sending ['"+new String(request)+"']");
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(request.length);
            dos.write(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // When we want to send a request to a specific socket (aka not the server, but a client)
    public void sendRequest(Socket dest, byte[] request){
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(dest.getOutputStream());
            dos.writeInt(request.length);
            dos.write(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] serializeObject(Object o){
        ObjectOutputStream oos;
        ByteArrayOutputStream bos = null;
        byte[] bytes = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // Sets the address of the server to connect to; used to connect to the lobby host, instead of the server
    public void setServerAddress(String address){
        this.address = address;
    }

    // Start the server over the given port
    public int startServer(Network network) {
        server = new Server(network, port);
        return port;
    }
}
