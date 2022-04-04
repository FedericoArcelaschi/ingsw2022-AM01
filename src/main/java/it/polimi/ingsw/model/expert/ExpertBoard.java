package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Board;

public class ExpertBoard extends Board {
    //List<Character> characterList = new ArrayList<>();

    public ExpertBoard(String playerID1, String playerID2) {
        super(playerID1, playerID2);
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3) {
        super(playerID1, playerID2, playerID3);
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4) {
        super(playerID1, playerID2, playerID3, playerID4);
    }
}
