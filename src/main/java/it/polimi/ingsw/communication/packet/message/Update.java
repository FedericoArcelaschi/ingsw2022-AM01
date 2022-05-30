package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.communication.modelData.BoardData;

public class Update implements Message {
    private final BoardData boardData;

    public Update(BoardData boardData) {
        this.boardData = boardData;
    }

    public BoardData getBoardData() {
        return boardData;
    }
}
