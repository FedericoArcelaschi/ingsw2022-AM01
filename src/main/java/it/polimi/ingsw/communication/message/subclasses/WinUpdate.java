package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.modelData.BoardData;

public class WinUpdate extends Update {

    private final String winner;    //FIXME: this should contain e Team Type?!

    public WinUpdate(BoardData boardData, String winner) {
        super(boardData);
        this.winner = winner;
    }

    @Override
    public String toString() { //TODO: implement a better toString()
        return super.toString() + winner;
    }

    public String getWinner() {
        return winner;
    }
}
