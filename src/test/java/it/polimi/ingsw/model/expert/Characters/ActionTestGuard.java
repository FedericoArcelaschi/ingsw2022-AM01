package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Guard;
import java.security.spec.ECField;
import java.time.temporal.Temporal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTestGuard { //THIRD CHARACTER
    private ExpertBoard board;
    private Generic guardChar;
    private Map<Parameters, Object> ParametersMap;
    private int i=0; //wanted to try a global loop, didn't work

    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("a", "b"));
        board = new ExpertBoard("a", "b", t);
        board.setup4CharacterTesting(3);
        guardChar = board.getAvailableCharacterCards().get(3);
        ParametersMap = new HashMap<>();
        i++;
    }

    @Test
    void applyEffectTest() throws NoSuchStudentException, TooManyStudentsException {
        Map<Color, Team> professorsMap
                = new HashMap<>(
                Map.of(
                        Color.YELLOW, Team.WHITE,
                        Color.PINK, Team.WHITE,
                        Color.BLUE, Team.WHITE,
                        Color.GREEN, Team.BLACK,
                        Color.RED, Team.BLACK
                ));
        for (int j = 0; j < 12; j++) {
            ExpertIsland island =
                    (ExpertIsland) board.getIslandList().get(j);
            ParametersMap.putAll(
                    Map.of(
                            Parameters.ISLAND, island,
                            Parameters.PROFESSORSMAP, professorsMap
                    ));
            guardChar.applyEffect(ParametersMap);
            for (Color c : List.of(Color.YELLOW, Color.BLUE, Color.PINK)) {
                if (island.getStudents().get(c) == 1) //island that contained those colors should be white.
                    assertEquals(Team.WHITE, board.getIslandList().get(j).getOwnership(),
                            "all yellow, blue, pink islands should go to WHITE team");
            }
            for (Color c : List.of(Color.GREEN, Color.RED)) {
                if (island.getStudents().get(c) == 1) //island that contained those colors should be black.
                    assertEquals(Team.BLACK, board.getIslandList().get(j).getOwnership(),
                            "all green and red islands should go to BlACK team");
            }
        }
    }
}
