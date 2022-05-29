package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.*;

import java.util.Objects;

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
                        .filter(key -> !Objects.equals(key, username))
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
        return new CastleData(
                username,
                castle.getWaitingRoom(),
                castle.getDiningRoom(),
                isMyCastle ? castle.getDeck().stream().filter(Card::isAvailable).map(Card::toString).toList() : null,
                castle.getLastCardPlayed() != null ?castle.getLastCardPlayed().toString() : null,
                castle.getTeam(),
                isMyCastle);
    }

    private static TurnData newTurnData(Turn t){
        return new TurnData(t.getSittingOrder(), t.getActionOrder(), t.getCurrentPhase(), t.getCurrentPlayer());
    }
}
