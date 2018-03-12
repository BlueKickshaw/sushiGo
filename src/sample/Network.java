package sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Network {
    public Socket socket;
    public Server server;

    private int port;
    private String address = "localhost";

    Network(int port){
        this.port = port;
    }

    // Start the server over the given port
    public void startServer(Network network) {
        server = new Server(network, port);
    }

    // Connects to the server (either lobby server, or host server)
    public void connectToServer() {
        try {
            // TODO: the address is hardcoded to the localhost, this should be changed
            socket = new Socket(address,port);
            System.out.println(Thread.currentThread().getName()
                    +": Created connection to "+ InetAddress.getByName(address));
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName()+": Unable to connect to server over port "+port);
        }
    }

    // Sets the address of the server to connect to; used to connect to the lobby host, instead of the server
    public void setServerAddress(String address){
        this.address = address;
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
        }
    }
}
