import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket extends ServerSocket implements Runnable {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private ServerSocket serverSocket;
    private Client client;
    private Protocol protocol = new Protocol();
    private int port;
    private Server server;

    public MyServerSocket(int port, Server server) throws IOException {
        serverSocket = new ServerSocket(port);
        server.getServerSockets().add(this); // adding the serversockets to the servers arraylist
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server started at port: " + port);
            socket = serverSocket.accept();
            server.getSockets().add(socket);
            System.out.println("Connected at port: " + port);
            out = new DataOutputStream(socket.getOutputStream());  // sends output to socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); // takes input from the client socket
            joinResponse();
            readMessages();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    /**
     * Method that keeps receving messages until "Quit" is send
     */

        private void readMessages() throws IOException{
        String line = "";  // reads message from client until "Quit" is sent
        while (!line.equalsIgnoreCase("Quit")) {
            try {
                line = in.readUTF();
                System.out.println(line);
                if (line.startsWith("DATA")) {
                    dataResponse(line);
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

    /**
     * Method that takes a string as input and transmits it to all connected clients
     * @param line
     * @throws IOException
     */
    private void dataResponse(String line) throws IOException {
        if (line.startsWith("DATA")){
               line = line.substring(5);
        }
        for (Socket socket: server.getSockets()) {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(line);
        }
    }

    /**
     * Method that keeps looping until the client sends a message that corresponds to the protocol
     * @throws IOException
     */

    public void joinResponse() {
        String line = "";
        boolean clientAccepted = false;
        while (!clientAccepted){
            try {
                line = in.readUTF();
                String[] args = line.split(" ");
                if (check(args)){
                    String name = args[1];
                    String IP = args[2];
                    int port = Integer.parseInt(args[3]);
                    this.client = new Client(name, IP, port);
                    server.getNames().add(name);
                    server.getClients().add(client);
                    out.writeUTF(protocol.ok());
                    dataResponse(this.client.getUsername() + " joined the chat");
                    System.out.println(protocol.ok());
                    System.out.println(this.client.getUsername() + " joined the chat");
                    clientAccepted = true;
                }
        } catch (IOException e) {
            e.printStackTrace();
            }
        }
    }

        public boolean check(String line[]) throws IOException {
            if (!line[0].equals("JOIN")){
                out.writeUTF(protocol.clientNotAccepted(1000, "Bad command"));
                System.out.println(protocol.clientNotAccepted(1000, "Bad command"));
                return false;
            }
            if (line.length>4){
                out.writeUTF(protocol.clientNotAccepted(2000, "Too many arguments"));
                System.out.println(protocol.clientNotAccepted(2000, "Too many arguments"));
                return false;
            }
            if (usernameIsTaken(line[1])){
                out.writeUTF(protocol.clientNotAccepted(5000, "Name is already taken"));
                System.out.println(protocol.clientNotAccepted(5000, "Name is already taken"));
                return false;
            }

            if (line[1].length()>12){
                out.writeUTF(protocol.clientNotAccepted(3000, "Name is too long"));
                System.out.println(protocol.clientNotAccepted(3000, "Name is too long"));
                return false;
            }
            if (Integer.parseInt(line[3])!=this.port){
                out.writeUTF(protocol.clientNotAccepted(4000, "Port is wrong"));
                System.out.println(protocol.clientNotAccepted(4000, "Port is wrong"));
                return false;
            }
            return true;
    }

    public boolean usernameIsTaken(String name){
        return server.getNames().contains(name);
    }
}