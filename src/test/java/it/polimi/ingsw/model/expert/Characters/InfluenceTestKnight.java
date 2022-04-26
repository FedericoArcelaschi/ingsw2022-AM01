package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.CoinException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 *test for eighth character
 */
class InfluenceTestKnight {

    private ExpertBoard board;
    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("lorenzo", "federico"));
        board = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("lorenzo", "federico"), true, t);
        board.extract4CharacterTesting(8);
    }

    @Test
    void testApplyEffect() {
        try {
            board.getCastleMap().get("lorenzo").addStudentsInDiningRoom(Arrays.asList(Color.PINK, Color.PINK, Color.PINK));
            assertEquals(2, ((ExpertCastle) board.getCastleMap().get("lorenzo")).getCoins());
            ExpertIsland island = (ExpertIsland) board.getIslandList().get(7);
            Color student = null;
            for (Color c:Color.values()) {
               if(island.getStudents().get(c) == 1) student = c;
            }
            island.addStudent(
                    Map.of(student, 1,
                            Color.GREEN, 2));
            Map<Color, Team> professorsMap = new HashMap<>();

            assertEquals(0, island.calculateInfluence(professorsMap).get(Team.WHITE));
            assertEquals(0, island.calculateInfluence(professorsMap).get(Team.BLACK));

            professorsMap.put(student, Team.WHITE);
            professorsMap.put(Color.GREEN, Team.BLACK);
            assertEquals(2, island.calculateInfluence(professorsMap).get(Team.WHITE));
            island.setOwnership(Team.BLACK);
            assertEquals(2, island.calculateInfluenceNoTowers(professorsMap).get(Team.BLACK));
            assertEquals(3, island.calculateInfluence(professorsMap).get(Team.BLACK));
            board.playExpertCard(8);
            board.playCard("lorenzo", 8);
            board.moveMotherNature(3);
            board.moveMotherNature(4);
            assertEquals(Team.WHITE, island.getOwnership(), "White should conquer this island");
        }catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");
        }
    }
}