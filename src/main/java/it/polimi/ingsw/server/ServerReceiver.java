package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.MessageType;
import it.polimi.ingsw.communication.packet.message.command.Command;
import it.polimi.ingsw.communication.packet.message.command.CommandMessage;
import it.polimi.ingsw.server.controller.Game;

import java.net.Socket;

/**
 * Allow the server to receive packets from a client's socket and handle them.
 */
public class ServerReceiver extends Receiver {

    private final HeartBeatServer hbs;
    private Game game;

    public ServerReceiver(Socket socket, HeartBeatServer hbs, Game game) {
        super(null, socket);
        this.hbs = hbs;
        this.game = game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    void messageSwitch(MessageType type, Message message) {
        switch (type){
            case PING -> {
                System.out.println("client pinged back");
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

    public Socket getSocket() {
        return socket;
    }

    public Game getGame(){
        return game;
    }
}
