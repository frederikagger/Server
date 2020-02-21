
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static ArrayList<Client> arrayList = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Server server1 = new Server(6000);
        Server server2 = new Server(5000);

        executor.execute(server1);
        executor.execute(server2);
        for (Client client : arrayList) {
            System.out.println(client);
        }
        executor.shutdown();
    }
}
