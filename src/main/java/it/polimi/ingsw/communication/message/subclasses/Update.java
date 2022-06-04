package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.communication.modelData.BoardData;

public class Update extends Message {

    private final BoardData boardData;

    public Update(BoardData boardData) {
        super(MessageType.UPDATE);
        this.boardData = boardData;
    }

    public BoardData getBoardData() {
        return boardData;
    }
    @Override
    public String toString() {
        return "updated board data:\n" + boardData;
    }
}
