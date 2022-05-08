package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.packet.*;
import it.polimi.ingsw.communication.packet.message.CommandMessage;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.Ping;
import it.polimi.ingsw.controller.Game;

import java.net.Socket;

/**
 * Allow the server to receive packets from a client's socket and handle them.
 */
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
                 CommandMessage commandMessage = (CommandMessage) message;
                 Command command = Command.createCommand(commandMessage.getUsername(), commandMessage.getCommand());
                 //the game already handle message back to clients
                 game.executeCommand(command);

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
