package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;

import java.util.ArrayList;
import java.util.List;

public class ExpertBoardData extends BoardData{

    //To add: something to represent characters, with their cost and name and a token that says whether the character
    //is active or not.
    //there are some characters that behave strangely (e.g.: may add students on their character card)
    //and need their data to be handled separately.
    private final List<CharacterData> characters;

    public ExpertBoardData(String username,
                           int nPlayer,
                           int motherNaturePosition,
                           List<CloudData> cloudList,
                           List<IslandData> islandList,
                           CastleData myCastle,
                           List<CastleData> otherCastles,
                           TurnData turn,
                           List<CharacterData> characters) {
        super(username, nPlayer, motherNaturePosition, cloudList, islandList, myCastle, otherCastles, turn);
        this.characters = new ArrayList<>(characters);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        //print island
        s.append("Islands: ");
        for (int i = 0; i < getIslandList().size(); i++) {
            s.append("\n\tIsland ")
                    .append( i + 1 )
                    .append(": ")
                    .append(getIslandList().get(i));
            if (i == getMotherNaturePosition()) s.append(", mother nature is Here!");
        }
        //Print characters
        s.append("\nCharacters: ");
        for (CharacterData cd : characters) {
            s.append(cd).append("\n");
        }
        //Print cloud
        s.append("\nClouds: ");
        for (int i = 0; i < getCloudList().size(); i++)
            s       .append("\n\tCloud ")
                    .append(i + 1)
                    .append(" contains: ")
                    .append(getCloudList().get(i));
        //Print other castles
        s.append("\nOther Player castles:");
        for (CastleData otherCastle : getOtherCastles())
            s.append("\n\tCastle ").append(otherCastle.getUsername()).append(": ").append(otherCastle);
        //Print turn
        s.append("\nTurn: ").append(getTurn());
        //Print my castle with the hand of cards
        s.append("\nMy Castle:");
        s.append("\n\tCastle ").append(getUsername()).append(": ").append(getMyCastle());
        return s.toString();
    }
}
