package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;

import it.polimi.ingsw.server.model.expertLogic.character.charTypes.BlockCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BlockedIslandTest {
    Island island;
    BlockCharacter witch;

    @BeforeEach
    void setUp() {
        island = new ExpertIsland(new Island());
        witch = new BlockCharacter(5);
    }

    @Test
    void blockIslandTest() {
        ExpertIsland expertIsland = (ExpertIsland) island;
        ParametersForCharacter par4C = new ParametersForCharacter();
        List<Island> islandList = new ArrayList<>(List.of(expertIsland));
        par4C.setIslandList(islandList);
        par4C.setIslandIndex(0);

        try {
            witch.applyEffect(par4C);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        assertTrue(((ExpertIsland)islandList.get(0)).isBlocked());
        unblockIslandTest(islandList);
    }

    void unblockIslandTest(List<Island> islandList) {
        islandList.get(0).setOwnership(Team.BLACK);
        assertFalse(((ExpertIsland)island).isBlocked());
        assertNull(island.getOwnership());
    }
}
