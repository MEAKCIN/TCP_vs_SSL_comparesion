package server;

import javax.net.ssl.SSLSocket;
import java.io.*;

public class SSLServerThread extends Thread
{

    private final String SERVER_ACK_MESSAGE = "ssl_server_ack";
    private SSLSocket sslSocket;
    private String line = new String();
    private BufferedReader is;
    private PrintWriter os;
    
    public SSLServerThread(SSLSocket s)
    {
        sslSocket = s;
    }

    public void run()
    {
        try {
            is = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            os = new PrintWriter(sslSocket.getOutputStream());
            line = is.readLine();
            os.write(SERVER_ACK_MESSAGE);

            System.out.println("Client " + sslSocket.getRemoteSocketAddress() + " sent : " + line+"\n");
            if (line.strip().equalsIgnoreCase("All")) {
                String[] file_name = {"alice.txt", "The Adventure of the Golden Pince-Nez.txt", "The Happy Prince.txt",
                        "The Memoirs of Sherlock Holmes.txt", "The nightingale and the rose.txt", "The Sign of the Four.txt"};

                for (String file : file_name) {
                    long start_time=  System.currentTimeMillis();
                    try {
                        File file_to_read= new File("./stories/" + file);
                        BufferedReader reader = new BufferedReader(new FileReader("./stories/" + file));
                        String read_line;
                        do {read_line=reader.readLine();
                            if (read_line!=null){
                                os.write(read_line+"\n");
                            }

                        }
                        while (reader.readLine()!=null);
                        long end_time= System.currentTimeMillis();
                        os.write("\n********************************************************************\nThis file elapsed: "+(end_time-start_time)+" sec"+"\nfile size: "+file_to_read.length()/1024+" KB \n");
                        os.flush();

                    }catch (IOException e){
                        e.printStackTrace();
                        System.err.println("Error reading file: "+e.getMessage());
                    }


                    }
                }else{
                  try{
                      long start_time=  System.currentTimeMillis();
                      BufferedReader reader= new BufferedReader(new FileReader("./src/stories/"+line));
                      File file_to_read=new File("./src/stories/"+line);
                      String read_line;
                      do {read_line=reader.readLine();
                          if (read_line!=null){
                              os.write(read_line+"\n");
                          }

                      }
                      while (reader.readLine()!=null);
                      long end_time= System.currentTimeMillis();
                      os.write("\n********************************************************************\nThis file elapsed: "+(end_time-start_time)+" sec"+"\nfile size: "+file_to_read.length()/1024+" KB \n");
                      os.flush();
                  }catch (IOException e){
                      System.err.println("File cannot readed: "+e.getMessage());
                  }
            }
        }
        
        catch (IOException e)
        {
            line = this.getClass().toString();
            System.out.println("Server Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        }
        catch (NullPointerException e)
        {
            line = this.getClass().toString();
            System.out.println("Server Thread. Run.Client " + line + " Closed");
        } 
        finally
        {
            try
            {
                System.out.println("Closing the connection");
                if (is != null)
                {
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if (os != null)
                {
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (sslSocket != null)
                {
                    sslSocket.close();
                    System.out.println("Socket Closed");
                }

            }
            catch (IOException ie)
            {
                System.out.println("Socket Close Error");
            }
        }
    }
}