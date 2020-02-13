import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream in;

    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = serverSocket.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            
            String line = "";
            // reads message from client until "Over" is sent
            while (!line.equals("Over")){
                try {
                    line = in.readUTF();
                    System.out.println(line);
                } catch(IOException e) {
                    System.out.println(e);
                }
            }

            System.out.println("Closing connection");
            // close connection
            socket.close();
            in.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

    }
}