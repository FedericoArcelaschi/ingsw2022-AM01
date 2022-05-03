package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.packet.*;
import it.polimi.ingsw.communication.packet.message.CommandMessage;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.Ping;
import it.polimi.ingsw.controller.Game;

import java.net.Socket;

public class ServerReceiver extends Receiver{

    private final HeartBeatServer hbs;
    private final Game game;

    public ServerReceiver(Socket socket, HeartBeatServer hbs, Game game) {
        super(null, socket);
        this.hbs = hbs;
        this.game = game;
    }

    void messageSwitch(MessageType type, Message message){
        switch (type){
            case PING -> {
                hbs.validateResponse(message);
            }
            case COMMAND -> {

                //TODO: The client is asking to run a command, so we need to check and execute it if is possible.
                 CommandMessage commandMessage = (CommandMessage) message;
                 Command command = Command.createCommand(commandMessage.getUsername(), commandMessage.getCommand());
                 Packet packet = game.executeCommand(command);
                 out.println(parser.toJson(packet));
                 if(packet.getType() == MessageType.UPDATE)
                     game.sendUpdate(packet);
                 else
                     game.sendError(commandMessage.getUsername(), packet);

            }
            case END -> {
                //TODO: the client received the end message from the server and sent back an acknowledgment.
            }
            case ERROR -> {
                //TODO: the client received the error message from the server and sent back an acknowledgment.
            }
        }
    }
}
