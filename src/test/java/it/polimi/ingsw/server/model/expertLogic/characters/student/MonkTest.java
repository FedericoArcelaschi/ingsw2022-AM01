package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class MonkTest { //1° character

    CharacterExplanation explanation = CharacterExplanation.MONK;

    private final String player1 = "lorenza";
    private final String player2 = "federica";
    private ExpertBoard expertBoard;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MONK))
            setUp();
        else
            try {
                expertBoard.playCard(player1, 5);
                expertBoard.changePhase();
                expertBoard.playCard(player2, 7);
                expertBoard.changePhase();
            } catch (PhaseNotRightException e) {
                e.printStackTrace();
                fail();
            }
    }

    @Test
    void playExpertCharacterOnEachIsland() {
        for (int i = 1; i < 6; i++){
            setUp();
            assertTrue(playExpertCharacterTest(i));
        }
    }

    boolean playExpertCharacterTest(int islandIndex) {
        //Monk setUp
        StudentCharacter monk
                = (StudentCharacter) expertBoard.getAvailableCharacters().get(CharacterUtility.MONK);
        StudentColor firstAvailableStudent
                = monk.getAvailableStudents().get(0);

        //island for check:
        Island thatIsland = expertBoard.getIslandList().get(islandIndex);
        //Set< Entry<Color, Integer> >
        StudentColor studentOnThatIsland
                = thatIsland
                .getStudents()
                .entrySet()
                .stream()
                .distinct()
                .filter(c->c.getValue() > 0) //gets a student's color which is present
                .toList()
                .get(0)
                .getKey();

        //actual playExpertCard
        try {
            expertBoard.playExpertCard(1, islandIndex, List.of(firstAvailableStudent));
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }


        ExpertIsland islandForComparison = new ExpertIsland(new Island(firstAvailableStudent));
        islandForComparison.addStudent(studentOnThatIsland);
        assertEquals(islandForComparison.getStudents(), expertBoard.getIslandList().get(islandIndex).getStudents());
        return true;
    }
}
