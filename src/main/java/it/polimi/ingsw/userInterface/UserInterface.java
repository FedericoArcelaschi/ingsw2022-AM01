package it.polimi.ingsw.userInterface;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.controller.GameType;

import java.util.List;

public interface UserInterface {
    void draw(BoardData boardData);
    void roomOutput(List<String> connectedUser, GameType gameType);
}
