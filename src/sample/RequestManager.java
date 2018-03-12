package sample;

import java.net.Socket;

public class RequestManager {
    Socket socket;

    public RequestManager(Socket socket){
        this.socket = socket;
    }

    public void handleRequest(Socket socket, String request){
        System.out.println(socket.getInetAddress().toString() + ": sent ['"+request+"']");
    }
}
