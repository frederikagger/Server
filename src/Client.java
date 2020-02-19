import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream inputSocket;
    private String username = "kaj";
    private Scanner scanner;

    // constructor to put ip address and port
    public Client(String address, int port) {
        try {
            socket = new Socket(address, port); // establish a connection
        } catch (UnknownHostException u) {
            u.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            scanner = new Scanner(System.in); // takes input from terminal
            output = new DataOutputStream(socket.getOutputStream());  // sends output to the socket
            inputSocket = new DataInputStream(new BufferedInputStream(socket.getInputStream())); // takes input from the server socket
         //   setUsername();
            //   output.writeUTF("JOIN<<"+this.username+">>, <<"+socket.getInetAddress()+">>:<<"+socket.getPort()+">>");
            //    sendMessages();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        output.writeUTF(this.username+": "+message);
    }

    public void setUsername(){
        System.out.println("Enter username: ");
        this.username = scanner.next();
        while (this.username.length()>12){
            System.out.println("Username has to be less than 12 characters. Try again.\nEnter username: ");
            this.username = scanner.next();
        }
    }

    public void sendMessages() throws IOException {
        String line = "";
        while (!line.equalsIgnoreCase("Quit")){ // keep reading until "Quit" is input
            try {
                line = scanner.nextLine();
                output.writeUTF(this.username+": "+line);
            }
            catch(IOException i){
                i.printStackTrace();
                break;
            }
        }
        output.close(); // close the connection
        socket.close();
        scanner.close();
    }
}