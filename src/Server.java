import java.io.*;
import java.net.*;

public class Server implements Runnable {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private Client client;
    private Protocol protocol = new Protocol();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            Client client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run () {
        try {
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = serverSocket.accept();
            System.out.println("Connected");
            out = new DataOutputStream(socket.getOutputStream());  // sends output to socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); // takes input from the client socket
            readMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that keeps receving messages until "Quit" is send
     */
    public void readMessages() throws IOException {
        String line = "";  // reads message from client until "Quit" is sent
        while (!line.equalsIgnoreCase("Quit")) {
            try {
                line = in.readUTF();
                System.out.println(line);
               // switch (line.){
               //     case
                if (line.startsWith("JOIN")){
                    joinResponse(line);
                }
                if (line.startsWith("DATA")) {
                    dataResonse(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("Closing connection");
        socket.close(); // close connection
        in.close();
        out.close();
    }

    public Client getClient() {
        return client;
    }

    private void dataResonse(String line) {
    }

    public void joinResponse(String line) throws IOException {
        String[] strings = line.split(" ");
        client = new Client(strings[1], strings[2], Integer.parseInt(strings[3]));
        Main.arrayList.add(client);
       // Data.getSingle_instance().arrayList.add(client);
        out.writeUTF(protocol.ok());
        System.out.println(protocol.ok());
    }
}