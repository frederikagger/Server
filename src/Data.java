import java.util.ArrayList;

public class Data {
    ArrayList<Client> arrayList = new ArrayList<>();

    private static Data single_instance = null;

    private Data(){
    }

    public static Data getSingle_instance() {
        if (single_instance == null){
            single_instance = new Data();
        }
        return single_instance;
    }
}
