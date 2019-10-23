package model;

import java.util.concurrent.Exchanger;

public class CommandExchanger {
    private final String error = "Error";
    private Exchanger<String> exchanger;

    private static volatile CommandExchanger instance;
    private CommandExchanger(Exchanger<String> exchanger){
        this.exchanger = exchanger;
    }
    public static CommandExchanger getInstance(Exchanger<String> exchanger){
        if (instance == null){
            synchronized (CommandExchanger.class){
                if (instance == null)
                    instance = new CommandExchanger(exchanger);
            }
        }
        return instance;
    }

    public String sendNewUser(StringBuilder user){
        try {
            return exchanger.exchange(user.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return error;
        }
    }

    public String sendOldUser(StringBuilder user){
        try {
            return exchanger.exchange(user.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return error;
        }
    }

    public String sendCommand(StringBuilder command){
        try {
            return exchanger.exchange(command.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return error;
        }
    }
}
