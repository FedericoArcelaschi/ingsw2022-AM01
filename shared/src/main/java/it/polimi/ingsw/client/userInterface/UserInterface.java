package it.polimi.ingsw.client.userInterface;

import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Update;
import it.polimi.ingsw.communication.modelData.BoardData;

public interface UserInterface {

    /**
     * Updates the view with the new data received from the server.
     */
    void draw(BoardData boardData);

    /**
     * Show and update info regarding players waiting to join games
     */
    void printLobby(LobbyInfo lobbyInfo);

    /**
     * Display error message
     */
    void printError(String error);

    /**
     * Draw for the last time and show info about the winner
     */
    void endCurrentGame(EndGame endGameMessage);

    /**
     * Display error message due to lost connection with the server
     */
    void disconnected();
}
