package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPConnectToServer {
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final int DEFAULT_SERVER_PORT = 1234;
    private Socket s;
    protected BufferedReader is;
    protected PrintWriter os;

    protected String serverAddress;
    protected int serverPort;

    public TCPConnectToServer(String address,int port){
        serverAddress=address;
        serverPort=port;
    }


    public void connect() throws IOException {
        s = new Socket(serverAddress, serverPort);
        is = new BufferedReader(new InputStreamReader(s.getInputStream()));
        os = new PrintWriter(s.getOutputStream());
        System.out.println("TCP Successfully connected to server at" + s.getRemoteSocketAddress());

    }
    public void send(String message) throws IOException
    {
        os.println(message);
        os.flush();
    }

    public String get() throws IOException {
        String response= new String();
        response=is.readLine();
        return response;

    }


    public void disconnect() throws IOException
    {
        is.close();
        os.close();
        s.close();
        System.out.println("Connection Closed");
    }






}
