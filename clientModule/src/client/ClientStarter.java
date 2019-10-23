package client;

import java.util.concurrent.Exchanger;

public class ClientStarter {
    private static final Exchanger<String> command_exchanger = new Exchanger<>();
    private static final Exchanger<Boolean> connection_exchanger = new Exchanger<>();

    public static void main(String[] args) {
        try{
            int port = 8289;
            String host = "localhost";
            new Thread(new Client(host,port, command_exchanger,connection_exchanger)).start();
            new Thread(new GuiThread(command_exchanger,connection_exchanger)).start();
        }catch (NumberFormatException e){
            return;
        }
    }
}
