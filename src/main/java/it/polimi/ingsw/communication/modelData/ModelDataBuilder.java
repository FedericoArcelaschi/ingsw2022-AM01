package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.communication.modelData.expertMode.ExpertBoardData;
import it.polimi.ingsw.communication.modelData.expertMode.ExpertCastleData;
import it.polimi.ingsw.communication.modelData.expertMode.ExpertIslandData;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.NotTheRightGamemodeException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public static ExpertBoardData newExpertBoardData(String username, Board board) {
        //TODO: this null is awful to look at. For now it works; find a better way regardless.
        List<CharacterData> characters = null;
        try {
            characters = board.getAvailableCharacterCards().stream().filter(Objects::nonNull).map(ModelDataBuilder::newCharacterData).toList();
        } catch (NotTheRightGamemodeException e) {
            e.printStackTrace();
        }
        return new ExpertBoardData(
                username,
                board.getNPlayer(),
                board.getMotherNaturePosition(),
                board.getCloudList().stream().map(ModelDataBuilder::newCloudData).toList(),
                board.getIslandList().stream().map(ModelDataBuilder::newExpertIslandData).toList(),
                newExpertCastleData(username, board.getCastle(username), true),
                board.getCastleMap().keySet().stream()
                        .filter(key -> !key.equals(username))
                        .map(key -> newExpertCastleData(key, board.getCastle(key), false))
                        .toList(),
                newTurnData(board.getTurn()),
                characters
        );
    }

    private static CharacterData newCharacterData(StandardCharacter character){
        return new CharacterData(character);
    }

    private static CloudData newCloudData(Cloud cloud) {
        return new CloudData(cloud.getStudentList(), cloud.isAvailable());
    }

    private static IslandData newIslandData(Island island) {
        return new IslandData(island.getOwnership(), island.getStudents(), island.getIslandNumber());
    }

    private static IslandData newExpertIslandData(Island island) {
        Boolean blocked = null;
        try{
            blocked = island.isBlocked();
        } catch (NotTheRightGamemodeException e) {
            e.printStackTrace();
        }
        return new ExpertIslandData(island.getOwnership(), island.getStudents(), island.getIslandNumber(), Boolean.TRUE.equals(blocked));
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
                isMyCastle
                );
    }

    private static CastleData newExpertCastleData(String username, Castle castle, boolean isMyCastle) {
        List<String> deck = castle.getDeck().stream().filter(Card::isAvailable).map(Card::toString).toList();
        Integer coins = null;
        try {
            coins = castle.getCoins();
        } catch (NotTheRightGamemodeException e) {
            e.printStackTrace();
        }
        return new ExpertCastleData(
                username,
                castle.getWaitingRoom(),
                castle.getDiningRoom(),
                isMyCastle ? deck : null,
                castle.getLastCardPlayed() != null ? castle.getLastCardPlayed().toString() : null,
                castle.getTeam(),
                isMyCastle,
                coins
        );
    }

    private static TurnData newTurnData(Turn t){
        return new TurnData(t.getSittingOrder(), t.getActionOrder(), t.getCurrentPhase(), t.getCurrentPlayer());
    }
}
