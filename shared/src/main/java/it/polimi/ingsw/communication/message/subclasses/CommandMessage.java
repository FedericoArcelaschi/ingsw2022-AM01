package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.communication.command.Command;

import java.text.ParseException;

public class CommandMessage extends Message {

    private final Command command;

    public CommandMessage(String command) throws ParseException {
        super(MessageType.COMMAND);
        this.command = new Command(command);
    }

    public CommandMessage(Command command) {
        super(MessageType.COMMAND);
        this.command = command;
    }

    public String getUsername() {
        return command.username();
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return  "player: " + command.username() +
                "command: " + command;
    }
}
