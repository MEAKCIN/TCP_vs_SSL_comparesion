package server;

public class ServerMain
{
    public static void main(String args[])
    {
    	int TCPPORT = 1234;
    	int SSLPORT = 4321;

        new Server(SSLPORT,TCPPORT).start();
    }

}
