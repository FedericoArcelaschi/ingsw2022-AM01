package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.communication.modelData.BoardData;

public class WinUpdate extends Update {

    private final String winner;

    public WinUpdate(BoardData boardData, String winner) {
        super(boardData);
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}
