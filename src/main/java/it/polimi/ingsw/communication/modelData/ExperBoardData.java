package it.polimi.ingsw.communication.modelData;

import java.util.List;

public class ExperBoardData extends BoardData{


    /**
     * @param username
     * @param nPlayer
     * @param motherNaturePosition
     * @param cloudList
     * @param islandList
     * @param myCastle
     * @param otherCastles
     * @param turn
     */


    public ExperBoardData(String username, int nPlayer, int motherNaturePosition, List<CloudData> cloudList, List<IslandData> islandList, CastleData myCastle, List<CastleData> otherCastles, TurnData turn) {
        super(username, nPlayer, motherNaturePosition, cloudList, islandList, myCastle, otherCastles, turn);
    }
}
