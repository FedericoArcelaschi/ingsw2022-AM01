package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfluenceTestFarmer {
    private final Turn t = new Turn(Arrays.asList("a", "b"));
    private ExpertBoard board;
    private Map<Parameters, Object> parametersMap;
    private Map<Color, Team> professorsMap;
    private Map<String, Castle> castleMap;
    private Castle castleA, castleB;
    private Generic charInfl;


    @BeforeEach
    void setUp() throws TooManyStudentsException {
        board = new ExpertBoard("a", "b", t);
        board.setup4CharacterTesting(2);
        castleA = board.getCastle("a");//WHITE
        castleB = board.getCastle("b");//BLACK
        charInfl = board.getAvailableCharacterCards().get(2);
        parametersMap = new HashMap<>();

    }

    @Test
    void applyEffect() throws TooManyStudentsException, NoSuchStudentException {//a's turn
        castleB.addStudentsInDiningRoom(//BLACK
                Arrays.asList(
                    Color.YELLOW,
                    Color.YELLOW,
                    Color.YELLOW,
                    Color.RED,
                    Color.PINK,
                    Color.GREEN));
        board.updateProfessorsOwners();
        castleA.addStudentsInDiningRoom( //WHITE - //a has 2 coins.
                Arrays.asList(
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.BLUE,
                        Color.GREEN,
                        Color.GREEN));
        board.updateProfessorsOwners();
        professorsMap = board.getProfessorMap();
        Map<Color, Team> expectedProfessorsMap
                = new HashMap<>(Map.of(
                Color.YELLOW, Team.BLACK,
                Color.GREEN, Team.WHITE,
                Color.PINK, Team.BLACK,
                Color.BLUE, Team.WHITE,
                Color.RED, Team.BLACK));
        assertEquals(expectedProfessorsMap, professorsMap
                    , "before applyEffect() use. Initialization check.");
        parametersMap.put(
                Parameters.PROFESSORMAP, professorsMap);
        Influence farmerChar = (Influence) board.getAvailableCharacterCards().get(2);
        board.playExpertCard(2, (ExpertIsland) board.getIslandList().get(0), 0, Arrays.asList());
        professorsMap = board.getProfessorMap();
        expectedProfessorsMap = Map.of(
                Color.YELLOW, Team.WHITE,
                Color.GREEN, Team.WHITE,
                Color.PINK, Team.BLACK,
                Color.BLUE, Team.WHITE,
                Color.RED, Team.BLACK);
        assertEquals(expectedProfessorsMap, professorsMap
                    , "after applyEffect() application.");
    }

}