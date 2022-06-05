package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.baseLogic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ModelDataBuilder {
    public static BoardData newBoardData(String username, Board board){
        return new BoardData(
                username,
                board.getNPlayer(),
                board.getMotherNaturePosition(),
                board.getCloudList().stream().map(ModelDataBuilder::newCloudData).toList(),
                board.getIslandList().stream().map(ModelDataBuilder::newIslandData).toList(),
                newCastleData(username, board.getCastle(username), true),
                board.getCastleMap().keySet().stream()
                        .filter(key -> !key.equals(username)) //selects only other players' castle
                        .map(key -> newCastleData(key, board.getCastle(key), false))
                        .toList(),
                newTurnData(board.getTurn())
        );
    }



    private static CloudData newCloudData(Cloud cloud) {
        return new CloudData(cloud.getStudentList(), cloud.isAvailable());
    }

    private static IslandData newIslandData(Island island) {
        return new IslandData(island.getOwnership(), island.getStudents(), island.getIslandNumber());
    }

    private static CastleData newCastleData(String username, Castle castle, boolean isMyCastle) {
        List<String> deck = castle.getDeck().stream().filter(Card::isAvailable).map(Card::toString).toList();
        return new CastleData(
                username,
                castle.getWaitingRoom(),
                castle.getDiningRoom(),
                isMyCastle ? deck : null,
                castle.getLastCardPlayed() != null ? castle.getLastCardPlayed().toString() : null,
                castle.getTeam(),
                isMyCastle);
    }

    private static TurnData newTurnData(Turn t){
        return new TurnData(t.getSittingOrder(), t.getActionOrder(), t.getCurrentPhase(), t.getCurrentPlayer());
    }
}
