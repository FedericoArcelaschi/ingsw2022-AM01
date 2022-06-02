package it.polimi.ingsw.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class MonkTest { //1° character

    CharacterExplanation explanation = CharacterExplanation.MONK;

    private String player1 = "lorenza", player2 = "federica";
    private ExpertBoard expertBoard;

    boolean playExpertCharacterTest(int islandIndex) {
        //SetUp
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        expertBoard.extract4CharacterTesting(1);
        //Monk setUp
        StudentCharacter monk
                = (StudentCharacter) expertBoard.getAvailableCharacterCards().get(1);
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
        } catch (StudentException | CoinException e) {
            fail(e.getCause());
        }


        ExpertIsland islandForComparison = new ExpertIsland(new Island(firstAvailableStudent));
        islandForComparison.addStudent(studentOnThatIsland);
        assertEquals(islandForComparison, expertBoard.getIslandList().get(islandIndex));
        return true;
    }

    @Test
    void playExpertCharacterOnEachIsland() {
        for (int i = 1; i < 6; i++)
            assertTrue(playExpertCharacterTest(i));
    }
}
