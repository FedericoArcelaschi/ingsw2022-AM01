package it.polimi.ingsw.communication.modelData.expertMode;

import com.google.gson.annotations.JsonAdapter;
import it.polimi.ingsw.communication.modelData.*;

import java.util.ArrayList;
import java.util.List;

@JsonAdapter(BoardDataAdapter.class)
public class ExpertBoardData extends BoardData {

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

    public List<CharacterData> characters() {
        return characters;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        //print island
        s.append("Islands: ");
        for (int i = 0; i < super.islandList.size(); i++) {
            s.append("\n\tIsland ")
                    .append(i + 1)
                    .append(": ")
                    .append(super.islandList.get(i));
            if (i == motherNaturePosition) s.append(", mother nature is Here!");
        }
        //Print characters
        s.append("\nCharacters:");
        for (CharacterData cd : characters) {
            s.append("\n\t").append(cd);
        }
        //Print cloud
        s.append("\nClouds: ");
        for (int i = 0; i < cloudList.size(); i++)
            s       .append("\n\tCloud ")
                    .append(i + 1)
                    .append(" contains: ")
                    .append(cloudList.get(i));
        //Print other castles
        s.append("\nOther Player castles:");
        for (CastleData otherCastle : otherCastles)
            s.append("\n\tCastle ").append(otherCastle.username()).append(": ").append(otherCastle);
        //Print turn
        s.append("\nTurn: ").append(turn);
        //Print my castle with the hand of cards
        s.append("\nMy Castle:");
        s.append("\n\tCastle ").append(username).append(": ").append(myCastle);
        return s.toString();
    }


}
