package client;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Copyright [Yahya Hassanzadeh-Nazarabadi]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class SSLConnectToServer
{
    /*
    Name of trust file
     */
    private final String TRUST_STORE_NAME =  "./src/client/MehmetEminAkçinTruststore.jks";
    /*
    Password to the trust store file
     */
    private final String TRUST_STORE_PASSWORD = "0076517";

    private SSLSocketFactory sslSocketFactory;
    private SSLSocket sslSocket;
    private BufferedReader is;
    private PrintWriter os;

    protected String serverAddress;
    protected int serverPort;



    protected int mainPort;

    public SSLConnectToServer(String address, int portSSL)
    {
        this.serverAddress = address;
        this.mainPort=portSSL;
        System.setProperty("javax.net.ssl.trustStore", TRUST_STORE_NAME);
        System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PASSWORD);

    }
    public void connect(){
        try{
            sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sslSocket = (SSLSocket) sslSocketFactory.createSocket(serverAddress, mainPort);
            sslSocket.startHandshake();
            is=new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            os= new PrintWriter(sslSocket.getOutputStream());
            System.out.println("SSL Successfully connected to " + serverAddress + " on port " + serverPort);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void disconnect()
    {
        try
        {
            is.close();
            os.close();
            sslSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void send(String message)
    {
        os.println(message);
        os.flush();
    }
    public String get() throws IOException {
        String response= new String();
        response=is.readLine();
        return response;
    }


}
