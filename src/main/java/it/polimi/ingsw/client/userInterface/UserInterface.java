package it.polimi.ingsw.client.userInterface;

import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Update;
import it.polimi.ingsw.communication.modelData.BoardData;

public interface UserInterface {

    /**
     * updates the view with the new data received from the server.
     */
    void draw(BoardData boardData);
    void printLobby(LobbyInfo lobbyInfo);
    void printError(String error);
    void endCurrentGame(EndGame endGameMessage);
    void disconnected();
}
