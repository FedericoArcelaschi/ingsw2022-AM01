package it.polimi.ingsw.communication.packet.message.command;

import it.polimi.ingsw.communication.packet.message.Message;

public class CommandMessage implements Message {
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

    @Override
    public String toString() {
        return  "player: " + username +
                "command: " + command;
    }
}
