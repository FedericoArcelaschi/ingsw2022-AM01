package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.baseLogic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        List<StudentColor> students = new ArrayList<>();
        for (StudentColor studentColor: island.getStudents().keySet()) {
            for (int i = 0; i < island.getStudents().get(studentColor); i++) {
                students.add(studentColor);
            }
        }
        return new IslandData(island.getOwnership(), students, island.getIslandNumber());
    }

    private static CastleData newCastleData(String username, Castle castle, boolean isMyCastle) {
        List<StudentColor> diningRoom = new ArrayList<>();
        for (StudentColor studentColor: castle.getDiningRoom().keySet()) {
            for (int i = 0; i < castle.getDiningRoom().get(studentColor); i++) {
                diningRoom.add(studentColor);
            }
        }
        return new CastleData(
                username,
                castle.getWaitingRoom(),
                diningRoom,
                isMyCastle ? castle.getDeck().stream().filter(Card::isAvailable).map(Card::toString).toList() : null,
                castle.getLastCardPlayed() != null ?castle.getLastCardPlayed().toString() : null,
                castle.getTeam(),
                isMyCastle);
    }

    private static TurnData newTurnData(Turn t){
        return new TurnData(t.getSittingOrder(), t.getActionOrder(), t.getCurrentPhase(), t.getCurrentPlayer());
    }
}
