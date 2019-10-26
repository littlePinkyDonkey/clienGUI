package controller;

import model.CommandExchanger;

public class CommandCreator {
    private final StringBuilder command_builder = new StringBuilder();
    private final CommandExchanger command_exchanger;

    private final String name_part = "-{\"name\": \"";
    private final String tired_part = "\", \"tiredLevel\": \"";
    private final String quantity_part = "\", \"quantity\": \"";
    private final String position_part = "\", \"position\": \"";
    private final String last_part = "\"}";

    public CommandCreator(CommandExchanger command_exchanger){
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

    public String createCommand(String command, String name, String tired, String quantity, String position){
        command_builder.append(command).append(name_part).append(name).append(tired_part)
                .append(tired).append(quantity_part).append(quantity).append(position_part)
                .append(position).append(last_part);
        return command_exchanger.sendCommand(command_builder);
    }
}
