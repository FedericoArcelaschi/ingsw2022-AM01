package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CentaurTest { //6° character

    CharacterExplanation explanation = CharacterExplanation.CENTAUR;
    private ExpertProfessors professorsMap;
    private ExpertInfluence influence;
    private Castle castle1, castle2;
    private String player1 = "Lorenza", player2 = "Federica";

    private ExpertIsland island;

    private StandardCharacter character;


    @BeforeEach
    void setUp() {
        castle1 = new Castle(Team.BLACK, 2, Bag.extractMany(7));
        castle2 = new Castle(Team.WHITE, 2, Bag.extractMany(7));
        professorsMap = new ExpertProfessors(Map.of(player1, castle1, player2, castle2));
        influence = new ExpertInfluence(professorsMap);
        island = new ExpertIsland(new Island());
        character = new StandardCharacter(6);
    }

    @Test
    void applyEffect() {
        island.setOwnership(Team.BLACK);
        Map<Team, Integer> influenceMap
                = influence.getInfluenceMap(island);
        int influenceOnIsland =
                (int) influenceMap.values().stream().filter(i -> i > 0).count();
        assertEquals(1, influenceOnIsland);

        ParametersForCharacter par4C = new ParametersForCharacter();
        par4C.setInfluence(influence);
        try {
            character.applyEffect(par4C);
        } catch (IllegalAccessException | StudentException e) {
            fail(e.getCause());
        }

        influenceMap = influence.getInfluenceMap(island);
        influenceOnIsland =
                (int) influenceMap.values().stream().filter(i -> i > 0).count();
        assertEquals(0, influenceOnIsland);

    }

    @Test
    void applyEffectMoreComplexBecause() {
        try {
            castle1.addStudentInDiningRoom(StudentColor.YELLOW);
        } catch (TooManyStudentsException e) {
            fail(e.getCause());
        }
        professorsMap.updateProfessorsAssigning();
        for (int i = 0; i < 10; i++) island.addStudent(StudentColor.YELLOW);
        island.setOwnership(Team.BLACK);
        Map<Team, Integer> influenceMap
                = influence.getInfluenceMap(island);
        int influenceForBlack = influenceMap.get(Team.BLACK);
        assertEquals(11, influenceForBlack);
        ParametersForCharacter par4C = new ParametersForCharacter();
        par4C.setInfluence(influence);
        try {
            character.applyEffect(par4C);
        } catch (IllegalAccessException | StudentException e) {
            fail(e.getCause());
        }

        influenceMap = influence.getInfluenceMap(island);
        influenceForBlack = influenceMap.get(Team.BLACK);
        assertEquals(10, influenceForBlack);
    }
}