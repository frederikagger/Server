import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ArrayList<Socket> sockets = new ArrayList<>();
    private ArrayList<MyServerSocket> serverSockets = new ArrayList<>();
    private HashSet<String> names = new HashSet<>();
    private ArrayList<Client> clients = new ArrayList<>();
    private final int numberOfClients = 4;
    private ExecutorService executor = Executors.newFixedThreadPool(numberOfClients);
    private final int port = 5000;

    public Server() {
        try {
            for (int i=0; i<numberOfClients; i++){
                MyServerSocket myServerSocket = new MyServerSocket(port+i, this);
                executor.execute(myServerSocket);
            }
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Socket> getSockets() {
        return sockets;
    }

    public ArrayList<MyServerSocket> getServerSockets() {
        return serverSockets;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public HashSet<String> getNames() {
        return names;
    }
}