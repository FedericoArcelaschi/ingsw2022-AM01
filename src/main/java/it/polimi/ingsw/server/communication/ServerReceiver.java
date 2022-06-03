package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.packet.message.Message;
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
    private final String username;

    public ServerReceiver (Socket socket, HeartBeatServer hbs, String username) {
        super(null, socket);
        this.hbs = hbs;
        this.username = username;
    }

    protected void messageSwitch (Message message) {
        switch (message.getMessageType()){
            case PING -> {
                System.out.println("PING!");
                hbs.validateResponse(message);
            }
            case COMMAND -> {
                 CommandMessage commandMessage = (CommandMessage) message;
                 Command command = commandMessage.getCommand();

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

    public void setGame(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

    public String getUsername(){
        return username;
    }
}
