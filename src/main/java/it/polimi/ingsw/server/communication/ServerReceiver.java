package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.communication.message.subclasses.CommandMessage;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Allow the server to receive packets from a serverReceiver's socket and handle them.
 */
public class ServerReceiver extends Receiver {

    private static Logger logger = LogManager.getLogger(ServerReceiver.class);

    private final Client client;
    private final HeartBeatServer hbs;
    private final LobbyManager lobbyManager;

    public ServerReceiver(Client client, HeartBeatServer hbs, LobbyManager lobbyManager) {
        super(client.clientsSocket());
        this.client = client;
        this.hbs = hbs;
        this.lobbyManager = lobbyManager;
    }

    @Override
    protected void messageSwitch(Message message) {
        if(message.getType() != MessageType.PING)
            logger.info("Server received message: " + message.getType() + " - from port: " + socket.getPort());
        switch (message.getType()) {
            case PING ->
                    hbs.validateResponse(socket);
            case COMMAND -> {
                    CommandMessage commandMessage = (CommandMessage) message;
                    System.out.println(commandMessage);
                    Command command = commandMessage.getCommand();
                    client.executeCommand(command, socket);
            }
            case END -> {/*TODO: the serverReceiver received the end message from the server and sent back an acknowledgment.*/}
            case ERROR -> {/*TODO: the serverReceiver received the error message from the server and sent back an acknowledgment.*/}
            case PREFERENCES -> lobbyManager.addPlayer(client, (Preferences) message);
        }
    }

}
