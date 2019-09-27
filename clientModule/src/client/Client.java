package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client implements Runnable {
    private String host;
    private int port;
    private Socket socket;
    private Scanner scanner = new Scanner(System.in);

    Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        for (;;){
            try{
                getConnection(host,port);
                System.out.println("Connection is ready. Enter help");
            }catch (ConnectException e){
                System.out.println("connect/exit");
                if (scanner.hasNextLine()){
                    String decision = scanner.nextLine();
                    if (decision.equals("connect")){
                        continue;
                    } else if (decision.equals("exit")){
                        System.exit(1);
                    } else{
                        System.out.println("Incorrect input. Try again!");
                        continue;
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            while (true){
                if (scanner.hasNextLine()){
                    String command = scanner.nextLine();

                    if (command.equals(""))
                        continue;

                    if (command.equals("import")){
                        Path path = Paths.get("Lab5XML.xml");

                        try{
                            command += " " + Files.lines(path).collect(Collectors.joining("\n"));
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    try{
                        if (socket==null || !socket.isConnected()) {
                            System.out.println("test");
                            continue;
                        }
                        socket.getOutputStream().write(command.getBytes());
                        System.out.println("Go to server");
                        byte[] bytes = new byte[8192];
                        int count = socket.getInputStream().read(bytes);
                        System.out.println(new String(bytes,0,count));

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
                }
            }
        }
    }

    public void getConnection(String host, int port) throws IOException {
        socket = new Socket(host,port);
    }
}
