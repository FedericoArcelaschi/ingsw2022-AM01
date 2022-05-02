package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Board;

import java.util.List;
import java.util.Objects;

/**
 * A representation of the model containing only data useful for the clients
 */
public record BoardData(
        String username,
        int nPlayer,
        int motherNaturePosition,
        List<CloudData> cloudList,
        List<IslandData> islandList,
        CastleData myCastle,
        List<CastleData> otherCastles
)
{
    public BoardData(String username, Board board){
        this(username, board.getNPlayer(),
                board.getMotherNaturePosition(),
                board.getCloudList().stream().map(CloudData::new).toList(),
                board.getIslandList().stream().map(IslandData::new).toList(),
                new CastleData(username, board.getCastle(username)),
                board.getCastleMap().keySet().stream().filter(key -> !Objects.equals(key, username)).map(key -> new CastleData(key, board.getCastle(key))).toList()
                );
    }
}
