package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.communication.command.Command;

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
