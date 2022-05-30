package it.polimi.ingsw.communication.packet.message;

public class CommandMessage implements Message{
    private final String username;
    private final String command;  //TODO: MAYBE THIS SHOULD BE A CommandType?

    public CommandMessage(String username, String command) {
        this.username = username;
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public String getCommand() {
        return command;
    }
}
