package it.polimi.ingsw.communication.modelData;

import java.util.List;

public class ExpertBoardData extends BoardData{

    //To add: something to represent characters, with their cost and name and a token that says whether the character
    //is active or not.
    //there are some characters that behave strangely (e.g.: may add students on their character card)
    //and need their data to be handled separately.

    public ExpertBoardData(String username,
                           int nPlayer,
                           int motherNaturePosition,
                           List<CloudData> cloudList,
                           List<IslandData> islandList,
                           CastleData myCastle,
                           List<CastleData> otherCastles,
                           TurnData turn) {
        super(username, nPlayer, motherNaturePosition, cloudList, islandList, myCastle, otherCastles, turn);
    }
}
