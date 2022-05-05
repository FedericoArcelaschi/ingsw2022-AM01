package it.polimi.ingsw.model.expert.charactersTypes;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.charactersFunctions.CharactersFunction;
import it.polimi.ingsw.model.expert.influence.ExpertInfluenceMap;
import it.polimi.ingsw.model.expert.influence.ExpertProfessorsMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FarmerTest {

    private ExpertProfessorsMap professorsMap;
    private ExpertInfluenceMap influenceMap;
    private Castle castle1, castle2;
    private String player1= "lorenza", player2 = "federica";

    @BeforeEach
    void setUp() {
        castle1 = new Castle(Team.BLACK, 2, Bag.extractMany(7));
        castle2 = new Castle(Team.WHITE, 2, Bag.extractMany(7));
        professorsMap = new ExpertProfessorsMap(Map.of(player1, castle1, player2, castle2));
        influenceMap = new ExpertInfluenceMap(professorsMap);
    }

    @Test
    void applyEffectFarmer() {
        try {
            castle1.addStudentInDiningRoom(Color.YELLOW);
            professorsMap.updateProfessorsMap();
            castle2.addStudentInDiningRoom(Color.YELLOW);
            professorsMap.updateProfessorsMap();
            assertEquals(Team.BLACK, professorsMap.getProfessorsMap().get(Color.YELLOW));
            CharactersFunction.FARMER.getFunction().applyEffect(null, Arrays.asList(castle2), influenceMap, null);
            professorsMap.updateProfessorsMap();
            assertEquals(Team.WHITE, professorsMap.getProfessorsMap().get(Color.YELLOW));
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}