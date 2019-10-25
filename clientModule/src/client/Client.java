package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class Client implements Runnable {
    private final Exchanger<String> command_exchanger;
    private final Exchanger<Boolean> connection_exchanger;
    private String host;
    private int port;
    private Socket socket;
    private Scanner scanner = new Scanner(System.in);
    private String userName;

    Client(String host, int port, Exchanger<String> command_exchanger, Exchanger<Boolean> connection_exchanger){
        this.host = host;
        this.port = port;
        this.command_exchanger = command_exchanger;
        this.connection_exchanger = connection_exchanger;
    }

    @Override
    public void run() {
        for (;;){
            try{
                getConnection(host,port);
                System.out.println("ready");
            }catch (ConnectException e){
                try{
                    System.out.println("connect/exit");
                    connection_exchanger.exchange(true);

                    String decision = command_exchanger.exchange(null);
                    if (decision.equals("connect")){
                        continue;
                    } else if (decision.equals("exit")){
                        System.exit(1);
                    }
                }catch (InterruptedException ex){
                    e.printStackTrace();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            while (true){
                try{

                    String command = command_exchanger.exchange(null);

                    if (command.contains(" auth")){
                        this.userName = command.split(" ")[0];
                    }
                    try{
                        if (socket==null || !socket.isConnected()) {
                            continue;
                        }
                        socket.getOutputStream().write(command.concat("/" + userName).getBytes());
                        byte[] bytes = new byte[8192];
                        int count = socket.getInputStream().read(bytes);
                        command_exchanger.exchange(new String(bytes,0,count));

                        if (command.equals("exit"))
                            System.exit(1);
                    }catch (IOException e){
                        if (e instanceof SocketException){
                            try{
                                Thread.sleep(1000);
                                socket.close();
                                getConnection(host,port);
                                continue;
                            }catch (IOException | InterruptedException e1){
                                continue;
                            }
                        }
                        e.printStackTrace();
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void getConnection(String host, int port) throws IOException {
        socket = new Socket(host,port);
    }
}
