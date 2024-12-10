package client;

import java.io.IOException;
import java.util.Scanner;


public class ClientMain {
    public static void main(String[] args) throws IOException {
    	 final  String SERVER_ADRESS ="localhost";
         final  int TCP_PORT = 1234;
    	 final int SSL_PORT = 4445;

        System.out.println("For connection select protocol type 1 for TCP , 2:  for SSL");
        Scanner scanner= new Scanner(System.in);
        Integer selected_port= scanner.nextInt();

        if(selected_port==1){
            TCPConnectToServer connectionToServer= new TCPConnectToServer(SERVER_ADRESS,TCP_PORT);
           try {

                connectionToServer.connect();
                System.out.println("Enter message for server");
               String message = scanner.nextLine();
                while(message==""||message==null){
                    message= scanner.nextLine();
                }
                long start_time = System.currentTimeMillis();
                connectionToServer.send(message);//message sends to server
                String response = connectionToServer.get();
                while (response != null) {
                    System.out.println("Server Respond: " + response);
                    response = connectionToServer.get();

                }//read server output and write in the client
                long end_time = System.currentTimeMillis();
                System.out.println("Time elapsed: " + (end_time - start_time));
            }
            catch(Exception e){
                System.err.println("Error in connection For TCP "+e.getMessage());
            }finally{
                try {
                    connectionToServer.disconnect();
                    scanner.close();
                }catch (IOException e){
                    System.err.println("TCP connection cannot closed "+e.getMessage());
                }
            }


        } else if (selected_port==2) {
            SSLConnectToServer connectionToServer=new SSLConnectToServer(SERVER_ADRESS,SSL_PORT);
            try{
                connectionToServer.connect();
                System.out.println("Enter message for server");
                String message = scanner.nextLine();
                while(message==""||message==null){
                    message= scanner.nextLine();
                }
                long start_time = System.currentTimeMillis();
                connectionToServer.send(message);//message sends to server
                String response = connectionToServer.get();
                while (response != null) {
                    System.out.println("Server Respond: " + response);
                    response = connectionToServer.get();

                }//read server output and write in the client
                long end_time = System.currentTimeMillis();
                System.out.println("Time elapsed: " + (end_time - start_time));

            }catch (Exception e){
                System.err.println("Error in connection for SSL "+e.getMessage());
            } finally{
                connectionToServer.disconnect();
                scanner.close();
            }

        }
        else {
            System.out.println("Entered wrong integer. Client will be closed");
        }


        
    }
}