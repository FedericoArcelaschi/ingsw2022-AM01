package it.polimi.ingsw.communication.packet;

import it.polimi.ingsw.communication.modelData.BoardData;

public final class Update extends Message {
    private final BoardData boardData;

    public Update(BoardData boardData) {
        this.boardData = boardData;
    }

    public BoardData getBoardData() {
        return boardData;
    }
}
