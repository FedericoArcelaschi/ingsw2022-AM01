package it.polimi.ingsw.model.expert.character.type;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IntegerBoxing;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.expert.BlockedIsland;
import it.polimi.ingsw.model.expert.ExpertIsland;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffect;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import it.polimi.ingsw.model.influence.Influence;

import java.util.List;

public class IslandCharacter extends MasterCharacter {
    //must be
    // 0 ≤ x ≤ 4
    private int availableBlockTile;
    private ApplyEffect function;

    public IslandCharacter(int idChar) {
        super(idChar);
        availableBlockTile = 4;
    }

    /**
     * WITCH character: blocks islands
     * @ param island contains the island to block
     */
    @Override
    public void applyEffect(List<Color> students, List<StudentPlaces> placesList, Influence influenceMap, IntegerBoxing steps) throws NoSuchStudentException, TooManyStudentsException {
        if (availableBlockTile == 0)
            throw new IllegalArgumentException("4 islands are already blocked");
        ExpertIsland island = (ExpertIsland) placesList.get(2);
        if (island.isBlocked())
            throw new IllegalArgumentException("Island is already blocked");
        island = new BlockedIsland(island, this); //Todo: check if the value also changes in the board.
        availableBlockTile--;
        cost = characterName.getCost() + 1;
    }

    @Override
    public String getEffect() {
        return null;
    }

    public void addBlockTile(){
        availableBlockTile++;
    }
}
