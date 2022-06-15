package it.polimi.ingsw.server.model.expertLogic.characters.student;


import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;

import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class MonkTest { //1° character

    CharacterExplanation explanation = CharacterExplanation.MONK;

    private String player1 = "lorenza", player2 = "federica";
    private ExpertBoard expertBoard;
    private StudentCharacter monk;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        monk = (StudentCharacter) expertBoard.getAvailableCharacters().get(CharacterUtility.MONK);
        if(monk != null) setUp();
    }

    @Test
    boolean playExpertCharacterTest(int islandIndex) {
        StudentColor firstAvailableStudent
                = monk.getAvailableStudents().get(0);
        //island for comparison
        Island thatIsland = expertBoard.getIslandList().get(islandIndex);

        Predicate<Integer> greaterThanZero = (Integer integer)->integer>0;
        //Set< Entry<Color, Integer> >
        StudentColor studentOnThatIsland
                = thatIsland
                .getStudents()
                .entrySet()
                .stream()
                .filter(studentColorIntegerEntry -> studentColorIntegerEntry.getValue() > 0)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(()->new RuntimeException("non va"));

        try {
            expertBoard.playExpertCard(1, islandIndex, List.of(firstAvailableStudent));
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            fail(e.getCause());
        }

        ExpertIsland islandForComparison = new ExpertIsland(new Island(firstAvailableStudent));
        islandForComparison.addStudent(studentOnThatIsland);
        assertEquals(islandForComparison, expertBoard.getIslandList().get(islandIndex));
        return true;
    }

    @Test
    void playExpertCharacterOnEachIsland() {
        for (int i = 1; i < 6; i++) {
            setUp();
            assertTrue(playExpertCharacterTest(i));
        }
    }
}