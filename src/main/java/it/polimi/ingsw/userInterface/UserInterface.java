package it.polimi.ingsw.userInterface;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.controller.GameType;

import java.util.List;

public interface UserInterface {

    /**
     * updates the view with the new data received from the server.
     */
    void draw(BoardData boardData);
    void roomOutput(List<String> connectedUser, GameType gameType);
}
