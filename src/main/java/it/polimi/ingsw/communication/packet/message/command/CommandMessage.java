package it.polimi.ingsw.communication.packet.message.command;

import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.MessageType;

public class CommandMessage extends Message {
    private final Command command;

    public CommandMessage(String username, String command) {
        super(MessageType.COMMAND);
        this.command = Command.createCommand(username, command);
    }

    public String getUsername() {
        return command.getUsername();
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return  "player: " + command.getUsername() +
                "command: " + command;
    }
}
