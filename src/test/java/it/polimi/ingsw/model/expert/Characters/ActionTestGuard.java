package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * test for the third character.
 */
public class ActionTestGuard {
    private ExpertBoard board;
    private Generic guardChar;
    private Map<Parameters, Object> ParametersMap;
    private final int i = 0; //wanted to try a global loop, didn't work

    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("Federico", "Lorenzo"));
        board = new ExpertBoard("Federico", "Lorenzo", t);
        board.extract4CharacterTesting(3);
        guardChar = board.getAvailableCharacterCards().get(3);
        ParametersMap = new HashMap<>();
    }

    @Test
    void applyEffectTest() throws StudentException { //THIRD CHARACTER
        Map<Color, Team> professorsMap
                = new HashMap<>(
                Map.of(
                        Color.YELLOW, Team.WHITE,
                        Color.PINK, Team.WHITE,
                        Color.BLUE, Team.WHITE,
                        Color.GREEN, Team.BLACK,
                        Color.RED, Team.BLACK
                ));
        for (int j = 0; j < 12; j++) {//loop: Federico conquers every island(first and 6th have no students on them)
            ExpertIsland island =
                    (ExpertIsland) board.getIslandList().get(j);
            assertNull(island.getOwnership(),
                    "First every island is well initialized");
            ParametersMap.putAll(
                    Map.of(
                            Parameters.ISLAND, island,
                            Parameters.PROFESSORSMAP, professorsMap
                    ));
            guardChar.applyEffect(ParametersMap);
            //System.out.println("isola prima: " + j + " " +island);
            for (Color c : List.of(Color.YELLOW, Color.BLUE, Color.PINK)) {
                if (island.getStudents().get(c) == 1) //island that contained those colors should be white.
                    assertEquals(Team.WHITE, board.getIslandList().get(j).getOwnership(),
                            "all yellow, blue, pink islands should go to WHITE team");
            }
            for (Color c : List.of(Color.GREEN, Color.RED)) {
                if (island.getStudents().get(c) == 1) //island that contained those colors should be black.
                    assertEquals(Team.BLACK, island.getOwnership(),
                            "all green and red islands should go to BlACK team");
            }
            if(j == 0 || j == 6)
                assertNull(island.getOwnership(),
                        "those islands contain no students --> therefore have no owner");
            //System.out.println("isola dopo: " + j + " " + board.getIslandList().get(j).toString());
        }
    }
}
