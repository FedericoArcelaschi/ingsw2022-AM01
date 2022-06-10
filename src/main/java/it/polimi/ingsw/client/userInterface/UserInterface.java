package it.polimi.ingsw.client.userInterface;

import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.controller.GameType;

import java.util.List;

public interface UserInterface {

    /**
     * updates the view with the new data received from the server.
     */
    void draw(BoardData boardData);
    void printWaitingRoom(LobbyInfo lobbyInfo);
}
