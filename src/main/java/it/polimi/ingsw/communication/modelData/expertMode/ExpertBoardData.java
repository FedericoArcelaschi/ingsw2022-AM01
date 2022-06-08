package it.polimi.ingsw.communication.modelData.expertMode;

import com.google.gson.annotations.JsonAdapter;
import it.polimi.ingsw.communication.modelData.*;

import java.util.ArrayList;
import java.util.List;

@JsonAdapter(BoardDataAdapter.class)
public class ExpertBoardData extends BoardData {

    //TODO: there are some characters that behave strangely (e.g.: may add students on their character card)
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
        s.append(super.toString());
        //Print characters
        s.append("\nCharacters:");
        for (CharacterData cd : characters) {
            s.append("\n\t").append(cd);
        }
        return s.toString();
    }

    public List<CharacterData> characters(){
        return characters;
    }

}
