public class Client {
    private String IP;
    private String username;
    private int port;

    public Client(String username, String IP, int port) {
        this.username = username;
        this.IP = IP;
        this.port = port;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Client{" + "IP='" + IP + '\'' +
                ", username='" + username + '\'' +
                ", port=" + port +
                '}';
    }
}