package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.packet.*;
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
                Ping ping = (Ping) message;
                System.out.println("Server: ping received");
                hbs.validateResponse(ping);
            }
            case COMMAND -> {

                //TODO: The client is asking to run a command, so we need to check and execute it if is possible.
                 CommandMessage commandMessage = (CommandMessage) message;
                 Command command = Command.createCommand(commandMessage.getUsername(), commandMessage.getCommand());
                 String response = game.executeCommand(command);
                //If the move is illegal we send an ERROR message.

                //Else we broadcast the updated BoardData to all clients.
                System.out.println("--Update Boards--: " + response);
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
