package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;

import java.util.List;
import java.util.Map;

public record BoardData(
        String username,
        int nPlayer,
        int motherNaturePosition,
        List<CloudData> cloudList,
        List<IslandData> islandList,
        List<CastleData> otherCastles
)
{
    public BoardData(String username, Board board){
        this(username, board.getNPlayer(),
                board.getMotherNaturePosition(),
                board.getCloudList().stream().map(CloudData::new).toList(),
                board.getIslandList().stream().map(IslandData::new).toList(),
                board.getCastleMap().keySet().stream().map(key -> new CastleData(key, board.getCastle(key))).toList()
                );
    }
}
