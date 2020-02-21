public class Protocol {

    public String join(String username, String ip, int port){
        return "JOIN "+username+" "+ip+" "+port;
    }

    public String ok(){
        return "J_OK";
    }

    public String clientNotAccepted(int errorCode, String errorMsg){
        return "J_ER "+errorCode+":"+errorMsg;
    }
    public String data(String username, String message){
        return "DATA "+username+": "+message;
    }

    public String IMAV(){
        return "IMAV";
    }

    public String quit(){
        return "Quit";
    }

    public String list(String[] usernames){
        String users = "";
        for (String username: usernames) {
            users = users + username;
        }
        return users;
    }
}