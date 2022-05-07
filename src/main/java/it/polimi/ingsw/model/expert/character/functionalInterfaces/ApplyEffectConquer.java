package it.polimi.ingsw.model.expert.character.functionalInterfaces;

import it.polimi.ingsw.model.expert.ExpertIsland;
import it.polimi.ingsw.model.influence.Influence;

import java.util.List;

public interface ApplyEffectConquer {

    /**
     * Method for Guard;
     * Tries to conquer an island.
     * Receives all the islands because it may need to join them
     * @param islandList all the islands from the Board
     * @param influence The influence object to compute the influence on the island
     * @param islandIndex The index to find the island to try to conquer.
     */
    void applyEffect(List<ExpertIsland> islandList, Influence influence, Integer islandIndex);

}
