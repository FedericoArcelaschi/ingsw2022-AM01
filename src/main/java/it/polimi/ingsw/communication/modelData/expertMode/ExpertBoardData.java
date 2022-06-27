package it.polimi.ingsw.communication.modelData.expertMode;

import com.google.gson.annotations.JsonAdapter;
import it.polimi.ingsw.communication.modelData.*;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.ArrayList;
import java.util.List;

@JsonAdapter(BoardDataAdapter.class)
public class ExpertBoardData extends BoardData {
    private final List<CharacterData> characters;
    private final CharacterUtility activeChar;

    public ExpertBoardData(String username,
                           int nPlayer,
                           int motherNaturePosition,
                           List<CloudData> cloudList,
                           List<IslandData> islandList,
                           CastleData myCastle,
                           List<CastleData> otherCastles,
                           TurnData turn,
                           List<CharacterData> characters,
                           CharacterUtility activeChar) {
        super(username, nPlayer, motherNaturePosition, cloudList, islandList, myCastle, otherCastles, turn);
        this.characters = new ArrayList<>(characters);
        this.activeChar = activeChar;
    }

    public List<CharacterData> characters() {
        return characters;
    }

    public CharacterUtility activeChar() {
        return activeChar;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(super.toString());
        s.append("\n\nAvailable characters:");
        for (CharacterData cd : characters) {
            s.append("\n\t").append(cd.toString(activeChar));
        }
        return s.toString();
    }

}
