package it.polimi.ingsw.communication.packet.message.command;

import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.MessageType;

public class CommandMessage extends Message {
    private final String username;
    private final String command;  //TODO: MAYBE THIS SHOULD BE A CommandType?

    public CommandMessage(String username, String command) {
        super(MessageType.COMMAND);
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
