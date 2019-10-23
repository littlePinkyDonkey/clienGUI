package controller;

import model.CommandExchanger;

public class LogInCreator {
    private final StringBuilder command_builder = new StringBuilder();
    private final CommandExchanger command_exchanger;

    public LogInCreator(CommandExchanger command_exchanger){
        this.command_exchanger = command_exchanger;
    }

    public String registration(String login, String email){
        command_builder.append(login).append(" ").append(email).append(" regist");
        return command_exchanger.sendNewUser(command_builder);
    }

    public String authorization(String login, String password){
        command_builder.append(login).append(" ").append(password).append(" auth");
        return command_exchanger.sendOldUser(command_builder);
    }
}
