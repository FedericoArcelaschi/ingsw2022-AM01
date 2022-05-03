package it.polimi.ingsw.communication.packet.message;

public final class CommandMessage extends Message{
    private final String username;
    private final String command;

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
