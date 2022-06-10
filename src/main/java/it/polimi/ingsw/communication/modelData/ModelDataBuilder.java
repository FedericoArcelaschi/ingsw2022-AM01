package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.communication.modelData.expertMode.ExpertBoardData;
import it.polimi.ingsw.communication.modelData.expertMode.ExpertCastleData;
import it.polimi.ingsw.communication.modelData.expertMode.ExpertIslandData;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.NotTheRightGameModeException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.*;

public abstract class ModelDataBuilder {
    public static BoardData newBoardData(Board board, String username) {
        return new BoardData(
                username,
                board.getCloudList().size(),
                board.getMotherNaturePosition(),
                board.getCloudList().stream().map(ModelDataBuilder::newCloudData).toList(),
                board.getIslandList().stream().map(ModelDataBuilder::newIslandData).toList(),
                newCastleData(username, board.getCastle(username), true, board.placedTowers(), board.getProfessorsMap()),
                board.getCastleMap().keySet().stream()
                        .filter(key -> !key.equals(username)) //selects only other players' castle
                        .map(key -> newCastleData(key, board.getCastle(key), false, board.placedTowers(), board.getProfessorsMap()))
                        .toList(),
                newTurnData(board.getTurn())
        );
    }

    public static ExpertBoardData newExpertBoardData(Board board, String username) {
        //TODO: this null is awful to look at. For now it works; find a better way regardless.
        List<CharacterData> characters = null;
        CharacterUtility activeChar = null;
        try {
            characters = board.getAvailableCharacterCards().stream().filter((Objects::nonNull)).map(ModelDataBuilder::newCharacterData).toList();
            activeChar = board.getPlayedExpertChar();
        } catch (NotTheRightGameModeException e) {
            e.getMessage();
        }
        return new ExpertBoardData(
                username,
                board.getCloudList().size(),
                board.getMotherNaturePosition(),
                board.getCloudList().stream().map(ModelDataBuilder::newCloudData).toList(),
                board.getIslandList().stream().map(ModelDataBuilder::newExpertIslandData).toList(),
                newExpertCastleData(username, board.getCastle(username), true, board.placedTowers(), board.getProfessorsMap()),
                board.getCastleMap().keySet().stream()
                        .filter(key -> !key.equals(username))
                        .map(key -> newExpertCastleData(key, board.getCastle(key), false, board.placedTowers(), board.getProfessorsMap()))
                        .toList(),
                newTurnData(board.getTurn()),
                characters,
                activeChar
        );
    }

    private static CharacterData newCharacterData(StandardCharacter character){
        return new CharacterData(character.getName(), character.getCost(), character.getAvailableStudents(), character.getExplanation());
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
        } catch (NotTheRightGameModeException e) {
            e.printStackTrace();
        }
        return new ExpertIslandData(island.getOwnership(), island.getStudents(), island.getIslandNumber(), Boolean.TRUE.equals(blocked));
    }

    private static CastleData newCastleData(String username, Castle castle, boolean isMyCastle, EnumMap<Team, Integer> placedTower, Map<StudentColor, Team> teachers) {
        List<String> deck = castle.getDeck().stream().filter(Card::isAvailable).map(Card::toString).toList();
        return new CastleData(
                username,
                castle.getWaitingRoom(),
                castle.getDiningRoom(),
                isMyCastle ? deck : null,
                castle.getLastCardPlayed() != null ? castle.getLastCardPlayed().toString() : null,
                castle.getTeam(),
                placedTower.get(castle.getTeam()),
                teachers,
                isMyCastle
                );
    }

    private static CastleData newExpertCastleData(String username, Castle castle, boolean isMyCastle, EnumMap<Team, Integer> placedTower, Map<StudentColor, Team> teachers) {
        List<String> deck = castle.getDeck().stream().filter(Card::isAvailable).map(Card::toString).toList();
        Integer coins = null;
        try {
            coins = castle.getCoins();
        } catch (NotTheRightGameModeException e) {
            e.printStackTrace();
        }
        return new ExpertCastleData(
                username,
                castle.getWaitingRoom(),
                castle.getDiningRoom(),
                isMyCastle ? deck : null,
                castle.getLastCardPlayed() != null ? castle.getLastCardPlayed().toString() : null,
                castle.getTeam(),
                placedTower.get(castle.getTeam()),
                teachers,
                isMyCastle,
                coins
        );
    }

    private static TurnData newTurnData(Turn t){
        return new TurnData(t.getSittingOrder(), t.getActionOrder(), t.getCurrentPhase(), t.getCurrentPlayer());
    }
}
